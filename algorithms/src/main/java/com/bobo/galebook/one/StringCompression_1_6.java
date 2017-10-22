package com.bobo.galebook.one;

public class StringCompression_1_6 {

	public String compress(String s) {
		int compressedCount = countLenWithCompression(s);
		if (compressedCount >= s.length()) {
			return s;
		}

		char lastChar = s.charAt(0);
		StringBuilder result = new StringBuilder(compressedCount);
		result.append(lastChar);
		int cnt = 0;
		for (char ch : s.toCharArray()) {
			if (ch != lastChar) {
				result.append(cnt);
				result.append(ch);
				cnt = 0;
				lastChar = ch;
			}
			cnt++;
		}
		result.append(cnt);

		return result.toString();
	}

	private int countLenWithCompression(String s) {
		int cnt = 2;
		char lastChar = s.charAt(0);
		for (char ch : s.toCharArray()) {
			if (ch != lastChar) {
				cnt+=2;
				lastChar = ch;
			}
		}
		return cnt;
	}

	public static void main(String[] args) {
		// String s = "aabcccccaaa";
		String s = "aaa";
		String compressed = new StringCompression_1_6().compress(s);

		System.out.println(s + ", after compression: " + compressed);
	}

}
