package jkind.slicing;

import jkind.lustre.Expr;
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
	public Void visit(IdExpr e) {
		set.add(e.id);
		return null;
	}
}
