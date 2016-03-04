package jkind;

import java.io.File;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.translation.RemoveEnumTypes;
import jkind.translation.Translate;
import jkind.util.Util;

public class JUnroll {
	public static void main(String args[]) {
		try {
			if (args.length != 1 && args.length != 2) {
				Output.fatal(ExitCodes.INVALID_OPTIONS, "usage: junroll <input> [depth]");
			}

			String filename = args[0];
			String outFilename = filename.substring(0, filename.length() - 4) + ".unroll.lus";		
			
			int depth = 2;
			if (args.length == 2) {
				depth = Integer.parseInt(args[1]);
			}

			Program program = Main.parseLustre(filename);
			StaticAnalyzer.check(program, SolverOption.Z3);
			
			Node main = Translate.translate(program);
			main = RemoveEnumTypes.node(main);
			main = UnrollLustre.node(main, depth);

			Util.writeToFile(main.toString(), new File(outFilename));
			Output.println("Wrote " + outFilename);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}
}
