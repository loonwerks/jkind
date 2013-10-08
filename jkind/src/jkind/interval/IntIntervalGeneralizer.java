package jkind.interval;

import java.math.BigInteger;

public class IntIntervalGeneralizer {
	private final ModelGeneralizer generalizer;
	private static final BigInteger TWO_INT = BigInteger.valueOf(2);

	public IntIntervalGeneralizer(ModelGeneralizer generalizer) {
		this.generalizer = generalizer;
	}

	public Interval generalize(String id, int i, NumericInterval initial) {
		NumericInterval curr = initial;
		curr = generalizeIntIntervalLow(id, i, curr);
		curr = generalizeIntIntervalHigh(id, i, curr);
		return curr;
	}

	private NumericInterval generalizeIntIntervalLow(String id, int i, NumericInterval curr) {
		NumericInterval next = new NumericInterval(IntEndpoint.NEG_INFINITY, curr.getHigh());
		if (generalizer.modelConsistent(id, i, next)) {
			return next;
		}

		BigInteger a = ((IntEndpoint) curr.getLow()).getValue();
		BigInteger b = getLowerBoundInt(id, i, curr);
		// Invariant b < true lower bound <= a
		while (true) {
			BigInteger guess = a.add(b).divide(TWO_INT);
			if (guess.equals(a) || guess.equals(b)) {
				return new NumericInterval(new IntEndpoint(a), curr.getHigh());
			}
			next = new NumericInterval(new IntEndpoint(guess), curr.getHigh());
			if (generalizer.modelConsistent(id, i, next)) {
				a = guess;
			} else {
				b = guess;
			}
		}
	}

	private BigInteger getLowerBoundInt(String id, int i, NumericInterval curr) {
		BigInteger gap = BigInteger.ONE;
		BigInteger low = ((IntEndpoint) curr.getLow()).getValue();
		while (true) {
			low = low.subtract(gap);
			gap = gap.multiply(TWO_INT);
			NumericInterval next = new NumericInterval(new IntEndpoint(low), curr.getHigh());
			if (generalizer.modelConsistent(id, i, next)) {
				curr = next;
			} else {
				return low;
			}
		}
	}

	private NumericInterval generalizeIntIntervalHigh(String id, int i, NumericInterval curr) {
		NumericInterval next = new NumericInterval(curr.getLow(), IntEndpoint.POS_INFINITY);
		if (generalizer.modelConsistent(id, i, next)) {
			return next;
		}

		BigInteger a = ((IntEndpoint) curr.getHigh()).getValue();
		BigInteger b = getUpperBoundInt(id, i, curr);
		// Invariant a <= true upper bound < b
		while (true) {
			BigInteger guess = a.add(b).divide(TWO_INT);
			if (guess.equals(a) || guess.equals(b)) {
				return new NumericInterval(curr.getLow(), new IntEndpoint(a));
			}
			next = new NumericInterval(curr.getLow(), new IntEndpoint(guess));
			if (generalizer.modelConsistent(id, i, next)) {
				a = guess;
			} else {
				b = guess;
			}
		}
	}

	private BigInteger getUpperBoundInt(String id, int i, NumericInterval curr) {
		BigInteger gap = BigInteger.ONE;
		BigInteger high = ((IntEndpoint) curr.getHigh()).getValue();
		while (true) {
			high = high.add(gap);
			gap = gap.multiply(TWO_INT);
			NumericInterval next = new NumericInterval(curr.getLow(), new IntEndpoint(high));
			if (generalizer.modelConsistent(id, i, next)) {
				curr = next;
			} else {
				return high;
			}
		}
	}
}
