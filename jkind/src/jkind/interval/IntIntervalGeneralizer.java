package jkind.interval;

import java.math.BigInteger;

import jkind.util.StreamIndex;

public class IntIntervalGeneralizer {
	private final ModelGeneralizer generalizer;
	private static final BigInteger TWO_INT = BigInteger.valueOf(2);

	public IntIntervalGeneralizer(ModelGeneralizer generalizer) {
		this.generalizer = generalizer;
	}

	public Interval generalize(StreamIndex si, NumericInterval initial) {
		NumericInterval curr = initial;
		curr = generalizeIntIntervalLow(si, curr);
		curr = generalizeIntIntervalHigh(si, curr);
		return curr;
	}

	private NumericInterval generalizeIntIntervalLow(StreamIndex si, NumericInterval curr) {
		NumericInterval next = new NumericInterval(IntEndpoint.NEGATIVE_INFINITY, curr.getHigh());
		if (generalizer.modelConsistent(si, next)) {
			return next;
		}

		BigInteger a = ((IntEndpoint) curr.getLow()).getValue();
		BigInteger b = getLowerBoundInt(si, curr);
		// Invariant b < true lower bound <= a
		while (true) {
			BigInteger guess = a.add(b).divide(TWO_INT);
			if (guess.equals(a) || guess.equals(b)) {
				return new NumericInterval(new IntEndpoint(a), curr.getHigh());
			}
			next = new NumericInterval(new IntEndpoint(guess), curr.getHigh());
			if (generalizer.modelConsistent(si, next)) {
				a = guess;
			} else {
				b = guess;
			}
		}
	}

	private BigInteger getLowerBoundInt(StreamIndex si, NumericInterval curr) {
		BigInteger gap = BigInteger.ONE;
		BigInteger low = ((IntEndpoint) curr.getLow()).getValue();
		while (true) {
			low = low.subtract(gap);
			gap = gap.multiply(TWO_INT);
			NumericInterval next = new NumericInterval(new IntEndpoint(low), curr.getHigh());
			if (generalizer.modelConsistent(si, next)) {
				curr = next;
			} else {
				return low;
			}
		}
	}

	private NumericInterval generalizeIntIntervalHigh(StreamIndex si, NumericInterval curr) {
		NumericInterval next = new NumericInterval(curr.getLow(), IntEndpoint.POSITIVE_INFINITY);
		if (generalizer.modelConsistent(si, next)) {
			return next;
		}

		BigInteger a = ((IntEndpoint) curr.getHigh()).getValue();
		BigInteger b = getUpperBoundInt(si, curr);
		// Invariant a <= true upper bound < b
		while (true) {
			BigInteger guess = a.add(b).divide(TWO_INT);
			if (guess.equals(a) || guess.equals(b)) {
				return new NumericInterval(curr.getLow(), new IntEndpoint(a));
			}
			next = new NumericInterval(curr.getLow(), new IntEndpoint(guess));
			if (generalizer.modelConsistent(si, next)) {
				a = guess;
			} else {
				b = guess;
			}
		}
	}

	private BigInteger getUpperBoundInt(StreamIndex si, NumericInterval curr) {
		BigInteger gap = BigInteger.ONE;
		BigInteger high = ((IntEndpoint) curr.getHigh()).getValue();
		while (true) {
			high = high.add(gap);
			gap = gap.multiply(TWO_INT);
			NumericInterval next = new NumericInterval(curr.getLow(), new IntEndpoint(high));
			if (generalizer.modelConsistent(si, next)) {
				curr = next;
			} else {
				return high;
			}
		}
	}
}
