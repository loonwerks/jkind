package jkind.lustre;

import java.util.List;

import jkind.lustre.visitors.AstVisitor;
import jkind.util.Util;

public class Contract extends Ast {
	public final List<Expr> requires;
	public final List<Expr> ensures;

	public Contract(Location location, List<Expr> requires, List<Expr> ensures) {
		super(location);
		this.requires = Util.safeList(requires);
		this.ensures = Util.safeList(ensures);
	}

	public Contract(List<Expr> requires, List<Expr> ensures) {
		this(Location.NULL, requires, ensures);
	}

	@Override
	public <T, S extends T> T accept(AstVisitor<T, S> visitor) {
		return visitor.visit(this);
	}
}
