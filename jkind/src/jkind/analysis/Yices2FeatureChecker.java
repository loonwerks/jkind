package jkind.analysis;

import jkind.Output;
import jkind.lustre.BinaryExpr;
import jkind.lustre.CastExpr;
import jkind.lustre.Program;
import jkind.lustre.visitors.AstIterVisitor;

public class Yices2FeatureChecker extends AstIterVisitor {
	private boolean passed = true;

	public static boolean check(Program program) {
		Yices2FeatureChecker checker = new Yices2FeatureChecker();
		checker.visit(program);
		return checker.passed;
	}

	@Override
	public Void visit(BinaryExpr e) {
		super.visit(e);

		switch (e.op) {
		case INT_DIVIDE:
			Output.error(e.location, "integer division not supported in Yices 2");
			passed = false;
			break;

		case MODULUS:
			Output.error(e.location, "modulus not supported in Yices 2");
			passed = false;
			break;

		default:
			break;
		}

		return null;
	}
	
	@Override
	public Void visit(CastExpr e) {
		Output.error(e.location, "casting not supported in Yices 2");
		passed = false;
		return null;
	}
}
