package jkind.api.eclipse;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import jkind.api.results.JKindResult;
import jkind.lustre.Program;

public class SallyApi extends jkind.api.SallyApi {

	/**
	 * Run Sally on a Lustre program
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
		execute(program, result, new jkind.api.eclipse.ApiUtil.CancellationMonitor(monitor));
	}

	/**
	 * Run Sally on a Lustre program
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
		execute(lustreFile, result, new jkind.api.eclipse.ApiUtil.CancellationMonitor(monitor));
	}

}
