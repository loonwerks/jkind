package jkind.api;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import jkind.JKindException;
import jkind.api.ApiUtil.ICancellationMonitor;
import jkind.api.results.JKindResult;
import jkind.lustre.Program;

public abstract class KindApi {
	protected Integer timeout = null;
	protected DebugLogger debug = new DebugLogger();

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
	 * Put the KindApi into debug mode where it saves all output
	 */
	public void setApiDebug() {
		debug = new DebugLogger("jkind-api-debug-");
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
	 * Run Kind on a Lustre program
	 *
	 * @param program
	 *            Lustre program
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 * @deprecated To be removed in 5.0.
	 *   Use {@link jkind.api.eclipse.KindApi.execute()} instead.
	 */
	@Deprecated
	public void execute(Program program, JKindResult result, IProgressMonitor monitor) {
		execute(program.toString(), result, new jkind.api.eclipse.ApiUtil.CancellationMonitor(monitor));
	}

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
	public void execute(Program program, JKindResult result, ICancellationMonitor monitor) {
		execute(program.toString(), result, monitor);
	}

	/**
	 * Run Kind on a Lustre program
	 *
	 * @param program
	 *            Lustre program as text
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 * @deprecated To be removed in 5.0.
	 *   Use {@link jkind.api.eclipse.KindApi.execute()} instead.
	 */
	@Deprecated
	public void execute(String program, JKindResult result, IProgressMonitor monitor) {
		execute(program, result, new jkind.api.eclipse.ApiUtil.CancellationMonitor(monitor));
	}

	/**
	 * Run Kind on a Lustre program
	 *
	 * @param program
	 *            Lustre program as text
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 */
	public void execute(String program, JKindResult result, ICancellationMonitor monitor) {
		File lustreFile = null;
		try {
			lustreFile = ApiUtil.writeLustreFile(program);
			execute(lustreFile, result, monitor);
		} finally {
			debug.deleteIfUnneeded(lustreFile);
		}
	}

	/**
	 * Run Kind on a Lustre program
	 *
	 * @param lustreFile
	 *            File containing Lustre program
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 * @deprecated To be removed in 5.0.
	 *   Use {@link jkind.api.eclipse.KindApi.execute()} instead.
	 */
	@Deprecated
	public abstract void execute(File lustreFile, JKindResult result, IProgressMonitor monitor);

	/**
	 * Run Kind on a Lustre program
	 *
	 * @param lustreFile
	 *            File containing Lustre program
	 * @param result
	 *            Place to store results as they come in
	 * @param monitor
	 *            Used to check for cancellation
	 * @throws jkind.JKindException
	 */
	public abstract void execute(File lustreFile, JKindResult result, ICancellationMonitor monitor);

	/**
	 * Check if the KindApi is available for running and throw exception if not
	 *
	 * @return Availability information when Kind is available
	 * @throws java.lang.Exception
	 *             When Kind is not available
	 */
	public abstract String checkAvailable() throws Exception;
}
