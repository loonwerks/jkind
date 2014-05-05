package jkind.util;

import java.math.BigInteger;
import java.util.Comparator;

public class StringNaturalOrdering implements Comparator<String> {
	@Override
	public int compare(String s1, String s2) {
		int n1 = s1.length();
		int n2 = s2.length();
		int min = Math.min(n1, n2);
		for (int i = 0; i < min; i++) {
			char c1 = s1.charAt(i);
			char c2 = s2.charAt(i);
			if (Character.isDigit(c1) && Character.isDigit(c2)) {
				int end1 = getIntEnd(s1, i);
				int end2 = getIntEnd(s2, i);

				String ss1 = s1.substring(i, end1);
				String ss2 = s2.substring(i, end2);
				BigInteger v1 = new BigInteger(ss1);
				BigInteger v2 = new BigInteger(ss2);
				if (v1.equals(v2)) {
					// Check leading zeros: The one with more is smaller
					int comp = ss1.compareTo(ss2);
					if (comp != 0) {
						return comp;
					} else {
						// Must have end1 = end2
						i = end1 - 1;
						continue;
					}
				} else {
					return v1.subtract(v2).signum();
				}
			} else if (c1 != c2) {
				return c1 - c2;
			}
		}

		return n1 - n2;
	}

	private int getIntEnd(String s, int begin) {
		int end = begin;
		while (end < s.length() && Character.isDigit(s.charAt(end))) {
			end++;
		}
		return end;
	}
}
