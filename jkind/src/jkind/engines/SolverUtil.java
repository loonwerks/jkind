package jkind.engines;

import jkind.SolverOption;
import jkind.analysis.LinearChecker;
import jkind.analysis.YicesArithOnlyCheck;
import jkind.lustre.Node;
import jkind.solvers.Solver;
import jkind.solvers.cvc4.Cvc4Solver;
import jkind.solvers.mathsat.MathSatSolver;
import jkind.solvers.smtinterpol.SmtInterpolSolver;
import jkind.solvers.yices.YicesSolver;
import jkind.solvers.yices2.Yices2Solver;
import jkind.solvers.z3.Z3Solver;

public class SolverUtil {
	public static Solver getSolver(SolverOption solverOption, String scratchBase, Node node, Integer randomSeed) {
		switch (solverOption) {
		case YICES:
			return new YicesSolver(scratchBase, randomSeed, YicesArithOnlyCheck.check(node));
		case CVC4:
			return new Cvc4Solver(scratchBase, randomSeed);
		case Z3:
			return new Z3Solver(scratchBase, randomSeed, LinearChecker.isLinear(node));
		case YICES2:
			return new Yices2Solver(scratchBase, randomSeed);
		case MATHSAT:
			return new MathSatSolver(scratchBase, randomSeed);
		case SMTINTERPOL:
			return new SmtInterpolSolver(scratchBase, randomSeed);
		}
		throw new IllegalArgumentException("Unknown solver: " + solverOption);
	}
}
