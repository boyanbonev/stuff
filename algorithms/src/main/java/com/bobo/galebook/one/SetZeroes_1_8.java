package com.bobo.galebook.one;

import java.util.Collection;
import java.util.HashSet;

public class SetZeroes_1_8 {

	public void setZero(int a[][]) {
		Collection<Integer> zeroRows = new HashSet<>();
		Collection<Integer> zeroCols = new HashSet<>();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				if (a[i][j] == 0) {
					zeroRows.add(i);
					zeroCols.add(j);
				}
			}
		}

		setZeroRows(a, zeroRows);
		setZeroCols(a, zeroCols);
	}

	private void setZeroCols(int[][] a, Collection<Integer> zeroCols) {
		for (int col : zeroCols) {
			for (int i = 0; i < a.length; i++) {
				a[i][col] = 0;
			}
		}
	}

	private void setZeroRows(int[][] a, Collection<Integer> zeroRows) {
		for (int row : zeroRows) {
			for (int i = 0; i < a[row].length; i++) {
				a[row][i] = 0;
			}
		}
	}

	private void print(int[][] a) {
		System.out.println();
		int n = a.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int[][] a = new int[][] { { 1, 2, 3 }, { 4, 0, 6 }, { 7, 8, 9 } };
		SetZeroes_1_8 sz = new SetZeroes_1_8();
		sz.print(a);

		sz.setZero(a);

		sz.print(a);
	}
}
