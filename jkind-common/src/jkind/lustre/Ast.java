package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.AstVisitor;
import jkind.lustre.visitors.PrettyPrintVisitor;

public abstract class Ast {
	public final Location location;

	public Ast(Location location) {
		Assert.isNotNull(location);
		this.location = location;
	}

	@Override
	public String toString() {
		PrettyPrintVisitor visitor = new PrettyPrintVisitor();
		accept(visitor);
		return visitor.toString();
	}

	public abstract <T, S extends T> T accept(AstVisitor<T, S> visitor);
}
