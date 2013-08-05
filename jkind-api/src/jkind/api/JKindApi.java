package jkind.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.xml.sax.SAXException;

import jkind.JKindException;
import jkind.api.results.DynamicJKindResult;
import jkind.api.results.JKindResult;
import jkind.api.results.PropertyResult;
import jkind.api.xml.JKindXmlFileInputStream;
import jkind.api.xml.XmlParseThread;
import jkind.lustre.Program;
import jkind.results.Property;

/**
 * The primary interface to JKind.
 */
public class JKindApi {
	private Integer timeout = null;
	private Integer n = null;
	private boolean inductiveCounterexamples = false;
	private boolean reduceInvariants = false;

	/**
	 * Set a maximum run time for entire execution
	 * 
	 * @param timeout
	 *            A positive timeout in seconds
	 */
	public void setTimeout(int timeout) {
		if (timeout <= 0) {
			throw new JKindException("Timeout must be positive");
		}
		this.timeout = timeout;
	}

	/**
	 * Set a maximum value for k in k-induction algorithm
	 * 
	 * @param n
	 *            A non-negative integer
	 */
	public void setN(int n) {
		if (n < 0) {
			throw new JKindException("n must be positive");
		}
		this.n = n;
	}

	/**
	 * Produce inductive counterexamples for 'unknown' properties
	 */
	public void setInductiveCounterexamples() {
		inductiveCounterexamples = true;
	}

	/**
	 * Reduce and report the invariants used for a valid property
	 */
	public void setReduceInvariants() {
		reduceInvariants = true;
	}

	@Deprecated
	public JKindResult execute(Program program) {
		DynamicJKindResult result = new DynamicJKindResult();
		execute(program, result, new NullProgressMonitor());

		List<Property> properties = new ArrayList<Property>();
		for (PropertyResult pr : result.getPropertyResults()) {
			properties.add(pr.getProperty());
		}
		return new JKindResult(result.getText(), properties);
	}

	/**
	 * Run JKind on a Lustre program
	 * 
	 * @param program
	 *            Lustre program
	 * @return results of JKind
	 * @throws jkind.JKindException
	 */
	public void execute(Program program, DynamicJKindResult result, IProgressMonitor monitor) {
		execute(program.toString(), result, monitor);
	}

	/**
	 * Run JKind on a Lustre program
	 * 
	 * @param program
	 *            Lustre program as text
	 * @return results of JKind
	 * @throws jkind.JKindException
	 */
	public void execute(String program, DynamicJKindResult result, IProgressMonitor monitor) {
		File lustreFile = null;
		try {
			lustreFile = writeLustreFile(program);
			execute(lustreFile, result, monitor);
		} finally {
			safeDelete(lustreFile);
		}
	}

	/**
	 * Run JKind on a Lustre program
	 * 
	 * @param lustreFile
	 *            File containing Lustre program
	 * @param monitor
	 * @return results of JKind
	 * @throws jkind.JKindException
	 */
	public void execute(File lustreFile, DynamicJKindResult result, IProgressMonitor monitor) {
		File xmlFile = null;
		try {
			xmlFile = getXmlFile(lustreFile);
			safeDelete(xmlFile);
			if (xmlFile.exists()) {
				throw new JKindException("Existing XML file cannot be removed: " + xmlFile);
			}
			callJKind(lustreFile, xmlFile, result, monitor);
		} catch (Throwable t) {
			throw new JKindException(result.getText(), t);
		} finally {
			safeDelete(xmlFile);
		}
	}

	private static File writeLustreFile(String program) {
		File file = null;
		try {
			file = File.createTempFile("jkind-api", ".lus");
			writeToFile(program, file);
			return file;
		} catch (IOException e) {
			safeDelete(file);
			throw new JKindException("Cannot write to file: " + file, e);
		}
	}

	private static void safeDelete(File file) {
		if (file != null && file.exists()) {
			file.delete();
		}
	}

	private static void writeToFile(String content, File file) throws IOException {
		Writer writer = null;
		try {
			writer = new FileWriter(file);
			writer.append(content);
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

	private void callJKind(File lustreFile, File xmlFile, DynamicJKindResult result,
			IProgressMonitor monitor) throws IOException, InterruptedException,
			ParserConfigurationException, SAXException {
		ProcessBuilder builder = getJKindProcessBuilder(lustreFile);
		Process process = null;
		JKindXmlFileInputStream xmlStream = new JKindXmlFileInputStream(xmlFile);
		XmlParseThread parseThread = new XmlParseThread(xmlStream, result);

		try {
			result.start();
			process = builder.start();
			parseThread.start();
			readOutput(process, result, monitor);
		} finally {
			if (process != null) {
				process.destroy();
				process.waitFor();
			}
			xmlStream.done();
			parseThread.join();
			if (monitor.isCanceled()) {
				result.cancel();
			} else {
				result.done();
			}
			monitor.done();
		}

		if (parseThread.getThrowable() != null) {
			throw new JKindException("Error parsing XML", parseThread.getThrowable());
		}
	}

	private void readOutput(Process process, final DynamicJKindResult result,
			IProgressMonitor monitor) throws IOException {
		final InputStream stream = new BufferedInputStream(process.getInputStream());

		while (true) {
			if (monitor.isCanceled()) {
				return;
			} else if (stream.available() > 0) {
				int c = stream.read();
				if (c == -1) {
					return;
				}
				result.addText((char) c);
			} else if (isTerminated(process)) {
				return;
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	private boolean isTerminated(Process process) {
		try {
			process.exitValue();
			return true;
		} catch (IllegalThreadStateException e) {
			return false;
		}
	}

	private ProcessBuilder getJKindProcessBuilder(File lustreFile) throws IOException {
		List<String> args = new ArrayList<>();
		if (System.getProperty("os.name").startsWith("Windows")) {
			args.add("cmd");
			args.add("/c");
		}
		args.add("jkind");
		args.add("-xml");
		if (timeout != null) {
			args.add("-timeout");
			args.add(timeout.toString());
		}
		if (n != null) {
			args.add("-n");
			args.add(n.toString());
		}
		if (inductiveCounterexamples) {
			args.add("-induct_cex");
		}
		if (reduceInvariants) {
			args.add("-reduce_inv");
		}
		args.add(lustreFile.toString());

		ProcessBuilder builder = new ProcessBuilder(args);
		builder.redirectErrorStream(true);
		return builder;
	}

	private static File getXmlFile(File lustreFile) {
		return new File(lustreFile.toString() + ".xml");
	}
}
