package jkind;

import jkind.analysis.LinearChecker;
import jkind.analysis.StaticAnalyzer;
import jkind.engines.Director;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.slicing.DependencyMap;
import jkind.slicing.LustreSlicer;
import jkind.translation.RecursiveFunctionSpecification;
import jkind.translation.Specification;
import jkind.translation.Translate;

public class JKind {
	public static void main(String[] args) {
		try {
			JKindSettings settings = JKindArgumentParser.parse(args);
			Program program = Main.parseLustre(settings.filename);

			StaticAnalyzer.check(program, settings.solver);
			if (!LinearChecker.isLinear(program)) {
				if (settings.pdrMax > 0) {
					Output.warning("disabling PDR due to non-linearities");
					settings.pdrMax = 0;
				}
			}

//			Node main = Translate.translate(program);
			program = Translate.translateProgram(program);
			Node main = program.getMainNode();
			DependencyMap dependencyMap = new DependencyMap(main, main.properties);
			main = LustreSlicer.slice(main, dependencyMap);
			//kind of hacky, but we need a way for the specification to contain
			//recursive functions if they are defined
			Specification spec;
            if (program.recFuns.size() != 0) {
                spec = new RecursiveFunctionSpecification(main, dependencyMap, program.recFuns);
            } else {
                spec = new Specification(main, dependencyMap);
            }
			new Director(settings, spec).run();
			System.exit(0); // Kills all threads
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}
}
