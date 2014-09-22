package jkind;

import jkind.analysis.Level;
import jkind.lustre.Location;

public class Output {
	public static void warning(String text) {
		output(Level.WARNING, text);
	}

	public static void warning(Location loc, String text) {
		output(Level.WARNING, loc, text);
	}

	public static void error(String text) {
		output(Level.ERROR, text);
	}

	public static void error(Location loc, String text) {
		output(Level.ERROR, loc, text);
	}

	public static void fatal(int exitCode, String text) {
		error(text);
		System.exit(exitCode);
	}

	public static void fatal(int exitCode, Location loc, String text) {
		error(loc, text);
		System.exit(exitCode);
	}

	public static void output(Level level, String text) {
		println(level + " " + text);
	}

	public static void output(Level level, Location loc, String text) {
		println(level + " at line " + loc + " " + text);
	}

	public static void println(String text) {
		System.out.println(text);
	}

	public static void println() {
		System.out.println();
	}

	public static void printf(String format, Object... args) {
		System.out.printf(format, args);
	}

	public static void printStackTrace(Throwable t) {
		t.printStackTrace(System.out);
	}
}
