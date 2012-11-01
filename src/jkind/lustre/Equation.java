package jkind.lustre;

import java.util.Collections;
import java.util.List;


public class Equation extends AST {
	final public List<IdExpr> lhs;
	final public Expr expr;

	public Equation(Location location, List<IdExpr> lhs, Expr expr) {
		super(location);
		this.lhs = Collections.unmodifiableList(lhs);
		this.expr = expr;
	}
	
	public Equation(Location location, IdExpr id, Expr expr) {
		super(location);
		this.lhs = Collections.singletonList(id);
		this.expr = expr;
	}
}
