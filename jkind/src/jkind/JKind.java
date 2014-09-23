package jkind;

import jkind.analysis.Level;
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
			
			if (!program.functions.isEmpty()) {
				if (settings.solver != SolverOption.YICES) {
					Output.error("uninterpreted functions not supported with " + settings.solver);
					System.exit(-1);
				} 
				if (settings.intervalGeneralization) {
					Output.error("uninterpreted functions not supported with interval generalization");
					System.exit(-1);
				}
			}
			
			Level nonlinear = settings.solver == SolverOption.Z3 ? Level.WARNING : Level.ERROR;
			StaticAnalyzer.check(program, nonlinear);

			program = Translate.translate(program);
			Node main = program.getMainNode();
			DependencyMap dependencyMap = new DependencyMap(main, main.properties, program.functions);
			main = LustreSlicer.slice(main, dependencyMap);
			Specification spec = new Specification(filename, program.functions, main, dependencyMap);
			new Director(settings, spec).run();
			System.exit(0); // Kills all threads
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(-1);
		}
	}
}
