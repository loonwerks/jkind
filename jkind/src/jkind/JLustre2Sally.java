package jkind;

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
			System.out.println(stateType);
			
			Sexp transitionSystem = Lustre2Sally.createTransitionSystem(main);
			System.out.println(transitionSystem);
			
			List<Sexp> queries = Lustre2Sally.createQueries(main);
			for (Sexp query : queries) {
				System.out.println(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

}
