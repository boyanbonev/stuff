package com.bobo.problems;

import java.util.Arrays;

public class ANDEquation {

	public int restoreY(int[] a) {
		Arrays.sort(a);
		int tmp = a[a.length - 1];
		for (int i = a.length - 1; i >= 1; i--) {
			tmp &= a[i];
		}
		if (tmp == a[0]) {
			return a[0];
		}
		return -1;
	}

	public static void main(String[] args) {
		// int[] a = { 31, 7 };
		int[] a = { 1362, 1066, 1659, 2010, 1912, 1720, 1851, 1593, 1799, 1805, 1139, 1493, 1141, 1163,
				1211 };
		// int a[] = new int[50];
		// for (int i = 0; i < 50; i++) {
		// a[i] = 1048575;
		// }
		// a[0] = 1048574;
		long startTime = System.currentTimeMillis();
		int y = new ANDEquation().restoreY(a);
		System.out.println("Calculataion took " + (System.currentTimeMillis() - startTime) + " ms");
		System.out.println("Y=" + y);
	}
}
