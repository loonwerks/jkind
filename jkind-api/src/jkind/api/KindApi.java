package jkind.api;

import java.io.File;
import java.io.IOException;

import jkind.JKindException;
import jkind.api.results.JKindResult;
import jkind.lustre.Program;
import jkind.util.Util;

import org.eclipse.core.runtime.IProgressMonitor;

public abstract class KindApi {
	protected Integer timeout = null;

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
	public void execute(Program program, JKindResult result, IProgressMonitor monitor) {
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
	 */
	public void execute(String program, JKindResult result, IProgressMonitor monitor) {
		File lustreFile = null;
		try {
			lustreFile = writeLustreFile(program);
			execute(lustreFile, result, monitor);
		} finally {
			safeDelete(lustreFile);
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
	 */
	public abstract void execute(File lustreFile, JKindResult result, IProgressMonitor monitor);
	
	/**
	 * Check if the KindApi is available for running and throw exception if not
	 * 
	 * @throws java.lang.Exception
	 */
	public abstract void checkAvailable() throws Exception;
	
	protected static File writeLustreFile(String program) {
		File file = null;
		try {
			file = File.createTempFile("jkind-api", ".lus");
			Util.writeToFile(program, file);
			return file;
		} catch (IOException e) {
			safeDelete(file);
			throw new JKindException("Cannot write to file: " + file, e);
		}
	}

	protected static void safeDelete(File file) {
		if (file != null && file.exists()) {
			file.delete();
		}
	}
}
