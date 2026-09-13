package jkind.fuzz;

import java.math.BigInteger;

/**
 * A Lustre value: a boolean, an integer, or an exact rational.
 *
 * Integers and rationals are unbounded and exact, matching the SMT semantics
 * JKind encodes against, so replaying a counterexample never disagrees with
 * JKind merely because of overflow or rounding.
 */
public abstract class Val {

	public static final class Bool extends Val {
		public final boolean value;

		public Bool(boolean value) {
			this.value = value;
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Bool && ((Bool) o).value == value;
		}

		@Override
		public int hashCode() {
			return Boolean.hashCode(value);
		}

		@Override
		public String toString() {
			return Boolean.toString(value);
		}
	}

	public static final class Int extends Val {
		public final BigInteger value;

		public Int(BigInteger value) {
			this.value = value;
		}

		public Int(long value) {
			this(BigInteger.valueOf(value));
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Int && ((Int) o).value.equals(value);
		}

		@Override
		public int hashCode() {
			return value.hashCode();
		}

		@Override
		public String toString() {
			return value.toString();
		}
	}

	/** An exact rational, always kept in lowest terms with a positive denominator. */
	public static final class Rat extends Val {
		public final BigInteger num;
		public final BigInteger den;

		public Rat(BigInteger num, BigInteger den) {
			if (den.signum() == 0) {
				throw new ArithmeticException("zero denominator");
			}
			BigInteger n = num;
			BigInteger d = den;
			if (d.signum() < 0) {
				n = n.negate();
				d = d.negate();
			}
			BigInteger g = n.gcd(d);
			if (g.signum() != 0 && !g.equals(BigInteger.ONE)) {
				n = n.divide(g);
				d = d.divide(g);
			}
			this.num = n;
			this.den = d;
		}

		public static Rat of(long n) {
			return new Rat(BigInteger.valueOf(n), BigInteger.ONE);
		}

		public Rat add(Rat o) {
			return new Rat(num.multiply(o.den).add(o.num.multiply(den)), den.multiply(o.den));
		}

		public Rat subtract(Rat o) {
			return new Rat(num.multiply(o.den).subtract(o.num.multiply(den)), den.multiply(o.den));
		}

		public Rat multiply(Rat o) {
			return new Rat(num.multiply(o.num), den.multiply(o.den));
		}

		public Rat divide(Rat o) {
			return new Rat(num.multiply(o.den), den.multiply(o.num));
		}

		public Rat negate() {
			return new Rat(num.negate(), den);
		}

		public int compareTo(Rat o) {
			return num.multiply(o.den).compareTo(o.num.multiply(den));
		}

		/** Largest integer not greater than this rational. */
		public BigInteger floor() {
			BigInteger[] qr = num.divideAndRemainder(den);
			if (num.signum() < 0 && qr[1].signum() != 0) {
				return qr[0].subtract(BigInteger.ONE);
			}
			return qr[0];
		}

		/** Parses a decimal literal such as {@code -3.75}. */
		public static Rat parseDecimal(String text) {
			int dot = text.indexOf('.');
			if (dot < 0) {
				return new Rat(new BigInteger(text), BigInteger.ONE);
			}
			String whole = text.substring(0, dot);
			String frac = text.substring(dot + 1);
			BigInteger scale = BigInteger.TEN.pow(frac.length());
			boolean negative = whole.startsWith("-");
			BigInteger wholePart = new BigInteger(negative ? whole.substring(1) : whole.isEmpty() ? "0" : whole);
			BigInteger fracPart = frac.isEmpty() ? BigInteger.ZERO : new BigInteger(frac);
			BigInteger n = wholePart.multiply(scale).add(fracPart);
			return new Rat(negative ? n.negate() : n, scale);
		}

		@Override
		public boolean equals(Object o) {
			return o instanceof Rat && ((Rat) o).num.equals(num) && ((Rat) o).den.equals(den);
		}

		@Override
		public int hashCode() {
			return num.hashCode() * 31 + den.hashCode();
		}

		@Override
		public String toString() {
			return den.equals(BigInteger.ONE) ? num.toString() : num + "/" + den;
		}
	}
}
