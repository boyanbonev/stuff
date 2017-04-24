package com.bobo;

import java.util.Arrays;

public class Sorts {

	public static void main(String[] args) {
		int[] arr = { 1000000, 100, -14, 17, 324, -23, 0, 34, 35, 35, 7, 4324, 1, 8 };
		System.out.println("Not sorted = " + Arrays.toString(arr));

		Sorts.mergeSort(arr);

		System.out.println("Sorted = " + Arrays.toString(arr));
	}

	public static void mergeSort(int[] arr) {
		int tmp[] = new int[arr.length];
		doMergeSort(arr, tmp, 0, arr.length);
	}

	private static void doMergeSort(int[] arr, int[] tmp, int left, int right) {
		if (right - left < 2) {
			return;
		}
		int middle = (right + left) / 2;

		doMergeSort(arr, tmp, left, middle);
		doMergeSort(arr, tmp, middle, right);

		mergeParts(arr, tmp, left, middle, right);
	}

	private static void mergeParts(int[] arr, int[] tmp, int left, int middle, int right) {

		System.arraycopy(arr, left, tmp, left, right - left);

		int i = left;
		int tmpLeft = left;
		int tmpMiddle = middle;
		while (tmpLeft < middle && tmpMiddle < right) {
			if (tmp[tmpLeft] < tmp[tmpMiddle]) {
				arr[i++] = tmp[tmpLeft++];
			} else {
				arr[i++] = tmp[tmpMiddle++];
			}
		}

		// copy the remaining elements from the left part of the tmp array
		while (tmpLeft < middle) {
			arr[i++] = tmp[tmpLeft++];
		}

		// copy the remaining elements from the right part of the tmp array
		while (tmpMiddle < right) {
			arr[i++] = tmp[tmpMiddle++];
		}
	}

	public static int[] quickSort(int[] arr, int left, int right) {
		if (left < right) {
			int index = partition(arr, left, right);
			quickSort(arr, left, index - 1);
			quickSort(arr, index + 1, right);
		}

		return arr;
	}

	private static int partition(int[] arr, int left, int right) {
		int i = left;
		int j = right;
		int pivot = arr[(left + right) / 2];

		while (i < j) {
			while (arr[i] < pivot) {
				i++;
			}

			while (pivot < arr[j]) {
				j--;
			}

			if (i < j) {
				swap(arr, i, j);
				i++;
				j--;
			}
		}

		return i;
	}

	private static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

}
