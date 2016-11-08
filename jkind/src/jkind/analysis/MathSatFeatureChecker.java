package jkind.analysis;

import jkind.StdErr;
import jkind.lustre.BinaryExpr;
import jkind.lustre.Program;
import jkind.lustre.visitors.AstIterVisitor;

public class MathSatFeatureChecker extends AstIterVisitor {
	private boolean passed = true;

	public static boolean check(Program program) {
		MathSatFeatureChecker checker = new MathSatFeatureChecker();
		checker.visit(program);
		return checker.passed;
	}

	@Override
	public Void visit(BinaryExpr e) {
		super.visit(e);

		switch (e.op) {
		case INT_DIVIDE:
			StdErr.error(e.location, "integer division not supported in MathSAT");
			passed = false;
			break;

		case MODULUS:
			StdErr.error(e.location, "modulus not supported in MathSAT");
			passed = false;
			break;

		default:
			break;
		}

		return null;
	}
}
