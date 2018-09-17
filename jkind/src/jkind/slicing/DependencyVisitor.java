package jkind.slicing;

import jkind.lustre.Expr;
import jkind.lustre.FunctionCallExpr;
import jkind.lustre.IdExpr;
import jkind.lustre.visitors.ExprIterVisitor;

public class DependencyVisitor extends ExprIterVisitor {
	public static DependencySet get(Expr expr) {
		DependencyVisitor visitor = new DependencyVisitor();
		expr.accept(visitor);
		return visitor.set;
	}

	private DependencySet set = new DependencySet();

	@Override
	public Void visit(FunctionCallExpr e) {
		set.add(Dependency.function(e.function));
		super.visit(e);
		return null;
	}

	@Override
	public Void visit(IdExpr e) {
		set.add(Dependency.variable(e.id));
		return null;
	}
}
