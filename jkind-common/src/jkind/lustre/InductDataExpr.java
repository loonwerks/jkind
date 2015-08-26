package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.ExprVisitor;
import jkind.util.Util;

public class InductDataExpr extends Expr {
	public final String name;
	public final List<Expr> args;

	public InductDataExpr(Location location, String name, List<Expr> args) {
		super(location);
		this.name = name;
		this.args = Util.safeList(args);
	}

	public InductDataExpr(String name, List<Expr> args){
		this(Location.NULL, name, args);
	}
	
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		// TODO Auto-generated method stub
		return null;
	}

}
