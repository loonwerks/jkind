package jkind.lustre;

public abstract class AST {
	final public Location location;
	
	public AST(Location location) {
		this.location = location;
	}
}
