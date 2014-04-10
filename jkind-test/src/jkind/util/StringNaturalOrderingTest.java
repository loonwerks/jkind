package jkind.util;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class StringNaturalOrderingTest {
	@Test
	public void ordering() {
		String actual[] = { "a1", "a01", "a11", "a2", "a02", "a1a", "a1k" };
		Arrays.sort(actual, new StringNaturalOrdering());
		String expected[] = { "a01", "a1", "a1a", "a1k", "a02", "a2", "a11" };
		assertArrayEquals(expected, actual);
	}
}
