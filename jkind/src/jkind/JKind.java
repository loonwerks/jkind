package jkind;

import jkind.analysis.LinearChecker;
import jkind.analysis.StaticAnalyzer;
import jkind.engines.Director;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.translation.InlineSimpleEquations;
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

			Node main = Translate.translate(program);
			Specification userSpec = new Specification(main);
			Specification analysisSpec = getAnalysisSpec(userSpec, settings);

			int exitCode = new Director(settings, userSpec, analysisSpec).run();
			System.exit(exitCode); // Kills all threads
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	private static Specification getAnalysisSpec(Specification userSpec, JKindSettings settings) {
		if (settings.inline) {
			Node inlined = InlineSimpleEquations.node(userSpec.node);
			return new Specification(inlined);
		} else {
			return userSpec;
		}
	}
}
