package jkind;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.processes.Director;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.Specification;
import jkind.translation.Translate;

public class JKind {
	public static void main(String[] args) {
		try {
			JKindSettings settings = JKindArgumentParser.parse(args);
			String filename = settings.filename;
			Program program = Main.parseLustre(filename);

			checkFunctionsCompatibility(program, settings);
			StaticAnalyzer.check(program, settings.solver);

			program = Translate.translate(program);
			Node main = program.getMainNode();
			DependencyMap depMap = new DependencyMap(main, main.properties, program.functions);
			main = LustreSlicer.slice(main, depMap);
			Specification spec = new Specification(filename, program.functions, main, depMap);
			new Director(settings, spec).run();
			System.exit(0); // Kills all threads
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	private static void checkFunctionsCompatibility(Program program, JKindSettings settings) {
		if (!program.functions.isEmpty()) {
			if (settings.solver != SolverOption.YICES) {
				Output.error("uninterpreted functions not supported with " + settings.solver);
				System.exit(ExitCodes.INVALID_OPTIONS);
			}
			if (settings.intervalGeneralization) {
				Output.error("uninterpreted functions not supported with interval generalization");
				System.exit(ExitCodes.UNSUPPORTED_FEATURE);
			}
		}
	}
}
