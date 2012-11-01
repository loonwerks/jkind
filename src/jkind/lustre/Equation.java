package jkind.lustre;

import java.util.Collections;
import java.util.List;


public class Equation extends AST {
	final public List<String> lhs;
	final public Expr expr;

	public Equation(Location location, List<String> lhs, Expr expr) {
		super(location);
		this.lhs = Collections.unmodifiableList(lhs);
		this.expr = expr;
	}
	
	public Equation(Location location, String id, Expr expr) {
		super(location);
		this.lhs = Collections.singletonList(id);
		this.expr = expr;
	}
}
