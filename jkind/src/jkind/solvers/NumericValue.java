package jkind.solvers;

import java.math.BigInteger;

import jkind.util.BigFraction;


public class NumericValue extends Value {
	private String val;
	
	public NumericValue(String val) {
		this.val = val;
	}
	
	public NumericValue(BigInteger i) {
		this.val = i.toString();
	}

	public NumericValue(BigFraction f) {
		this.val = f.toString();
	}

	@Override
	public String toString() {
		return val;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((val == null) ? 0 : val.hashCode());
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
		if (!(obj instanceof NumericValue)) {
			return false;
		}
		NumericValue other = (NumericValue) obj;
		if (val == null) {
			if (other.val != null) {
				return false;
			}
		} else if (!val.equals(other.val)) {
			return false;
		}
		return true;
	}
}
