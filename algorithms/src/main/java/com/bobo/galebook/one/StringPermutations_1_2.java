package com.bobo.galebook.one;

import java.util.Arrays;

public class StringPermutations_1_2 {
	public boolean isPermutation(String s1, String s2) {
		if (s1.length() != s2.length()) {
			return false;
		}

		char[] ch1 = s1.toCharArray();
		Arrays.sort(ch1);

		char[] ch2 = s2.toCharArray();
		Arrays.sort(ch2);

		for (int i = 0; i < ch1.length; i++) {
			if (ch1[i] != ch2[i]) {
				return false;
			}
		}

		return true;
	}

	public static void main(String[] args) {
		String s1 = "baabb";
		String s2 = "abbaa";
		boolean isPerm = new StringPermutations_1_2().isPermutation(s1, s2);
		System.out.println("'" + s1 + "' is" + (isPerm ? " " : " NOT ") + "a permutation of '" + s2 + "'");
	}
}
