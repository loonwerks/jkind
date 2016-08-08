package jkind.interval;

import java.math.BigInteger;

import jkind.util.BigFraction;
import jkind.util.StreamIndex;

public class RealIntervalGeneralizer {
	private final ModelGeneralizer generalizer;
	private static final BigFraction TWO = new BigFraction(BigInteger.valueOf(2));
	private static final BigFraction REAL_THRESHHOLD = new BigFraction(BigInteger.ONE,
			BigInteger.valueOf(10000));

	public RealIntervalGeneralizer(ModelGeneralizer generalizer) {
		this.generalizer = generalizer;
	}

	public Interval generalize(StreamIndex si, NumericInterval initial) {
		NumericInterval curr = initial;
		curr = generalizeRealIntervalLow(si, curr);
		curr = generalizeRealIntervalHigh(si, curr);
		return curr;
	}

	private NumericInterval generalizeRealIntervalLow(StreamIndex si, NumericInterval curr) {
		NumericInterval next = new NumericInterval(RealEndpoint.NEGATIVE_INFINITY, curr.getHigh());
		if (generalizer.modelConsistent(si, next)) {
			return next;
		}

		BigFraction a = ((RealEndpoint) curr.getLow()).getValue();
		BigFraction b = getLowerBoundReal(si, curr);
		// Invariant b < true lower bound <= a
		while (true) {
			if (a.subtract(b).compareTo(REAL_THRESHHOLD) < 0) {
				return new NumericInterval(new RealEndpoint(a), curr.getHigh());
			}

			BigFraction guess = a.add(b).divide(TWO);
			next = new NumericInterval(new RealEndpoint(guess), curr.getHigh());
			if (generalizer.modelConsistent(si, next)) {
				a = guess;
			} else {
				b = guess;
			}
		}
	}

	private BigFraction getLowerBoundReal(StreamIndex si, NumericInterval curr) {
		BigFraction gap = BigFraction.ONE;
		BigFraction low = ((RealEndpoint) curr.getLow()).getValue();
		while (true) {
			low = low.subtract(gap);
			gap = gap.multiply(TWO);
			NumericInterval next = new NumericInterval(new RealEndpoint(low), curr.getHigh());
			if (generalizer.modelConsistent(si, next)) {
				curr = next;
			} else {
				return low;
			}
		}
	}

	private NumericInterval generalizeRealIntervalHigh(StreamIndex si, NumericInterval curr) {
		NumericInterval next = new NumericInterval(curr.getLow(), RealEndpoint.POSITIVE_INFINITY);
		if (generalizer.modelConsistent(si, next)) {
			return next;
		}

		BigFraction a = ((RealEndpoint) curr.getHigh()).getValue();
		BigFraction b = getUpperBoundReal(si, curr);
		// Invariant a <= true upper bound < b
		while (true) {
			if (b.subtract(a).compareTo(REAL_THRESHHOLD) < 0) {
				return new NumericInterval(curr.getLow(), new RealEndpoint(a));
			}
			
			BigFraction guess = a.add(b).divide(TWO);
			next = new NumericInterval(curr.getLow(), new RealEndpoint(guess));
			if (generalizer.modelConsistent(si, next)) {
				a = guess;
			} else {
				b = guess;
			}
		}
	}

	private BigFraction getUpperBoundReal(StreamIndex si, NumericInterval curr) {
		BigFraction gap = BigFraction.ONE;
		BigFraction high = ((RealEndpoint) curr.getHigh()).getValue();
		while (true) {
			high = high.add(gap);
			gap = gap.multiply(TWO);
			NumericInterval next = new NumericInterval(curr.getLow(), new RealEndpoint(high));
			if (generalizer.modelConsistent(si, next)) {
				curr = next;
			} else {
				return high;
			}
		}
	}
}
