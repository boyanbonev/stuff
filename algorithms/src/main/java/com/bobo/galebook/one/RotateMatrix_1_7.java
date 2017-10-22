package com.bobo.galebook.one;

public class RotateMatrix_1_7 {
	public int[][] rotate(int a[][]) {
		final int n = a.length;
		// int result[][] = new int[n][n];
		for (int layer = 0; layer < n / 2; layer++) {
			int first = layer;
			int last = n - layer - 1;
			for (int i = first; i < last; i++) {
				int offset = i - first;

				int tmp = a[first][i];
				a[first][i] = a[last - offset][first];
				a[last - offset][first] = a[last][last - offset];
				a[last][last - offset] = a[i][last];
				a[i][last] = tmp;
			}
		}

		return a;
	}

	public static void main(String[] args) {
		int[][] a = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 } };
		print(a);

		new RotateMatrix_1_7().rotate(a);

		print(a);
	}

	private static void print(int[][] a) {
		System.out.println();
		int n = a.length;
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(a[i][j] + " ");
			}
			System.out.println();
		}
	}

}
