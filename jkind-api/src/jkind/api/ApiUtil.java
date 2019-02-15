package jkind.api;

import static java.util.stream.Collectors.joining;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import org.eclipse.core.runtime.IProgressMonitor;

import jkind.JKindException;
import jkind.api.results.JKindResult;
import jkind.api.xml.JKindXmlFileInputStream;
import jkind.api.xml.XmlParseThread;
import jkind.util.Util;

public class ApiUtil {

	public static File writeLustreFile(String program) {
		return writeTempFile("jkind-api-", ".lus", program);
	}

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

	public static void execute(Function<File, ProcessBuilder> runCommand, File lustreFile,
			JKindResult result, IProgressMonitor monitor, DebugLogger debug) {
		File xmlFile = null;
		try {
			xmlFile = getXmlFile(lustreFile);
			debug.println("XML results file", xmlFile);
			System.out.println("XML results file\n" + xmlFile);
			ensureDeleted(xmlFile);
			callJKind(runCommand, lustreFile, xmlFile, result, monitor, debug);
		} catch (JKindException e) {
			throw e;
		} catch (Throwable t) {
			throw new JKindException(result.getText(), t);
		} finally {
			debug.deleteIfUnneeded(xmlFile);
			debug.println();
		}
	}

	private static void ensureDeleted(File file) {
		if (file != null && file.exists()) {
			file.delete();
			if (file.exists()) {
				throw new JKindException("Unable to delete file: " + file);
			}
		}
	}

	private static File getXmlFile(File lustreFile) {
		return new File(lustreFile.toString() + ".xml");
	}

	private static void callJKind(Function<File, ProcessBuilder> runCommand, File lustreFile,
			File xmlFile, JKindResult result, IProgressMonitor monitor, DebugLogger debug)
			throws IOException, InterruptedException {
		ProcessBuilder builder = runCommand.apply(lustreFile);
		debug.println("JKind command: " + ApiUtil.getQuotedCommand(builder.command()));
		System.out.println("JKind command: " + ApiUtil.getQuotedCommand(builder.command()));
		Process process = null;
		try (JKindXmlFileInputStream xmlStream = new JKindXmlFileInputStream(xmlFile)) {
			XmlParseThread parseThread = new XmlParseThread(xmlStream, result, Backend.JKIND);

			try {
				result.start();
				process = builder.start();
				parseThread.start();
				String output = ApiUtil.readOutput(process, monitor);
				result.setText(output);
				debug.println("JKind output", debug.saveFile("jkind-output-", ".txt", output));
			} finally {
				int code = 0;
				if (process != null) {
					if (monitor.isCanceled() && process.isAlive()) {
						// Only destroy the process if we have to since it can
						// change the exit code on Windows
						process.destroy();
					}
					code = process.waitFor();
				}

				xmlStream.done();
				parseThread.join();

				if (monitor.isCanceled()) {
					result.cancel();
				} else {
					result.done();
				}
				monitor.done();

				if (code != 0 && !monitor.isCanceled()) {
					throw new JKindException("Abnormal termination, exit code " + code
							+ System.lineSeparator() + result.getText());
				}
			}

			if (parseThread.getThrowable() != null) {
				throw new JKindException("Error parsing XML", parseThread.getThrowable());
			}
		}
	}

	public static String readOutput(Process process, IProgressMonitor monitor) throws IOException {
		InputStream stream = new BufferedInputStream(process.getInputStream());
		StringBuilder text = new StringBuilder();

		while (true) {
			if (!process.isAlive()) {
				return text.toString();
			}

			checkMonitor(monitor, process);
			while (stream.available() > 0) {
				int c = stream.read();
				if (c == -1) {
					return text.toString();
				}
				text.append((char) c);
				checkMonitor(monitor, process);
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	private static void checkMonitor(IProgressMonitor monitor, Process process) throws IOException {
		if (monitor.isCanceled()) {
			process.getOutputStream().write(Util.END_OF_TEXT);
			process.getOutputStream().flush();
		}
	}

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

	public static String getJavaPath() {
		String slash = File.separator;
		return System.getProperty("java.home") + slash + "bin" + slash + "java";
	}

	public static String readAll(InputStream inputStream) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedInputStream buffered = new BufferedInputStream(inputStream);
		int i;
		while ((i = buffered.read()) != -1) {
			result.append((char) i);
		}
		return result.toString();
	}

	public static String getQuotedCommand(List<String> pieces) {
		return pieces.stream().map(p -> p.contains(" ") ? "\"" + p + "\"" : p)
				.collect(joining(" "));
	}

	public static void addEnvironment(ProcessBuilder builder, Map<String, String> environment) {
		for (Entry<String, String> entry : environment.entrySet()) {
			builder.environment().put(entry.getKey(), entry.getValue());
		}
	}
}
