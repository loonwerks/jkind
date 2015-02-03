package jkind.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jkind.JKindException;
import jkind.api.results.JRealizabilityResult;
import jkind.lustre.Program;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * The primary interface to JRealizability.
 */
public class JRealizabilityApi {
	private Integer timeout = null;
	private Integer n = null;
	private boolean extendCounterexample = false;

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
	 * Set a maximum path length in realizability checking algorithm
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
	 * Produce extend counterexample if realizability is 'unknown'
	 */
	public void setInductiveCounterexamples() {
		extendCounterexample = true;
	}

	/**
	 * Run JRealizability on a Lustre program
	 * 
	 * @param program
	 *            Lustre program
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 */
	public void execute(Program program, JRealizabilityResult result, IProgressMonitor monitor) {
		execute(program.toString(), result, monitor);
	}

	/**
	 * Run JRealizability on a Lustre program
	 * 
	 * @param program
	 *            Lustre program as text
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 */
	public void execute(String program, JRealizabilityResult result, IProgressMonitor monitor) {
		File lustreFile = null;
		try {
			lustreFile = ApiUtil.writeLustreFile(program);
			execute(lustreFile, result, monitor);
		} finally {
			ApiUtil.safeDelete(lustreFile);
		}
	}

	/**
	 * Run JRealizability on a Lustre program
	 * 
	 * @param lustreFile
	 *            File containing Lustre program
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 */
	public void execute(File lustreFile, JRealizabilityResult result, IProgressMonitor monitor) {
		ApiUtil.execute(this::getJRealizabilityProcessBuilder, lustreFile, result, monitor);
	}

	private ProcessBuilder getJRealizabilityProcessBuilder(File lustreFile) {
		List<String> args = new ArrayList<>();
		args.addAll(Arrays.asList(getJRealizabilityCommand()));
		args.add("-xml");
		if (timeout != null) {
			args.add("-timeout");
			args.add(timeout.toString());
		}
		if (n != null) {
			args.add("-n");
			args.add(n.toString());
		}
		if (extendCounterexample) {
			args.add("-extend_cex");
		}

		args.add(lustreFile.toString());

		ProcessBuilder builder = new ProcessBuilder(args);
		builder.redirectErrorStream(true);
		return builder;
	}

	private static String[] getJRealizabilityCommand() {
		return new String[] { "java", "-jar", ApiUtil.findJKindJar().toString(), "-jrealizability" };
	}

	public void checkAvailable() throws Exception {
		List<String> args = new ArrayList<>();
		args.addAll(Arrays.asList(getJRealizabilityCommand()));
		args.add("-version");

		ProcessBuilder builder = new ProcessBuilder(args);
		builder.redirectErrorStream(true);
		Process process = builder.start();

		String output = ApiUtil.readAll(process.getInputStream());
		if (process.exitValue() != 0) {
			throw new JKindException("Error running JRealizability: " + output);
		}
	}
}