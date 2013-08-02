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
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import jkind.JKindException;
import jkind.lustre.Program;
import jkind.results.JKindResult;
import jkind.results.Property;

import org.xml.sax.SAXException;

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
	 * @param timeout A positive timeout in seconds
	 */
	public void setTimeout(int timeout) {
		if (timeout <= 0) {
			throw new JKindException("Timeout must be positive");
		}
		this.timeout = timeout;
	}

	/**
	 * Set a maximum value for k in k-induction algorithm
	 * @param n A non-negative integer
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

	/**
	 * Run JKind on a Lustre program
	 * @param program Lustre program
	 * @return results of JKind
	 * @throws JKindException 
	 */
	public JKindResult execute(Program program) {
		return execute(program.toString());
	}
	
	/**
	 * Run JKind on a Lustre program
	 * @param program Lustre program as text
	 * @return results of JKind
	 * @throws JKindException
	 */
	public JKindResult execute(String program) {
		File lustreFile = null;
		try {
			lustreFile = writeLustreFile(program);
			return execute(lustreFile);
		} finally {
			safeDelete(lustreFile);
		}
	}
	
	/**
	 * Run JKind on a Lustre program
	 * @param lustreFile File containing Lustre program
	 * @return results of JKind
	 * @throws JKindException
	 */
	public JKindResult execute(File lustreFile) {
		String text = null;
		File xmlFile = null;
		try {
			text = callJKind(lustreFile);
			xmlFile = getXmlFile(lustreFile);
			List<Property> properties = parseXmlFile(xmlFile);
			return new JKindResult(text, properties);
		} catch (Throwable t) {
			throw new JKindException(text, t);
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

	private String callJKind(File lustreFile) throws IOException {
		ProcessBuilder builder = getJKindProcessBuilder(lustreFile);
		Process process = null;
		StringBuilder result = new StringBuilder();
		try {
			process = builder.start();
			InputStream stream = new BufferedInputStream(process.getInputStream());
			int c;
			while ((c = stream.read()) != -1) {
				result.append((char) c);
			}
		} finally {
			if (process != null) {
				process.destroy();
			}
		}
		return result.toString();
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

	private static List<Property> parseXmlFile(File xmlFile) throws IOException {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			XmlHandler handler = new XmlHandler();
			saxParser.parse(xmlFile, handler);
			return handler.properties;
		} catch (ParserConfigurationException | SAXException e) {
			throw new JKindException("Error parsing XML", e);
		}
	}
}
