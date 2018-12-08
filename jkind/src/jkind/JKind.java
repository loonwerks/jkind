package jkind;

import java.util.ArrayList;
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

			/*
			 * We want to keep the original types before inlining
			 * We keep a copy of it and put the constants with their original type into
			 * the new program
			 */
			Program transProgram = Translate.translate(program); // In transProgram the constants are missing
			Program newProgram = new Program(transProgram.location, transProgram.types, program.constants, transProgram.functions, transProgram.nodes, transProgram.main); // Adding the constants and types
			Specification userSpec = new Specification(newProgram, settings.slicing); // userSpec with constants and types
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
			Program finalProgram = new Program(program.location, program.types, new ArrayList<>(userSpec.constants), program.functions, program.nodes, program.main);
			return new Specification(finalProgram, settings.slicing);
		} else {
			return userSpec;
		}
	}
}
