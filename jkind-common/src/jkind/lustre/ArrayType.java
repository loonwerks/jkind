package jkind.lustre;

import jkind.Assert;
import jkind.lustre.visitors.TypeVisitor;

public class ArrayType extends Type {
	public final Type base;
	public final int size;

	public ArrayType(Location location, Type base, int size) {
		super(location);
		Assert.isNotNull(base);
		Assert.isTrue(size > 0);
		this.base = base;
		this.size = size;
	}

	public ArrayType(Type base, int size) {
		this(Location.NULL, base, size);
	}

	@Override
	public String toString() {
		return base + "[" + size + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((base == null) ? 0 : base.hashCode());
		result = prime * result + size;
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
		if (!(obj instanceof ArrayType)) {
			return false;
		}
		ArrayType other = (ArrayType) obj;
		if (base == null) {
			if (other.base != null) {
				return false;
			}
		} else if (!base.equals(other.base)) {
			return false;
		}
		if (size != other.size) {
			return false;
		}
		return true;
	}

	@Override
	public <T> T accept(TypeVisitor<T> visitor) {
		return visitor.visit(this);
	}
}