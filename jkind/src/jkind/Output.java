package jkind;

import java.util.List;

import jkind.analysis.Level;
import jkind.lustre.Location;
import jkind.util.Util;

public class Output {
	private static List<String> locationReference;

	public static void setLocationReference(List<String> locationReference) {
		Output.locationReference = locationReference;
	}

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
		if (level != Level.IGNORE) {
			println(level + " " + text);
		}
	}

	public static void output(Level level, Location loc, String text) {
		if (level != Level.IGNORE) {
			println(level + " at line " + loc + " " + text);
			if (level == Level.ERROR) {
				showLocation(loc);
			}
		}
	}

	public static void showLocation(Location loc) {
		if (1 <= loc.line && loc.line <= locationReference.size()) {
			String line = locationReference.get(loc.line - 1);
			Output.println(line);
			Output.println(Util.spaces(loc.charPositionInLine) + "^");
		}
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
