package jkind.api.simple;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;

import jkind.JKindException;
import jkind.api.Backend;
import jkind.api.results.JKindResult;
import jkind.api.simple.xml.Kind2WebInputStream;
import jkind.api.simple.xml.XmlParseThread;

/**
 * The web-based interface to Kind2.
 * 
 * @deprecated
 *    To be reomved in 6.0.
 * 	  This class represents a transitional API to provide a basic, command-
 *    line oriented means of using JKind.  This functionality duplicates that
 *    of the jkind.api package but removes the dependencies on Eclipse.  Once
 *    the Eclipse-specific dependencies have been removed, this functionality
 *    will migrate to package jkind.api.
 */
@Deprecated
public class Kind2WebApi extends Kind2Api {
	private static final long POLL_INTERVAL = 100;
	private final URI uri;

	public Kind2WebApi(String uri) {
		try {
			this.uri = new URI(uri + "/");
		} catch (URISyntaxException e) {
			throw new JKindException("Error parsing URI", e);
		}
	}

	/**
	 * Run Kind2 on a Lustre program
	 * 
	 * @param lustreFile
	 *            File containing Lustre program
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 */
	@Override
	public void execute(File lustreFile, JKindResult result) {
		execute(readFile(lustreFile), result);
	}

	private String readFile(File file) {
		try {
			return new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			throw new JKindException("Error reading lustre file", e);
		}
	}

	/**
	 * Run Kind2 on a Lustre program
	 * 
	 * @param program
	 *            Lustre program as text
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 */
	@Override
	public void execute(String program, JKindResult result) {
		XmlParseThread parseThread = null;

		debug.println("Kind 2 URI: " + uri);
		debug.println("Kind 2 arguments: " + String.join(" ", getArgs()));
		try (Kind2WebInputStream stream = new Kind2WebInputStream(uri, getArgs(), program)) {
			result.start();
			parseThread = new XmlParseThread(stream, result, Backend.KIND2);
			parseThread.start();
			while (parseThread.isAlive()) {
				sleep(POLL_INTERVAL);
			}
		} finally {
			try {
				parseThread.join();
			} catch (Exception e) {
			}

			result.done();
		}

		if (parseThread.getThrowable() != null) {
			throw new JKindException("Error parsing XML", parseThread.getThrowable());
		}
	}

	@Override
	public String checkAvailable() throws IOException {
		String program = "node main() returns (); let tel;";
		List<String> args = Collections.emptyList();
		try (Kind2WebInputStream stream = new Kind2WebInputStream(uri, args, program)) {
			stream.read();
		}
		return "Kind 2 web interface available";
	}
}
