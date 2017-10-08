/* **********************************************************************
 * Copyright 2017 VMware, Inc.  All rights reserved. VMware Confidential
 ************************************************************************/
package com.bobo.galebook.eight;

public class MagicIndex_8_3 {
	public static void main(String[] args) {
		int[] arr = new int[] { -1, 2, 3, 4, 4 };
		int index = fastFindMagicIndex(arr);
		if (index != -1) {
			System.out.println("Found index = " + index);
		} else {
			System.out.println("No magic index");
		}

	}

	public static int findMagicIndex(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i] == i) {
				return i;
			}
		}

		return -1;
	}

	public static int fastFindMagicIndex(int[] arr) {
		return fastFindMagicIndex(arr, 0, arr.length);
	}

	public static int fastFindMagicIndex(int[] arr, int start, int end) {
		if (start > end) {
			return -1;
		}
		int midIndex = (start + end) / 2;
		int midElem = arr[midIndex];
		if (midElem == midIndex) {
			return midElem;
		}

		int leftInd = Math.min(midIndex - 1, midElem);
		int left = fastFindMagicIndex(arr, start, leftInd);
		if (left > 0) {
			return left;
		}

		int rightInd = Math.max(midIndex + 1, midElem);
		return fastFindMagicIndex(arr, rightInd, end);
	}
}
