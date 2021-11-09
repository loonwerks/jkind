package jkind.api.eclipse;

import org.eclipse.core.runtime.IProgressMonitor;

import jkind.api.results.JKindResult;
import jkind.lustre.Program;

public abstract class KindApi extends jkind.api.KindApi {

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
		execute(program.toString(), result, new jkind.api.eclipse.ApiUtil.CancellationMonitor(monitor));
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
	@Override
	public void execute(String program, JKindResult result, IProgressMonitor monitor) {
		execute(program, result, new jkind.api.eclipse.ApiUtil.CancellationMonitor(monitor));
	}

}
