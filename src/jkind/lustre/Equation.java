package jkind.lustre;

import java.util.Collections;
import java.util.List;


public class Equation extends Ast {
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
	
	@Override
	public <T> T accept(AstVisitor<T> visitor) {
		return visitor.visit(this);
	}
}
