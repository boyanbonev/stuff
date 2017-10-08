package com.bobo.galebook.one;

public class URLify_1_3 {

	public String replaceSpaces(char[] chars, int originalLength) {

		int spaceCnt = 0;
		// count how much space we need for the extra characters
		for (int i = 0; i < originalLength; i++) {
			if (chars[i] == ' ') {
				spaceCnt++;
			}
		}

		int pos = originalLength + 2 * spaceCnt - 1;
		for (int i = originalLength - 1; i > 0; i--) {
			char c = chars[i];
			if (c == ' ') {
				chars[pos] = '%';
				chars[pos - 1] = '2';
				chars[pos - 2] = '0';
				pos = pos - 3;
			} else {
				chars[pos--] = c;
			}
		}

		return new String(chars);
	}

	public static void main(String[] args) {
		char[] chars = new char[12];
		chars[0] = 'a';
		chars[1] = ' ';
		chars[2] = 'b';
		chars[3] = ' ';
		chars[4] = 'c';
		chars[5] = ' ';

		System.out.println(new String(chars));
		String s = new URLify_1_3().replaceSpaces(chars, 6);

		System.out.println(s);
	}
}
