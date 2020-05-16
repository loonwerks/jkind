package jkind.translation.compound;

import java.math.BigInteger;

public class ArrayAccess implements Access {
	public final BigInteger index;

	public ArrayAccess(BigInteger index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "[" + index + "]";
	}
}
