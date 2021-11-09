package jkind.api.eclipse;

import java.io.File;
import java.io.IOException;
import java.util.function.Function;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;

import jkind.api.DebugLogger;
import jkind.api.results.JKindResult;

public class ApiUtil extends jkind.api.ApiUtil {

	/**
	 * An implementation of an (@link jkind.api.ApiUtil.ICancellationMonitor) based on using
	 * an Eclipse IProgressMonitor.
	 */
	public static class CancellationMonitor implements jkind.api.ApiUtil.ICancellationMonitor {
		private IProgressMonitor monitor;

		public CancellationMonitor(IProgressMonitor monitor) {
			this.monitor = monitor;
		}

		public CancellationMonitor() {
			this(new NullProgressMonitor());
		}

		@Override
		public boolean isCanceled() {
			return monitor.isCanceled();
		}

		@Override
		public void done() {
			monitor.done();
		}
	}

	public static void execute(Function<File, ProcessBuilder> runCommand, File lustreFile, JKindResult result,
			IProgressMonitor monitor, DebugLogger debug) {
		execute(runCommand, lustreFile, result, new CancellationMonitor(monitor), debug);
	}

	public static String readOutput(Process process, IProgressMonitor monitor) throws IOException {
		return readOutput(process, new CancellationMonitor(monitor));
	}

	public static void readOutputToBuilder(Process process, IProgressMonitor monitor, StringBuilder outputText,
			StringBuilder errorText) throws IOException {
		readOutputToBuilder(process, new CancellationMonitor(monitor), outputText, errorText);
	}

}
