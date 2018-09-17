package jkind.api;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jkind.JKindException;
import jkind.SolverOption;
import jkind.api.results.JKindResult;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * The primary interface to JKind.
 */
public class JKindApi extends KindApi {
	protected Integer n = null;

	protected boolean boundedModelChecking = true;
	protected boolean kInduction = true;
	protected boolean invariantGeneration = true;
	protected Integer pdrMax = null;
	protected boolean inductiveCounterexamples = false;
	protected boolean ivcReduction = false;
	protected boolean allIvcs = false;
	protected boolean smoothCounterexamples = false;
	protected boolean slicing = true;

	protected SolverOption solver = null;

	protected String jkindJar;
	protected Map<String, String> environment = new HashMap<>();
	protected String readAdviceFileName = null;
	protected String writeAdviceFileName = null;

	/**
	 * Set the maximum depth for BMC and k-induction
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

	public void disableBoundedModelChecking() {
		boundedModelChecking = false;
	}

	public void disableKInduction() {
		kInduction = false;
	}

	public void disableInvariantGeneration() {
		invariantGeneration = false;
	}

	/**
	 * Set the maximum number of PDR instances to run
	 * 
	 * @param pdrMax
	 *            A non-negative integer
	 */
	public void setPdrMax(int pdrMax) {
		if (pdrMax < 0) {
			throw new JKindException("pdrMax must be positive");
		}
		this.pdrMax = pdrMax;
	}

	/**
	 * Produce inductive counterexamples for 'unknown' properties
	 */
	public void setInductiveCounterexamples() {
		inductiveCounterexamples = true;
	}

	/**
	 * Set the solver to use (Yices, Z3, CVC4, ...)
	 */
	public void setSolver(SolverOption solver) {
		this.solver = solver;
	}

	/**
	 * Find an inductive validity core for valid properties
	 */
	public void setIvcReduction() {
		ivcReduction = true;
	}
	
	/**
	 * Find all inductive validity cores for valid properties
	 */
	public void setAllIvcs() {
		allIvcs = true;
	}

	/**
	 * Post-process counterexamples to have minimal input value changes
	 */
	public void setSmoothCounterexamples() {
		smoothCounterexamples = true;
	}

	/**
	 * Disable slicing of input model and counterexamples
	 */
	public void disableSlicing() {
		slicing = false;
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
	 * Set an environment variable for the JKind process
	 */
	public void setEnvironment(String key, String value) {
		environment.put(key, value);
	}

	/*
	 * Set the advice file to be read
	 */
	public void setReadAdviceFile(String fileName) {
		readAdviceFileName = fileName;
	}

	/*
	 * Set the advice file to be written
	 */
	public void setWriteAdviceFile(String fileName) {
		writeAdviceFileName = fileName;
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
		debug.println("Lustre file", lustreFile);
		ApiUtil.execute(this::getJKindProcessBuilder, lustreFile, result, monitor, debug);
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
		if (!boundedModelChecking) {
			args.add("-no_bmc");
		}
		if (!kInduction) {
			args.add("-no_k_induction");
		}
		if (!invariantGeneration) {
			args.add("-no_inv_gen");
		}
		if (pdrMax != null) {
			args.add("-pdr_max");
			args.add(pdrMax.toString());
		}
		if (inductiveCounterexamples) {
			args.add("-induct_cex");
		}
		if (ivcReduction) {
			args.add("-ivc");
		}
		if (allIvcs) {
			args.add("-all_ivcs");
		}
		if (smoothCounterexamples) {
			args.add("-smooth");
		}
		if (!slicing) {
			args.add("-no_slicing");
		}
		if (solver != null) {
			args.add("-solver");
			args.add(solver.toString());
		}
		String tempDir = System.getProperty("java.io.tmpdir");
		if (readAdviceFileName != null) {
			args.add("-read_advice");
			args.add(new File(tempDir, readAdviceFileName).getAbsolutePath());
		}
		if (writeAdviceFileName != null) {
			args.add("-write_advice");
			args.add(new File(tempDir, writeAdviceFileName).getAbsolutePath());
		}

		args.add(lustreFile.toString());

		ProcessBuilder builder = new ProcessBuilder(args);
		ApiUtil.addEnvironment(builder, environment);
		builder.redirectErrorStream(true);
		return builder;
	}

	protected String[] getJKindCommand() {
		return new String[] { ApiUtil.getJavaPath(), "-jar", getOrFindJKindJar(), "-jkind" };
	}

	private String getOrFindJKindJar() {
		if (jkindJar != null) { 
			return jkindJar; 
		} else {  
			return ApiUtil.findJKindJar().toString();
		}
	}

	@Override
	public String checkAvailable() throws Exception {
		List<String> args = new ArrayList<>();
		args.addAll(Arrays.asList(getJKindCommand()));
		args.add("-version");

		ProcessBuilder builder = new ProcessBuilder(args);
		ApiUtil.addEnvironment(builder, environment);
		builder.redirectErrorStream(true);
		Process process = builder.start();

		String output = ApiUtil.readAll(process.getInputStream());
		if (process.waitFor() != 0) {
			throw new JKindException("Error running JKind: " + output);
		}
		return output;
	}
}
