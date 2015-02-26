package jkind.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;

import jkind.JKindException;
import jkind.api.results.JKindResult;
import jkind.api.results.Status;
import jkind.api.xml.JKindXmlFileInputStream;
import jkind.api.xml.XmlParseThread;
import jkind.util.Util;

import org.eclipse.core.runtime.IProgressMonitor;

public class ApiUtil {
	public static File writeLustreFile(String program) {
		File file = null;
		try {
			file = File.createTempFile("jkind-api", ".lus");
			Util.writeToFile(program, file);
			return file;
		} catch (IOException e) {
			safeDelete(file);
			throw new JKindException("Cannot write to file: " + file, e);
		}
	}

	public static void safeDelete(File file) {
		if (file != null && file.exists()) {
			file.delete();
		}
	}

	public static void execute(Function<File, ProcessBuilder> runCommand, File lustreFile,
			JKindResult result, IProgressMonitor monitor) {
		File xmlFile = null;
		try {
			xmlFile = getXmlFile(lustreFile);
			ApiUtil.safeDelete(xmlFile);
			if (xmlFile.exists()) {
				throw new JKindException("Existing XML file cannot be removed: " + xmlFile);
			}
			callJKind(runCommand, lustreFile, xmlFile, result, monitor);
		} catch (JKindException e) {
			throw e;
		} catch (Throwable t) {
			throw new JKindException(result.getText(), t);
		} finally {
			ApiUtil.safeDelete(xmlFile);
		}
	}

	private static File getXmlFile(File lustreFile) {
		return new File(lustreFile.toString() + ".xml");
	}

	private static void callJKind(Function<File, ProcessBuilder> runCommand, File lustreFile,
			File xmlFile, JKindResult result, IProgressMonitor monitor) throws IOException,
			InterruptedException {
		ProcessBuilder builder = runCommand.apply(lustreFile);
		Process process = null;
		try (JKindXmlFileInputStream xmlStream = new JKindXmlFileInputStream(xmlFile)) {
			XmlParseThread parseThread = new XmlParseThread(xmlStream, result, Backend.JKIND);

			try {
				result.start();
				process = builder.start();
				parseThread.start();
				result.setText(ApiUtil.readOutput(process, result, monitor));
			} finally {
				int code = 0;
				if (process != null) {
					process.destroy();
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

				Status status = result.getMultiStatus().getOverallStatus();

				if (code != 0 && !monitor.isCanceled() && status != Status.CANCELED) {
				    throw new JKindException("Abnormal termination, exit code " + code);
				}
			}

			if (parseThread.getThrowable() != null) {
			    throw new JKindException("Error parsing XML", parseThread.getThrowable());
			}
		}
	}

	public static String readOutput(Process process, JKindResult result, IProgressMonitor monitor) throws IOException {
		InputStream stream = new BufferedInputStream(process.getInputStream());
		StringBuilder text = new StringBuilder();

		while (true) {
		    Status status = result.getMultiStatus().getOverallStatus();
			if (monitor.isCanceled()) {
				return text.toString();
			}else if(status != null && status == Status.CANCELED){
			    return text.toString();
			} else if (stream.available() > 0) {
				int c = stream.read();
				if (c == -1) {
					return text.toString();
				}
				text.append((char) c);
			} else if (isTerminated(process)) {
				return text.toString();
			} else {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	public static boolean isTerminated(Process process) {
		try {
			process.exitValue();
			return true;
		} catch (IllegalThreadStateException e) {
			return false;
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

	public static String readAll(InputStream inputStream) throws IOException {
		StringBuilder result = new StringBuilder();
		BufferedInputStream buffered = new BufferedInputStream(inputStream);
		int i;
		while ((i = buffered.read()) != -1) {
			result.append((char) i);
		}
		return result.toString();
	}
}
