package jkind.lustre;

public abstract class Ast {
	final public Location location;
	
	public Ast(Location location) {
		this.location = location;
	}
}
