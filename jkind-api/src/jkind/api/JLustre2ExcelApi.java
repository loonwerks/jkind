package jkind.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jkind.JKindException;
import jkind.lustre.Program;

import org.eclipse.core.runtime.NullProgressMonitor;

/**
 * The primary interface to JLustre2Excel.
 */
public class JLustre2ExcelApi {
	private DebugLogger debug = new DebugLogger();

	private String jkindJar;

	/**
	 * Put the JLustre2Excel into debug mode where it saves all output
	 */
	public void setApiDebug() {
		debug = new DebugLogger("jlustre2excel-api-debug-");
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
	 * Provide a fixed JKind jar file to use
	 */
	public void setJKindJar(String jkindJar) {
		if (!new File(jkindJar).exists()) {
			throw new JKindException("JKind jar file does not exist: " + jkindJar);
		}
		this.jkindJar = jkindJar;
	}

	/**
	 * Run JLustre2Excel on a Lustre program
	 * 
	 * @param program
	 *            Lustre program
	 * @return Excel file
	 * @throws jkind.JKindException
	 */
	public File execute(Program program) {
		return execute(program.toString());
	}

	/**
	 * Run JLustre2Excel on a Lustre program
	 * 
	 * @param program
	 *            Lustre program as text
	 * @return
	 * @return Excel file
	 * @throws jkind.JKindException
	 */
	public File execute(String program) {
		File lustreFile = null;
		try {
			lustreFile = ApiUtil.writeLustreFile(program);
			return execute(lustreFile);
		} finally {
			debug.deleteIfUnneeded(lustreFile);
		}
	}

	/**
	 * Run JLustre2Excel on a Lustre program
	 * 
	 * @param lustreFile
	 *            File containing Lustre program
	 * @return Excel file
	 * @throws jkind.JKindException
	 */
	public File execute(File lustreFile) {
		ProcessBuilder pb = getJLustre2ExcelProcessBuilder(lustreFile);
		Process process = null;
		try {
			process = pb.start();
			String output = ApiUtil.readOutput(process, new NullProgressMonitor());
			debug.println("JLustre2Excel output", debug.saveFile("jlustre2excel-output-", ".txt", output));
			return new File(lustreFile.getPath() + ".xls");
		} catch (IOException e) {
			throw new JKindException("Error running JLustre2Excel", e);
		} finally {
			int code = 0;
			if (process != null) {
				if (process.isAlive()) {
					process.destroy();
				}
				try {
					code = process.waitFor();
				} catch (InterruptedException e) {
				}
				if (code != 0) {
					throw new JKindException("Abnormal termination, exit code " + code);
				}
			}
		}
	}

	private ProcessBuilder getJLustre2ExcelProcessBuilder(File lustreFile) {
		List<String> args = new ArrayList<>();
		args.addAll(Arrays.asList(getJLustre2Excel()));
		args.add(lustreFile.toString());
		ProcessBuilder builder = new ProcessBuilder(args);
		builder.redirectErrorStream(true);
		return builder;
	}

	private String[] getJLustre2Excel() {
		return new String[] { ApiUtil.getJavaPath(), "-jar", getOrFindJKindJar(), "-jlustre2excel" };
	}

	private String getOrFindJKindJar() {
		if (jkindJar != null) {
			return jkindJar;
		} else {
			return ApiUtil.findJKindJar().toString();
		}
	}
}