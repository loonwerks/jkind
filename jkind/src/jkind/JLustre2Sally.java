package jkind;

import java.io.FileWriter;
import java.util.List;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.sexp.Sexp;
import jkind.translation.Translate;

public class JLustre2Sally {
	public static void main(String args[]) {
		try {
			if (args.length != 1) {
				Output.fatal(ExitCodes.INVALID_OPTIONS, "usage: jlustre2sally <input>");
			}
			String filename = args[0];

			Program program = Main.parseLustre(filename);
			StaticAnalyzer.check(program, SolverOption.MATHSAT);
			Node main = Translate.translate(program);

			Sexp stateType = Lustre2Sally.createStateType(main);
			Sexp transitionSystem = Lustre2Sally.createTransitionSystem(main);
			List<Sexp> queries = Lustre2Sally.createQueries(main);

//			System.out.println(stateType);
//			System.out.println(transitionSystem);
//			for (Sexp query : queries) {
//				System.out.println(query);
//			}

			try (FileWriter file = new FileWriter(filename + ".mcmt")) {
				file.write(stateType.toString());
				file.write(System.lineSeparator());
				file.write(System.lineSeparator());
				file.write(transitionSystem.toString());
				file.write(System.lineSeparator());
				file.write(System.lineSeparator());
				for (Sexp query : queries) {
					file.write(query.toString());
					file.write(System.lineSeparator());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

}
