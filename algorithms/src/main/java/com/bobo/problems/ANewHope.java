package com.bobo.problems;

import java.util.Arrays;

public class ANewHope {

	public int count(int[] firstWeek, int[] lastWeek, int d) {
		if (arraysEqual(firstWeek, lastWeek)) {
			return 0;
		}
		int cnt = 0;
		int[] tmp = firstWeek;
		do {
			tmp = genNextPerm(tmp, d);
			cnt++;
			if (arraysEqual(tmp, firstWeek)) {
				break;
			}
			System.out.println(Arrays.toString(tmp));
		} while (!arraysEqual(tmp, lastWeek));
		return cnt;
	}

	private boolean arraysEqual(int[] a, int[] b) {
		if (a.length != b.length) {
			return false;
		}
		for (int i = 0; i < a.length; i++) {
			if (a[i] != b[i]) {
				return false;
			}
		}
		return true;
	}

	public int[] genNextPerm(int[] startWeek, int d) {
		int weekLen = startWeek.length;
		int[] tmp = new int[weekLen];
		for (int i = 0; i < weekLen; i++) {
			int num = startWeek[i];
			int newPos = (i + d) % weekLen;
			tmp[newPos] = num;
		}

		return tmp;
	}

	public static void main(String[] args) {
		// int[] firstWeek = { 1, 2, 3, 4 };
		// int[] lastWeek = { 4, 3, 2, 1 };

		int[] firstWeek = { 8, 5, 4, 1, 7, 6, 3, 2 };
		int[] lastWeek = { 2, 4, 6, 8, 1, 3, 5, 7 };

		int d = 3;

		int count = new ANewHope().count(firstWeek, lastWeek, d);

		System.out.println("Count=" + count);
	}

}

