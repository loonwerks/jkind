package jkind.lustre;

import java.math.BigInteger;

public class SubrangeIntType extends Type {
	final public Location location;
	final public BigInteger low;
	final public BigInteger high;

	public SubrangeIntType(Location location, BigInteger low, BigInteger high) {
		super("int");
		this.location = location;
		this.low = low;
		this.high = high;
	}

	@Override
	public String toString() {
		return "subrange [" + low + ", " + high + "] of int";
	}
}