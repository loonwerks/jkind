package jkind.solvers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import jkind.JKindException;

public abstract class ProcessBasedSolver extends Solver {
	protected final static String DONE = "@DONE";

	protected Process process;
	protected BufferedWriter toSolver;
	protected BufferedReader fromSolver;
	protected PrintWriter scratch;

	protected ProcessBasedSolver(String scratchBase, ProcessBuilder processBuilder) {
		this.scratch = getScratch(scratchBase);

		processBuilder.redirectErrorStream(true);
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			throw new JKindException("Unable to start solver", e);
		}
		addShutdownHook();
		toSolver = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
		fromSolver = new BufferedReader(new InputStreamReader(process.getInputStream()));
	}

	private PrintWriter getScratch(String scratchBase) {
		if (scratchBase == null) {
			return null;
		}

		String filename = scratchBase + "." + getSolverExtension();
		try {
			return new PrintWriter(new FileOutputStream(filename), true);
		} catch (FileNotFoundException e) {
			throw new JKindException("Unable to open scratch file: " + filename, e);
		}
	}

	protected abstract String getSolverExtension();

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread("shutdown-hook") {
			@Override
			public void run() {
				ProcessBasedSolver.this.stop();
			}
		});
	}

	@Override
	public synchronized void stop() {
		/**
		 * This must be synchronized since two threads (an Engine or a shutdown
		 * hook) may try to stop the solver at the same time
		 */

		if (process != null) {
			process.destroy();
			process = null;
		}

		if (scratch != null) {
			scratch.close();
			scratch = null;
		}
	}

	public void scratch(String str) {
		if (scratch != null) {
			scratch.println(str);
		}
	}

	@Override
	public void comment(String str) {
		scratch("; " + str);
	}
}
