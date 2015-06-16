package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.AstVisitor;
import jkind.util.Util;

public class Contract extends Ast{

	public final List<Expr> requires;
	public final List<Expr> ensures;
	public final String name;
	
	public Contract(Location location, String name, List<Expr> requires, List<Expr> ensures) {
		super(location);
		this.name = name;
		this.requires = Util.safeList(requires);
		this.ensures = Util.safeList(ensures);
	}
	
	public Contract(String name, List<Expr> requires, List<Expr> ensures) {
		this(Location.NULL, name, requires, ensures);
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}

}
