package jkind.api.simple;

import static java.util.stream.Collectors.joining;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import jkind.JKindException;
import jkind.api.Backend;
import jkind.api.results.JKindResult;
import jkind.api.simple.xml.JKindXmlFileInputStream;
import jkind.api.simple.xml.XmlParseThread;
import jkind.util.Util;

/**
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
public class ApiUtil {
	@Deprecated
	public static File writeLustreFile(String program) {
		return writeTempFile("jkind-api-", ".lus", program);
	}

	@Deprecated
	public static File writeTempFile(String fileName, String fileExt, String contents) {
		File file = null;
		try {
			file = File.createTempFile(fileName, fileExt);
			if (contents != null) {
				Util.writeToFile(contents, file);
			}
			return file;
		} catch (IOException e) {
			throw new JKindException("Cannot write to file: " + file, e);
		}
	}

	@Deprecated
	public static void execute(Function<File, ProcessBuilder> runCommand, File lustreFile, JKindResult result,
			DebugLogger debug) {
		File xmlFile = null;
		try {
			xmlFile = getXmlFile(lustreFile);
			debug.println("XML results file", xmlFile);
			ensureDeleted(xmlFile);
			callJKind(runCommand, lustreFile, xmlFile, result, debug);
		} catch (JKindException e) {
			throw e;
		} catch (Throwable t) {
			throw new JKindException(result.getText(), t);
		} finally {
			debug.deleteIfUnneeded(xmlFile);
			debug.println();
		}
	}

	@Deprecated
	private static void ensureDeleted(File file) {
		if (file != null && file.exists()) {
			file.delete();
			if (file.exists()) {
				throw new JKindException("Unable to delete file: " + file);
			}
		}
	}

	@Deprecated
	private static File getXmlFile(File lustreFile) {
		return new File(lustreFile.toString() + ".xml");
	}

	@Deprecated
	private static void callJKind(Function<File, ProcessBuilder> runCommand, File lustreFile, File xmlFile,
			JKindResult result, DebugLogger debug) throws IOException, InterruptedException {
		ProcessBuilder builder = runCommand.apply(lustreFile);
		debug.println("JKind command: " + ApiUtil.getQuotedCommand(builder.command()));
		Process process = null;
		try (JKindXmlFileInputStream xmlStream = new JKindXmlFileInputStream(xmlFile)) {
			XmlParseThread parseThread = new XmlParseThread(xmlStream, result, Backend.JKIND);
			StringBuilder outputText = new StringBuilder();
			StringBuilder errorText = new StringBuilder();

			try {
				result.start();
				process = builder.start();
				parseThread.start();
				ApiUtil.readOutputToBuilder(process, outputText, errorText);
			} finally {
				int code = 0;
				if (process != null) {
					if (process.isAlive()) {
						// Only destroy the process if we have to since it can
						// change the exit code on Windows
						process.destroy();
					}
					code = process.waitFor();
				}

				xmlStream.done();
				parseThread.join();

				String output = outputText.toString();
				String errors = errorText.toString();
				result.setText(errors + output);
				debug.println("JKind output", debug.saveFile("jkind-output-", ".txt", errors + output));

				result.done();

				if (code != 0) {
					throw new JKindException(
							"Abnormal termination, exit code " + code + System.lineSeparator() + result.getText());
				}
			}

			if (parseThread.getThrowable() != null) {
				throw new JKindException("Error parsing XML", parseThread.getThrowable());
			}
		}
	}

	@Deprecated
	public static String readOutput(Process process) throws IOException {
		StringBuilder outputText = new StringBuilder();
		StringBuilder errorText = new StringBuilder();
		readOutputToBuilder(process, outputText, errorText);
		return errorText.toString() + outputText.toString();
	}

	@Deprecated
	public static void readOutputToBuilder(Process process, StringBuilder outputText,
			StringBuilder errorText)
			throws IOException {
		InputStream stream = new BufferedInputStream(process.getInputStream());
		InputStream errorStream = new BufferedInputStream(process.getErrorStream());

		while (true) {
			while (stream.available() > 0) {
				int c = stream.read();
				if (c == -1) {
					return;
				}
				outputText.append((char) c);
			}
			while (errorStream.available() > 0) {
				int c = errorStream.read();
				if (c == -1) {
					return;
				}
				errorText.append((char) c);
			}

			if (!process.isAlive()) {
				return;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	@Deprecated
	public static File findJKindJar() {
		/*
		 * On Windows, invoking Process.destroy does not kill the subprocesses
		 * of the destroyed process. If we were to run jkind.bat and kill it,
		 * only the cmd.exe process which is running the batch file would be
		 * killed. The underlying JKind process would continue to its natural
		 * end. To avoid this, we search the user's path for the jkind.jar file
		 * and invoke it directly.
		 *
		 * In order to support JKIND_HOME or PATH as the location for JKind, we
		 * now search in non-windows environments too.
		 */

		File jar = findJKindJar(System.getenv("JKIND_HOME"));
		if (jar != null) {
			return jar;
		}

		jar = findJKindJar(System.getenv("PATH"));
		if (jar != null) {
			return jar;
		}

		throw new JKindException("Unable to find jkind.jar in JKIND_HOME or on system PATH");
	}

	@Deprecated
	public static File findJKindJar(String path) {
		if (path == null) {
			return null;
		}

		for (String element : path.split(File.pathSeparator)) {
			File jar = new File(element, "jkind.jar");
			if (jar.exists()) {
				return jar;
			}
		}

		return null;
	}

	@Deprecated
	public static String getJavaPath() {
		String slash = File.separator;
		return System.getProperty("java.home") + slash + "bin" + slash + "java";
	}

	@Deprecated
	public static String readAll(InputStream inputStream) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedInputStream buffered = new BufferedInputStream(inputStream);
		int i;
		while ((i = buffered.read()) != -1) {
			result.append((char) i);
		}
		return result.toString();
	}

	@Deprecated
	public static String getQuotedCommand(List<String> pieces) {
		return pieces.stream().map(p -> p.contains(" ") ? "\"" + p + "\"" : p).collect(joining(" "));
	}

	@Deprecated
	public static void addEnvironment(ProcessBuilder builder, Map<String, String> environment) {
		for (Entry<String, String> entry : environment.entrySet()) {
			builder.environment().put(entry.getKey(), entry.getValue());
		}
	}
}
