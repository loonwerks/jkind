package jkind.lustre.parsing;

import jkind.lustre.ArrayAccessExpr;
import jkind.lustre.Expr;
import jkind.lustre.IdExpr;
import jkind.lustre.RecordAccessExpr;
import jkind.lustre.visitors.AstMapVisitor;

public class FlattenIds extends AstMapVisitor {
	@Override
	public Expr visit(ArrayAccessExpr e) {
		return new IdExpr(e.location, e.toString());
	}

	@Override
	public Expr visit(RecordAccessExpr e) {
		return new IdExpr(e.location, e.toString());
	}
}
