package com.bobo.problems;

public class ABBADiv1 {
	private static final char A = 'A';
	private static final char B = 'B';

	private static final String POSSIBLE = "Possible";
	private static final String IMPOSSIBLE = "Impossible";

	public String canObtain(String initial, String target) {
		if (initial == null || initial.trim().length() < 1 || initial.trim().length() > 49) {
			return IMPOSSIBLE;
		}

		if (target == null || target.trim().length() < 2 || target.trim().length() > 50) {
			return IMPOSSIBLE;
		}

		return validate(initial, target) ? POSSIBLE : IMPOSSIBLE;
	}

	public boolean validate(String initial, String target) {
		if (initial.length() == target.length()) {
			return initial.toString().equals(target);
		}
		final String a = new StringBuilder(initial).append(A).toString();
		final String b = new StringBuilder(initial).append(B).reverse().toString();
		
		boolean result = false;
		if (target.contains(a) || new StringBuilder(target).reverse().indexOf(a) >= 0) {
			result |= validate(a, target) ;
		}
		
		if (target.contains(b) || new StringBuilder(target).reverse().indexOf(b) >= 0) {
			result |= validate(b, target) ;
		}

		return result;
	}

	public static void main(String[] args) {
		ABBADiv1 abba = new ABBADiv1();
		String initial = "AAABAAABB";
		String target = "BAABAAABAABAABBBAAAAAABBAABBBBBBBABB";//impossible
		// String initial = "A";
		// String target = "BABA";
		// StringBuilder sb = new StringBuilder();
		// for (int i = 0; i < 1000; i++) {
		// sb.append("A");
		// }
		long startTime = System.currentTimeMillis();
		System.out.println(abba.canObtain(initial, target));
		System.out.println("Execution time = " + (System.currentTimeMillis() - startTime)/1000 + "s");
	}
}
