package com.bobo.galebook.one;

import java.util.HashMap;
import java.util.Map;

public class PalindromePerm_1_4 {
	/**
	 * @param s
	 * @return
	 */
	public boolean isPalindromePerm(String s) {
		char[] chars = s.toCharArray();

		Map<Character, Integer> map = new HashMap<>();
		int oddCnt = 0;
		for (char c : chars) {
			Integer num = map.get(c);
			if (num == null) {
				num = new Integer(0);
			}

			num += 1;
			if (num % 2 != 0) {
				oddCnt++;
			} else {
				oddCnt--;
			}
			map.put(c, num);
		}

		return oddCnt <= 1;
	}

	public static void main(String[] args) {
		String s = "ab jgkkj ab";
		boolean hasPalindrome = new PalindromePerm_1_4().isPalindromePerm(s);

		System.out.println("'" + s + "' " + (hasPalindrome ? "has" : "doesn't have") + " a palindrome");
	}

}
