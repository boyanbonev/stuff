package com.bobo.galebook.one;

public class IsUnique_1_1 {
	public boolean isUniqueWithoutExtraSpace(String s) {
		for (int i = 0; i < s.length(); i++) {
			char c1 = s.charAt(i);
			for (int j = 0; j < s.length(); j++) {
				if (i != j) {
					char c2 = s.charAt(j);
					if (c1 == c2) {
						System.out.println("Chars at i=" + i + " and j=" + j + " are equal to " + c1);
						return false;
					}
				}
			}
		}

		return true;
	}

	public boolean isUniqueWithBitVector(String s) {
		int bits = 0;
		for (int i = 0; i < s.length(); i++) {
			int c = s.charAt(i) - 'a';
			if ((bits & (1 << c)) == 1) {
				System.out.println("Duplication of char " + (char) (c + 'a'));
				return false;
			}
			bits = bits | (1 << c);
		}

		return true;
	}

	public static void main(String[] args) {
		String s = "qwertyuiopasfdzxvabnm";
		boolean isUnique = new IsUnique_1_1().isUniqueWithBitVector(s);
		System.out.println("Is '" + s + "' unique: " + isUnique);
	}
}
