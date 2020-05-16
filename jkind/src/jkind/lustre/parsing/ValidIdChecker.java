package jkind.lustre.parsing;

import jkind.StdErr;
import jkind.lustre.Equation;
import jkind.lustre.IdExpr;
import jkind.lustre.Location;
import jkind.lustre.Program;
import jkind.lustre.VarDecl;
import jkind.lustre.visitors.AstIterVisitor;

public class ValidIdChecker extends AstIterVisitor {
	private boolean passed = true;

	public static boolean check(Program program) {
		ValidIdChecker checker = new ValidIdChecker();
		checker.visit(program);
		return checker.passed;
	}

	@Override
	public Void visit(VarDecl e) {
		super.visit(e);
		check(e.location, e.id);
		return null;
	}

	@Override
	public Void visit(IdExpr e) {
		super.visit(e);
		check(e.location, e.id);
		return null;
	}

	@Override
	public Void visit(Equation e) {
		super.visit(e);
		e.lhs.forEach(this::visit);
		return null;
	}

	private void check(Location location, String id) {
		if (id.contains("~") || id.contains("[") || id.contains("]") || id.contains(".")) {
			StdErr.error(location, "Invalid id: " + id);
			passed = false;
		}
	}
}
