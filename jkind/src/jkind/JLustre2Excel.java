package jkind;

import java.util.Set;
import java.util.TreeSet;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.EnumType;
import jkind.lustre.Program;
import jkind.translation.Node2Excel;
import jkind.translation.Translate;
import jkind.util.Util;

public class JLustre2Excel {
	public static void main(String args[]) {
		try {
			if (args.length != 1) {
				StdErr.fatal(ExitCodes.INVALID_OPTIONS, "usage: jlustre2excel <input>");
			}
			String filename = args[0];

			Program program = Main.parseLustre(filename);
			StaticAnalyzer.check(program, SolverOption.Z3);
			ensureUniqueEnumValues(program);
			ensureNoFunctions(program);

			program = Translate.translate(program);
			String outFilename = filename + ".xls";
			new Node2Excel().convert(program.getMainNode(), outFilename);
			System.out.println("Wrote " + outFilename);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	private static void ensureUniqueEnumValues(Program program) {
		for (EnumType et : Util.getEnumTypes(program.types)) {
			Set<String> seen = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
			for (String value : et.values) {
				if (seen.contains(value)) {
					StdErr.fatal(ExitCodes.UNSUPPORTED_FEATURE, et.location,
							"cannot handle enumerated values that differ only by case");
				} else {
					seen.add(value);
				}
			}
		}
	}

	private static void ensureNoFunctions(Program program) {
		if (!program.functions.isEmpty()) {
			StdErr.fatal(ExitCodes.UNSUPPORTED_FEATURE, "functions are not supported");
		}
	}
}
