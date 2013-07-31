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

import jkind.lustre.Program;

import org.xml.sax.SAXException;

public class JKindApi {
	private Integer timeout = null;
	private Integer n = null;
	
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public void setN(int n) {
		this.n = n;
	}
	
	public JKindResult execute(Program program) throws IOException {
		return execute(program.toString());
	}
	
	public JKindResult execute(String program) throws IOException {
		String text = null;
		File lustreFile = null;
		File xmlFile = null;
		try {
			lustreFile = writeLustreFile(program);
			text = callJKind(lustreFile);
			xmlFile = getXmlFile(lustreFile);
			List<Property> properties = parseXmlFile(xmlFile);
			return new JKindResult(text, properties);
		} catch (Throwable t) {
			throw new JKindApiException(text, t);
		} finally {
			if (lustreFile != null && lustreFile.exists()) {
				lustreFile.delete();
			}
			if (xmlFile != null && xmlFile.exists()) {
				xmlFile.delete();
			}
		}
	}

	private static File writeLustreFile(String program) throws IOException {
		File file = File.createTempFile("jkind-api", ".lus");
		writeToFile(program, file);
		return file;
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
		} catch (ParserConfigurationException e) {
			throw new JKindApiException("Error parsing XML", e);
		} catch (SAXException e) {
			throw new JKindApiException("Error parsing XML", e);
		}
	}

	// This method exists to allow Eclipse to export an api jar more easily
	public static void main(String[] args) {
	}
}
