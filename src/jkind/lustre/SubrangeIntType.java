package jkind.lustre;

public class SubrangeIntType extends Type {
	final public Location location;
	final public int low;
	final public int high;
	
	public SubrangeIntType(Location location, int low, int high) {
		super("int");
		this.location = location;
		this.low = low;
		this.high = high;
	}
}
