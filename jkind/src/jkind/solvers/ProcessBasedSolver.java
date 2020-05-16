package jkind.solvers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jkind.JKindException;

public abstract class ProcessBasedSolver extends Solver {
	protected static final String DONE = "@DONE";

	protected Process process;
	protected BufferedWriter toSolver;
	protected BufferedReader fromSolver;
	protected PrintWriter scratch;

	protected ProcessBasedSolver(String scratchBase) {
		this.scratch = getScratch(scratchBase);

		ProcessBuilder processBuilder = new ProcessBuilder(getSolverCommand());
		processBuilder.redirectErrorStream(true);
		try {
			process = processBuilder.start();
		} catch (IOException e) {
			throw new JKindException("Unable to start solver by executing: " + processBuilder.command().get(0), e);
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

	private List<String> getSolverCommand() {
		List<String> command = new ArrayList<>();
		command.add(getSolverPath());
		command.addAll(Arrays.asList(getSolverOptions()));
		return command;
	}

	private String getSolverPath() {
		String executable = getSolverExecutable();
		String home = System.getenv(getSolverHomeVariable());
		if (home != null) {
			return new File(getBinDir(home), executable).toString();
		}
		return executable;
	}

	private static File getBinDir(String homeString) {
		File home = new File(homeString);
		File bin = new File(home, "bin");
		if (bin.isDirectory()) {
			return bin;
		} else {
			return home;
		}
	}

	protected abstract String getSolverName();

	protected abstract String getSolverExtension();

	protected String getSolverExecutable() {
		return getSolverName().toLowerCase();
	}

	protected String[] getSolverOptions() {
		return new String[0];
	}

	protected String getSolverHomeVariable() {
		return getSolverName().toUpperCase() + "_HOME";
	}

	private final Thread shutdownHook = new Thread("shutdown-hook") {
		@Override
		public void run() {
			ProcessBasedSolver.this.stop();
		}
	};

	private void addShutdownHook() {
		Runtime.getRuntime().addShutdownHook(shutdownHook);
	}

	private void removeShutdownHook() {
		try {
			Runtime.getRuntime().removeShutdownHook(shutdownHook);
		} catch (IllegalStateException e) {
			// Ignore, we are already shutting down
		}
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

		removeShutdownHook();
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
