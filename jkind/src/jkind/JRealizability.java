package jkind;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jkind.analysis.StaticAnalyzer;
import jkind.lustre.Node;
import jkind.lustre.Program;
import jkind.realizability.engines.RealizabilityDirector;
import jkind.translation.Specification;
import jkind.translation.Translate;
import jkind.util.Util;

public class JRealizability {
	public static void main(String[] args) {
		try {
			JRealizabilitySettings settings = JRealizabilityArgumentParser.parse(args);
			String filename = settings.filename;
			Program program = Main.parseLustre(filename);

			StaticAnalyzer.check(program, SolverOption.Z3);
			realizabilitySpecificChecks(program);

			program = Translate.translate(program);
			Specification spec = new Specification(program);
			int exitCode = new RealizabilityDirector(settings, spec).run();
			System.exit(exitCode); // Kills all threads
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(ExitCodes.UNCAUGHT_EXCEPTION);
		}
	}

	private static void realizabilitySpecificChecks(Program program) {
		Node main = program.getMainNode();

		boolean valid = true;
		valid = valid && checkNoFunctions(program);
		valid = valid && realizablitityQueryExists(main);
		valid = valid && realizablitityInputsNodeInputs(main);
		valid = valid && realizablitityInputsUnique(main);

		if (!valid) {
			System.exit(ExitCodes.STATIC_ANALYSIS_ERROR);
		}
	}

	private static boolean checkNoFunctions(Program program) {
		if (!program.functions.isEmpty()) {
			StdErr.error("functions are not supported in JRealizability");
			return false;
		} else {
			return true;
		}
	}

	private static boolean realizablitityQueryExists(Node main) {
		if (main.realizabilityInputs == null) {
			StdErr.error("main node '" + main.id + "' must have realizability query");
			return false;
		} else {
			return true;
		}
	}

	private static boolean realizablitityInputsNodeInputs(Node main) {
		boolean pass = true;
		List<String> inputs = Util.getIds(main.inputs);
		for (String ri : main.realizabilityInputs) {
			if (!inputs.contains(ri)) {
				StdErr.error("in node '" + main.id + "' realizability input '" + ri
						+ "' must be a node input");
				pass = false;
			}
		}
		return pass;
	}

	private static boolean realizablitityInputsUnique(Node main) {
		boolean unique = true;
		Set<String> seen = new HashSet<>();
		for (String ri : main.realizabilityInputs) {
			if (!seen.add(ri)) {
				StdErr.error("in node '" + main.id + "' realizability input '" + ri
						+ "' listed multiple times");
				unique = false;
			}
		}
		return unique;
	}
}