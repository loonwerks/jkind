package jkind.api;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jkind.JKindException;
import jkind.SolverOption;
import jkind.api.results.JKindResult;
import jkind.api.xml.JKindXmlFileInputStream;
import jkind.api.xml.XmlParseThread;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * The primary interface to JKind.
 */
public class JKindApi extends KindApi {
	protected Integer n = null;
	protected boolean inductiveCounterexamples = false;
	protected boolean reduceInvariants = false;
	protected boolean smoothCounterexamples = false;
	protected boolean intervalGeneralization = false;
	protected SolverOption solver = null;

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
	 * Set the solver to use (Yices, Z3, CVC4)
	 */
	public void setSolver(SolverOption solver) {
		this.solver = solver;
	}

	/**
	 * Reduce and report the invariants used for a valid property
	 */
	public void setReduceInvariants() {
		reduceInvariants = true;
	}

	/**
	 * Post-process counterexamples to have minimal input value changes
	 */
	public void setSmoothCounterexamples() {
		smoothCounterexamples = true;
	}

	/**
	 * Post-process counterexamples using interval analysis to make them more
	 * general
	 */
	public void setIntervalGeneralization() {
		intervalGeneralization = true;
	}

	/**
	 * Run JKind on a Lustre program
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
	public void execute(File lustreFile, JKindResult result, IProgressMonitor monitor) {
		File xmlFile = null;
		try {
			xmlFile = getXmlFile(lustreFile);
			safeDelete(xmlFile);
			if (xmlFile.exists()) {
				throw new JKindException("Existing XML file cannot be removed: " + xmlFile);
			}
			callJKind(lustreFile, xmlFile, result, monitor);
		} catch (JKindException e) {
			throw e;
		} catch (Throwable t) {
			throw new JKindException(result.getText(), t);
		} finally {
			safeDelete(xmlFile);
		}
	}

	private void callJKind(File lustreFile, File xmlFile, JKindResult result,
			IProgressMonitor monitor) throws IOException, InterruptedException {
		ProcessBuilder builder = getJKindProcessBuilder(lustreFile);
		Process process = null;
		try (JKindXmlFileInputStream xmlStream = new JKindXmlFileInputStream(xmlFile)) {
			XmlParseThread parseThread = new XmlParseThread(xmlStream, result, Backend.JKIND);

			try {
				result.start();
				process = builder.start();
				parseThread.start();
				readOutput(process, result, monitor);
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

				if (code != 0 && !monitor.isCanceled()) {
					throw new JKindException("Abnormal termination, exit code " + code);
				}
			}

			if (parseThread.getThrowable() != null) {
				throw new JKindException("Error parsing XML", parseThread.getThrowable());
			}
		}
	}

	private void readOutput(Process process, final JKindResult result, IProgressMonitor monitor)
			throws IOException {
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

	private ProcessBuilder getJKindProcessBuilder(File lustreFile) {
		List<String> args = new ArrayList<>();
		args.addAll(Arrays.asList(getJKindCommand()));
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
		if (solver != null) {
			args.add("-solver");
			args.add(solver.toString());
		}
		if (reduceInvariants) {
			args.add("-reduce_inv");
		}
		if (smoothCounterexamples) {
			args.add("-smooth");
		}
		if (intervalGeneralization) {
			args.add("-interval");
		}

		args.add(lustreFile.toString());

		ProcessBuilder builder = new ProcessBuilder(args);
		builder.redirectErrorStream(true);
		return builder;
	}

	private String[] getJKindCommand() {
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

		File jar = findJKindJar();
		if (jar == null) {
			throw new JKindException("Unable to find jkind-functions.jar in JKIND_HOME or on system PATH");
		}
		return new String[] { "java", "-jar", jar.toString(), "-jkind" };
	}

	private File findJKindJar() {
		File jar = findJKindJar(System.getenv("JKIND_HOME"));
		if (jar == null) {
			jar = findJKindJar(System.getenv("PATH"));
		}
		return jar;
	}

	private File findJKindJar(String path) {
		if (path == null) {
			return null;
		}

		for (String element : path.split(File.pathSeparator)) {
			File jar = new File(element, "jkind-functions.jar");
			if (jar.exists()) {
				return jar;
			}
		}

		return null;
	}

	private static File getXmlFile(File lustreFile) {
		return new File(lustreFile.toString() + ".xml");
	}

	@Override
	public void checkAvailable() throws Exception {
		new ProcessBuilder(getJKindCommand()).start().waitFor();
	}
}
