package jkind.lustre;

public abstract class Ast {
	final public Location location;

	public Ast(Location location) {
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
