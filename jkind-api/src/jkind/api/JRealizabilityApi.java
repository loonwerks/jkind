package jkind.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private boolean reduce = false;
	private DebugLogger debug = new DebugLogger();

	private List<String> vmArgs = Collections.emptyList();

	private String jkindJar;
	private Map<String, String> environment = new HashMap<>();

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
	public void setExtendCounterexamples() {
		extendCounterexample = true;
	}

	/**
	 * Reduce conflicting properties in the case of 'unrealizable'
	 */
	public void setReduce() {
		reduce = true;
	}

	/**
	 * Put the RealizabilityApi into debug mode where it saves all output
	 */
	public void setApiDebug() {
		debug = new DebugLogger("jrealizability-api-debug-");
	}

	/**
	 * Print string to debug log (assuming setApiDebug() has been called)
	 * 
	 * @param text
	 *            text to print to debug log
	 */
	public void apiDebug(String text) {
		if (debug != null) {
			debug.println(text);
		}
	}

	/**
	 * Provide a fixed JKind jar file to use
	 */
	public void setJKindJar(String jkindJar) {
		if (!new File(jkindJar).exists()) {
			throw new JKindException("JKind jar file does not exist: " + jkindJar);
		}
		this.jkindJar = jkindJar;
	}

	/**
	 * Set an environment variable for the JRealizability process
	 */
	public void setEnvironment(String key, String value) {
		environment.put(key, value);
	}

	/**
	 * Set VM args
	 */
	public void setVmArgs(List<String> args) {
		vmArgs = new ArrayList<>(args);
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
			debug.deleteIfUnneeded(lustreFile);
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
		ApiUtil.execute(this::getJRealizabilityProcessBuilder, lustreFile, result, monitor, debug);
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
		if (reduce) {
			args.add("-reduce");
		}

		args.add(lustreFile.toString());

		ProcessBuilder builder = new ProcessBuilder(args);
		ApiUtil.addEnvironment(builder, environment);
		builder.redirectErrorStream(true);
		return builder;
	}

	protected String[] getJRealizabilityCommand() {
		List<String> args = new ArrayList<>();
		args.add(ApiUtil.getJavaPath());
		args.addAll(vmArgs);
		args.add("-jar");
		args.add(getOrFindJKindJar());
		args.add("-jrealizability");

		return args.toArray(new String[args.size()]);
	}

	private String getOrFindJKindJar() {
		if (jkindJar != null) {
			return jkindJar;
		} else {
			return ApiUtil.findJKindJar().toString();
		}
	}

	public void checkAvailable() throws Exception {
		List<String> args = new ArrayList<>();
		args.addAll(Arrays.asList(getJRealizabilityCommand()));
		args.add("-version");

		ProcessBuilder builder = new ProcessBuilder(args);
		ApiUtil.addEnvironment(builder, environment);
		builder.redirectErrorStream(true);
		Process process = builder.start();

		String output = ApiUtil.readAll(process.getInputStream());
		if (process.waitFor() != 0) {
			throw new JKindException("Error running JRealizability: " + output);
		}
	}
}