package jkind.api.eclipse;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import jkind.api.results.JRealizabilityResult;
import jkind.lustre.Program;

public class JRealizabilityApi extends jkind.api.JRealizabilityApi {

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
	@Override
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
	@Override
	public void execute(String program, JRealizabilityResult result, IProgressMonitor monitor) {
		execute(program, result, new jkind.api.eclipse.ApiUtil.CancellationMonitor(monitor));
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
	@Override
	public void execute(File lustreFile, JRealizabilityResult result, IProgressMonitor monitor) {
		execute(lustreFile, result, new jkind.api.eclipse.ApiUtil.CancellationMonitor(monitor));
	}

}
