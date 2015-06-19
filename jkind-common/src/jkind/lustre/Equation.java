package jkind.lustre;

import java.util.Collections;
import java.util.List;

import jkind.Assert;
import jkind.lustre.visitors.AstVisitor;
import jkind.util.Util;

public class Equation extends Ast {
	public final List<IdExpr> lhs;
	public final Expr expr;

	public Equation(Location location, List<IdExpr> lhs, Expr expr) {
		super(location);
		Assert.isNotNull(expr);
		this.lhs = Util.safeList(lhs);
		this.expr = expr;
	}

	public Equation(Location location, IdExpr id, Expr expr) {
		super(location);
		this.lhs = Collections.singletonList(id);
		this.expr = expr;
	}

	public Equation(List<IdExpr> lhs, Expr expr) {
		this(Location.NULL, lhs, expr);
	}

	public Equation(IdExpr id, Expr expr) {
		this(Location.NULL, id, expr);
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}