package jkind;

import jkind.analysis.LinearChecker;
import jkind.analysis.StaticAnalyzer;
import jkind.engines.Director;
import jkind.engines.SolverUtil;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.lustre.builders.ProgramBuilder;
import jkind.translation.InlineSimpleEquations;
import jkind.translation.Specification;
import jkind.translation.Translate;

public class JKind {
	public static void main(String[] args) {
		try {
			JKindSettings settings = JKindArgumentParser.parse(args);
			Program program = Main.parseLustre(settings.filename);
			program = setMainNode(program, settings.main);

			StaticAnalyzer.check(program, settings.solver);
			if (!LinearChecker.isLinear(program)) {
				if (settings.pdrMax > 0) {
					StdErr.warning("PDR not available for some properties due to non-linearities");
				}
			}

			ensureSolverAvailable(settings.solver);

			program = Translate.translate(program);
			Specification userSpec = new Specification(program, settings.slicing);
			Specification analysisSpec = getAnalysisSpec(userSpec, settings);

			int exitCode = new Director(settings, userSpec, analysisSpec).run();
			System.exit(exitCode); // Kills all threads
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	private static Program setMainNode(Program program, String main) {
		if (main == null) {
			return program;
		}

		boolean hasMainNode = program.nodes.stream().anyMatch(n -> n.id.equals(main));
		if (!hasMainNode) {
			StdErr.fatal(ExitCodes.INVALID_OPTIONS, "Unable to find main node '" + main + "'");
		}

		return new ProgramBuilder(program).setMain(main).build();
	}

	private static void ensureSolverAvailable(SolverOption solver) {
		try {
			SolverUtil.getBasicSolver(solver);
		} catch (JKindException e) {
			StdErr.fatal(ExitCodes.INVALID_OPTIONS, e.getMessage());
		}
	}

	private static Specification getAnalysisSpec(Specification userSpec, JKindSettings settings) {
		if (settings.inlining) {
			Node inlined = InlineSimpleEquations.node(userSpec.node);
			Program program = new ProgramBuilder().addFunctions(userSpec.functions).addNode(inlined).build();
			return new Specification(program, settings.slicing);
		} else {
			return userSpec;
		}
	}
}
