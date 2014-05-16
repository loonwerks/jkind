package jkind;

import java.util.Set;
import java.util.TreeSet;

import jkind.analysis.Level;
import jkind.analysis.StaticAnalyzer;
import jkind.lustre.EnumType;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.TypeDef;
import jkind.translation.Node2Excel;
import jkind.translation.Translate;

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

			Node main = Translate.translate(program);
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

		for (TypeDef def : program.types) {
			if (def.type instanceof EnumType) {
				EnumType et = (EnumType) def.type;
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
		}

		if (!unique) {
			System.exit(-1);
		}
	}
}
