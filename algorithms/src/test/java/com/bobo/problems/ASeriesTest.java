package com.bobo.problems;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ASeriesTest {

	private ASeries series = new ASeries();

	@Test
	public void testLongest1() {
		int longest = series.longest(new int[] { 3, 8, 4, 5, 6, 2, 2 });
		assertEquals(longest, 5);
	}

	@Test
	public void testLongest2() {
		int longest = series.longest(new int[] { -1, -5, 1, 33, 8, 4, 5, 6, 2, 2 });
		assertEquals(longest, 3);
	}

	@Test
	public void testLongest3() {
		int longest = series.longest(new int[] { -10, -20, -10, -10 });
		assertEquals(longest, 3);
	}

}
