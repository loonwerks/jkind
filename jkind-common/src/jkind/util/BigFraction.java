package jkind.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import jkind.JKindException;

/**
 * An arbitrary sized fractional value
 * 
 * Stored as <code>numerator</code> / <code>denominator</code> where the
 * fraction is in reduced form and <code>denominator</code> is always positive
 */
public class BigFraction implements Comparable<BigFraction> {
	public static final BigFraction ZERO = new BigFraction(BigInteger.ZERO);
	public static final BigFraction ONE = new BigFraction(BigInteger.ONE);

	// The numerator and denominator are always stored in reduced form with the
	// denominator always positive
	final private BigInteger num;
	final private BigInteger denom;

	public BigFraction(BigInteger num, BigInteger denom) {
		if (num == null || denom == null) {
			throw new NullPointerException();
		}
		if (denom.equals(BigInteger.ZERO)) {
			throw new ArithmeticException("Divide by zero");
		}

		BigInteger gcd = num.gcd(denom);
		if (denom.compareTo(BigInteger.ZERO) > 0) {
			this.num = num.divide(gcd);
			this.denom = denom.divide(gcd);
		} else {
			this.num = num.negate().divide(gcd);
			this.denom = denom.negate().divide(gcd);
		}
	}

	public BigFraction(BigInteger num) {
		this(num, BigInteger.ONE);
	}

	public static BigFraction valueOf(BigDecimal value) {
		if (value.scale() >= 0) {
			return new BigFraction(value.unscaledValue(), BigInteger.valueOf(10).pow(value.scale()));
		} else {
			return new BigFraction(value.unscaledValue().multiply(
					BigInteger.valueOf(10).pow(-value.scale())));
		}
	}

	public BigInteger getNumerator() {
		return num;
	}

	public BigInteger getDenominator() {
		return denom;
	}

	public BigFraction add(BigFraction val) {
		return new BigFraction(num.multiply(val.denom).add(val.num.multiply(denom)),
				denom.multiply(val.denom));
	}

	public BigFraction add(BigInteger val) {
		return add(new BigFraction(val));
	}

	public BigFraction subtract(BigFraction val) {
		return new BigFraction(num.multiply(val.denom).subtract(val.num.multiply(denom)),
				denom.multiply(val.denom));
	}

	public BigFraction subtract(BigInteger val) {
		return subtract(new BigFraction(val));
	}

	public BigFraction multiply(BigFraction val) {
		return new BigFraction(num.multiply(val.num), denom.multiply(val.denom));
	}

	public BigFraction multiply(BigInteger val) {
		return multiply(new BigFraction(val));
	}

	public BigFraction divide(BigFraction val) {
		return new BigFraction(num.multiply(val.denom), denom.multiply(val.num));
	}

	public BigFraction divide(BigInteger val) {
		return divide(new BigFraction(val));
	}

	public BigFraction negate() {
		return new BigFraction(num.negate(), denom);
	}

	public int signum() {
		return num.signum();
	}

	public double doubleValue() {
		double result = num.doubleValue() / denom.doubleValue();
		if (Double.isFinite(result)) {
			return result;
		} else {
			BigDecimal numDec = new BigDecimal(num);
			BigDecimal denomDec = new BigDecimal(denom);
			return numDec.divide(denomDec, MathContext.DECIMAL64).doubleValue();
		}
	}

	public BigInteger floor() {
		BigInteger divAndRem[] = num.divideAndRemainder(denom);
		if (num.signum() >= 0 || divAndRem[1].equals(BigInteger.ZERO)) {
			return divAndRem[0];
		} else {
			return divAndRem[0].subtract(BigInteger.ONE);
		}
	}

	public BigDecimal toBigDecimal(int scale) {
		BigDecimal decNum = new BigDecimal(num).setScale(scale);
		BigDecimal decDenom = new BigDecimal(denom);
		return decNum.divide(decDenom, BigDecimal.ROUND_DOWN);
	}

	public String toTruncatedDecimal(int scale, String suffix) {
		if (scale <= 0) {
			throw new JKindException("Scale must be positive");
		}

		BigDecimal dec = toBigDecimal(scale);
		if (this.equals(BigFraction.valueOf(dec))) {
			return Util.removeTrailingZeros(dec.toPlainString());
		} else {
			return dec.toPlainString() + suffix;
		}
	}

	@Override
	public int compareTo(BigFraction other) {
		return num.multiply(other.denom).compareTo(other.num.multiply(denom));
	}

	@Override
	public String toString() {
		if (denom.equals(BigInteger.ONE)) {
			return num.toString();
		} else {
			return num + "/" + denom;
		}
	}

	@Override
	public int hashCode() {
		return num.hashCode() + denom.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof BigFraction)) {
			return false;
		}
		BigFraction other = (BigFraction) obj;
		return num.equals(other.num) && denom.equals(other.denom);
	}
}
