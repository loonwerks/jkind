package jkind.lustre;

import java.math.BigInteger;

import jkind.Assert;
import jkind.lustre.visitors.TypeVisitor;

public class SubrangeIntType extends Type {
	public final BigInteger low;
	public final BigInteger high;

	public SubrangeIntType(Location location, BigInteger low, BigInteger high) {
		super(location);
		Assert.isNotNull(low);
		Assert.isNotNull(high);
		Assert.isTrue(low.compareTo(high) <= 0);
		this.low = low;
		this.high = high;
	}

	public SubrangeIntType(BigInteger low, BigInteger high) {
		this(Location.NULL, low, high);
	}

	@Override
	public String toString() {
		return "subrange [" + low + ", " + high + "] of int";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((high == null) ? 0 : high.hashCode());
		result = prime * result + ((low == null) ? 0 : low.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SubrangeIntType)) {
			return false;
		}
		SubrangeIntType other = (SubrangeIntType) obj;
		if (high == null) {
			if (other.high != null) {
				return false;
			}
		} else if (!high.equals(other.high)) {
			return false;
		}
		if (low == null) {
			if (other.low != null) {
				return false;
			}
		} else if (!low.equals(other.low)) {
			return false;
		}
		return true;
	}

	@Override
	public <T> T accept(TypeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}