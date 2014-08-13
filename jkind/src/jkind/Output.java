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

	public static void fatal(String text) {
		error(text);
		System.exit(-1);
	}

	public static void fatal(Location loc, String text) {
		error(loc, text);
		System.exit(-1);
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
