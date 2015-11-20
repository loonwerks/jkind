package jkind.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jkind.JKindException;
import jkind.api.results.JKindResult;
import jkind.api.workarounds.WorkaroundKind2ForwardReference;
import jkind.api.xml.XmlParseThread;
import jkind.lustre.Program;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * The primary interface to Kind2.
 */
public class Kind2Api extends KindApi {
	public static final String KIND2 = "kind2-contracts";
	private static final long POLL_INTERVAL = 100;
	
	/**
	 * Run Kind on a Lustre program
	 * 
	 * @param program
	 *            Lustre program
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 */
	@Override
	public void execute(Program program, JKindResult result, IProgressMonitor monitor) {
		program = WorkaroundKind2ForwardReference.program(program);
		execute(program.toString(), result, monitor);
	}
	
	/**
	 * Run Kind2 on a Lustre program
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
		try {
			callKind2(lustreFile, result, monitor);
		} catch (JKindException e) {
			throw e;
		} catch (Throwable t) {
			throw new JKindException(result.getText(), t);
		}
	}

	private void callKind2(File lustreFile, JKindResult result, IProgressMonitor monitor)
			throws IOException, InterruptedException {
		ProcessBuilder builder = getKind2ProcessBuilder(lustreFile);
		debug.println("Kind 2 command: " + ApiUtil.getQuotedCommand(builder.command()));
		Process process = null;
		XmlParseThread parseThread = null;
		int code = 0;

		try {
			result.start();
			process = builder.start();
			parseThread = new XmlParseThread(process.getInputStream(), result, Backend.KIND2);
			parseThread.start();
			while (!monitor.isCanceled() && parseThread.isAlive()) {
				sleep(POLL_INTERVAL);
			}
		} finally {
			if (process != null) {
				process.destroy();
				code = process.waitFor();
			}

			if (parseThread != null) {
				parseThread.join();
			}

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

	private ProcessBuilder getKind2ProcessBuilder(File lustreFile) {
		List<String> args = new ArrayList<>();
		args.add(KIND2);
		args.addAll(getArgs());
		args.add(lustreFile.toString());

		ProcessBuilder builder = new ProcessBuilder(args);
		builder.redirectErrorStream(true);
		return builder;
	}

	protected List<String> getArgs() {
		List<String> args = new ArrayList<>();
		args.add("-xml");
		args.add("-v");
		if (timeout != null) {
			args.add("--timeout_wall");
			args.add(timeout.toString());
		}
		return args;
	}

	protected void sleep(long interval) {
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
		}
	}

	@Override
	public String checkAvailable() throws Exception {
		ProcessBuilder builder = new ProcessBuilder(KIND2, "--version");
		builder.redirectErrorStream(true);
		Process process = builder.start();

		String output = ApiUtil.readAll(process.getInputStream());
		if (process.exitValue() != 0) {
			throw new JKindException("Error running kind2: " + output);
		}
		return output;
	}
}
