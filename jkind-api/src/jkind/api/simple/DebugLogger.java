package jkind.api.simple;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import jkind.JKindException;
import jkind.util.Util;

/**
 * 
 * @deprecated
 *    To be reomved in 6.0.
 * 	  This class represents a transitional API to provide a basic, command-
 *    line oriented means of using JKind.  This functionality duplicates that
 *    of the jkind.api package but removes the dependencies on Eclipse.  Once
 *    the Eclipse-specific dependencies have been removed, this functionality
 *    will migrate to package jkind.api.
 */
@Deprecated
public class DebugLogger {
	@Deprecated
	private final PrintWriter debug;

	@Deprecated
	public DebugLogger() {
		debug = null;
	}

	@Deprecated
	public DebugLogger(String prefix) {
		try {
			File debugFile = File.createTempFile(prefix, ".txt");
			debug = new PrintWriter(new FileWriter(debugFile), true);
		} catch (IOException e) {
			throw new JKindException("Unable to create temporary debug file", e);
		}
	}

	@Deprecated
	public void println() {
		if (debug != null) {
			debug.println();
		}
	}

	@Deprecated
	public void println(String text) {
		if (debug != null) {
			debug.println(text);
		}
	}

	@Deprecated
	public void println(String text, File file) {
		if (debug != null) {
			try {
				debug.println(text + ": " + file.getCanonicalPath());
			} catch (IOException e) {
				debug.println(text + ": " + file.getAbsolutePath());
			}
		}
	}

	@Deprecated
	public File saveFile(String prefix, String suffix, String contents) {
		if (debug != null) {
			try {
				File file = File.createTempFile(prefix, suffix);
				Util.writeToFile(contents, file);
				return file;
			} catch (IOException e) {
				throw new JKindException("Unable to create temporary file", e);
			}
		} else {
			return null;
		}
	}

	@Deprecated
	public void deleteIfUnneeded(File file) {
		if (debug == null && file != null && file.exists()) {
			file.delete();
		}
	}
}
