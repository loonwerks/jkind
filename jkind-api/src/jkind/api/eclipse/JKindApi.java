package jkind.api.eclipse;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;

import jkind.api.results.JKindResult;

public class JKindApi extends jkind.api.JKindApi {

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
		execute(lustreFile, result, new jkind.api.eclipse.ApiUtil.CancellationMonitor(monitor));
	}

}
