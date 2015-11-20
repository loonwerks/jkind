package jkind.api.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jkind.JKindException;
import jkind.api.Kind2Api;

public class Kind2WebInputStream extends InputStream {
	private static final int POLL_INTERVAL = 1000;
	private final URI baseUri;
	private final List<String> args;
	private final String lustre;
	private String jobId;
	private String buffer;
	private int index;
	private volatile boolean done;

	public Kind2WebInputStream(URI baseUri, List<String> args, String lustre) {
		this.baseUri = baseUri;
		this.args = args;
		this.lustre = lustre;
	}

	@Override
	public int read() throws IOException {
		if (done) {
			return -1;
		}

		if (jobId == null) {
			submitJob();
			buffer = "";
			index = 0;
		}

		while (index >= buffer.length()) {
			try {
				Thread.sleep(POLL_INTERVAL);
			} catch (InterruptedException e) {
				return -1;
			}
			buffer = retrieveJob();
			if (buffer == null || done) {
				done = true;
				return -1;
			}
			index = 0;
		}

		return buffer.charAt(index++);
	}

	private void submitJob() throws IOException {
		URL url = baseUri.resolve("submitjob").toURL();
		URLConnection conn = createRequest(lustre, url);
		conn.connect();
		jobId = getJobId(conn.getInputStream());
		conn.getInputStream().close();
	}

	private URLConnection createRequest(String lustre, URL url) throws IOException {
		// http://stackoverflow.com/questions/2793150/how-to-use-java-net-urlconnection-to-fire-and-handle-http-requests/2793153#2793153
		URLConnection conn = url.openConnection();
		conn.setUseCaches(false);
		conn.setDoOutput(true);
		// Just generate some unique random value.
		String boundary = Long.toHexString(System.currentTimeMillis());
		String CRLF = "\r\n"; // Line separator required by multipart/form-data.
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

		try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),
				"UTF-8"), true)) {
			// kind param
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"kind\"").append(CRLF);
			writer.append(CRLF).append(Kind2Api.KIND2).append(CRLF).flush();

			// arg param
			for (String arg : args) {
				writer.append("--" + boundary).append(CRLF);
				writer.append("Content-Disposition: form-data; name=\"arg\"").append(CRLF);
				writer.append(CRLF).append(arg).append(CRLF).flush();
			}

			// file param
			writer.append("--" + boundary).append(CRLF);
			writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"upload.lus\"")
					.append(CRLF);
			writer.append("Content-Type: text/plain; charset=UTF-8").append(CRLF);
			writer.append(CRLF).flush();
			writer.append(lustre);
			writer.append(CRLF).flush();
			// CRLF is important! It indicates end of boundary.

			// End of multipart/form-data.
			writer.append("--" + boundary + "--").append(CRLF);
		}
		return conn;
	}

	private String retrieveJob() throws IOException {
		StringBuilder content = new StringBuilder();
		URL url = baseUri.resolve("retrievejob/").resolve(jobId).toURL();
		URLConnection conn = url.openConnection();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			content.append(line).append("\n");
		}
		conn.getInputStream().close();
		String body = content.toString();

		if (body.startsWith("<Jobstatus msg=\"completed\">")) {
			return null;
		} else {
			return body;
		}
	}

	private String getJobId(InputStream is) throws IOException {
		Pattern jobIdPattern = Pattern.compile(".*jobid=\"(.*?)\".*");
		Pattern abortPattern = Pattern.compile(".*msg=\"aborted\">(.*?)</.*");
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = reader.readLine()) != null) {
			Matcher match = jobIdPattern.matcher(line);
			if (match.matches()) {
				return match.group(1);
			}
			match = abortPattern.matcher(line);
			if (match.matches()) {
				throw new JKindException("Kind2 server aborted job: " + match.group(1));
			}
		}
		throw new JKindException("Failed to receive job id from " + baseUri);
	}

	@Override
	public void close() {
		if (jobId != null && !done) {
			cancelJob();
			done = true;
		}
	}

	private void cancelJob() {
		try {
			URL url = baseUri.resolve("canceljob/").resolve(jobId).toURL();
			URLConnection conn = url.openConnection();
			conn.getInputStream().close();
		} catch (IOException e) {
			throw new JKindException("Error canceling kind2 job", e);
		}
	}
}
