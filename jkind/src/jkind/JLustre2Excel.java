package jkind;

import java.util.Set;
import java.util.TreeSet;

import jkind.analysis.Level;
import jkind.analysis.StaticAnalyzer;
import jkind.lustre.EnumType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.translation.Node2Excel;
import jkind.translation.Translate;
import jkind.util.Util;

public class JLustre2Excel {
	public static void main(String args[]) {
		try {
			if (args.length != 1) {
				Output.println("usage: jlustre2excel <input>");
				System.exit(-1);
			}
			String filename = args[0];

			Program program = Main.parseLustre(filename);
			StaticAnalyzer.check(program, Level.WARNING);
			checkUniqueEnumValues(program);
			
			program = Translate.translate(program);
			Node main = program.getMainNode();

			String outFilename = filename + ".xls";
			new Node2Excel().convert(main, outFilename);
			Output.println("Wrote " + outFilename);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	private static void checkUniqueEnumValues(Program program) {
		boolean unique = true;

		for (EnumType et : Util.getEnumTypes(program.types)) {
			Set<String> seen = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
			for (String value : et.values) {
				if (seen.contains(value)) {
					Output.error(et.location,
							"cannot handle enumerated values that differ only by case");
					unique = false;
					break;
				} else {
					seen.add(value);
				}
			}
		}

		if (!unique) {
			System.exit(-1);
		}
	}
}
