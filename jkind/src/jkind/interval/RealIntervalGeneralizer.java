package jkind.interval;

import java.math.BigInteger;

import jkind.util.BigFraction;

public class RealIntervalGeneralizer {
	private final ModelGeneralizer generalizer;
	private static final BigFraction TWO = new BigFraction(BigInteger.valueOf(2));
	private static final BigFraction REAL_THRESHHOLD = new BigFraction(BigInteger.ONE,
			BigInteger.valueOf(10000));

	public RealIntervalGeneralizer(ModelGeneralizer generalizer) {
		this.generalizer = generalizer;
	}

	public Interval generalize(String id, int i, NumericInterval initial) {
		NumericInterval curr = initial;
		curr = generalizeRealIntervalLow(id, i, curr);
		curr = generalizeRealIntervalHigh(id, i, curr);
		return curr;
	}

	private NumericInterval generalizeRealIntervalLow(String id, int i, NumericInterval curr) {
		NumericInterval next = new NumericInterval(RealEndpoint.NEG_INFINITY, curr.getHigh());
		if (generalizer.modelConsistent(id, i, next)) {
			return next;
		}

		BigFraction a = ((RealEndpoint) curr.getLow()).getValue();
		BigFraction b = getLowerBoundReal(id, i, curr);
		// Invariant b < true lower bound <= a
		while (true) {
			if (a.subtract(b).compareTo(REAL_THRESHHOLD) < 0) {
				return new NumericInterval(new RealEndpoint(a), curr.getHigh());
			}

			BigFraction guess = a.add(b).divide(TWO);
			next = new NumericInterval(new RealEndpoint(guess), curr.getHigh());
			if (generalizer.modelConsistent(id, i, next)) {
				a = guess;
			} else {
				b = guess;
			}
		}
	}

	private BigFraction getLowerBoundReal(String id, int i, NumericInterval curr) {
		BigFraction gap = BigFraction.ONE;
		BigFraction low = ((RealEndpoint) curr.getLow()).getValue();
		while (true) {
			low = low.subtract(gap);
			gap = gap.multiply(TWO);
			NumericInterval next = new NumericInterval(new RealEndpoint(low), curr.getHigh());
			if (generalizer.modelConsistent(id, i, next)) {
				curr = next;
			} else {
				return low;
			}
		}
	}

	private NumericInterval generalizeRealIntervalHigh(String id, int i, NumericInterval curr) {
		NumericInterval next = new NumericInterval(curr.getLow(), RealEndpoint.POS_INFINITY);
		if (generalizer.modelConsistent(id, i, next)) {
			return next;
		}

		BigFraction a = ((RealEndpoint) curr.getHigh()).getValue();
		BigFraction b = getUpperBoundReal(id, i, curr);
		// Invariant a <= true upper bound < b
		while (true) {
			if (b.subtract(a).compareTo(REAL_THRESHHOLD) < 0) {
				return new NumericInterval(curr.getLow(), new RealEndpoint(a));
			}
			
			BigFraction guess = a.add(b).divide(TWO);
			next = new NumericInterval(curr.getLow(), new RealEndpoint(guess));
			if (generalizer.modelConsistent(id, i, next)) {
				a = guess;
			} else {
				b = guess;
			}
		}
	}

	private BigFraction getUpperBoundReal(String id, int i, NumericInterval curr) {
		BigFraction gap = BigFraction.ONE;
		BigFraction high = ((RealEndpoint) curr.getHigh()).getValue();
		while (true) {
			high = high.add(gap);
			gap = gap.multiply(TWO);
			NumericInterval next = new NumericInterval(curr.getLow(), new RealEndpoint(high));
			if (generalizer.modelConsistent(id, i, next)) {
				curr = next;
			} else {
				return high;
			}
		}
	}
}
