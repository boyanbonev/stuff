package com.bobo.galebook.one;

public class OneWay_1_5 {
	public static void main(String[] args) {
		String first = "pale";
		String second = "pales";
		boolean areOneStepAway = new OneWay_1_5().areOneStepAway(first, second);

		System.out.println("Are one step away '" + first + "' and '" + second + "'=" + areOneStepAway);
	}

	// pale - pales
	public boolean areOneStepAway(String first, String second) {
		if (first == null || second==null ) {
			return false;
		}
		if (first.length() == second.length()) {
			if (first.equals(second)) {
				return true;
			}
			int replCnt = 0;
			for (int i = 0; i < first.length(); i++) {
				if (first.charAt(i) != second.charAt(i)) {
					replCnt++;
					if (replCnt > 1) {
						return false;
					}
				}
			}
			return true;
		} else if (first.length() == second.length() - 1) {
			int firstIndex = 0;
			int secondIndex = 0;
			while (firstIndex < first.length()) {
				if (first.charAt(firstIndex) != second.charAt(secondIndex)) {
					secondIndex++;
					if (secondIndex - firstIndex > 1) {
						return false;
					}
				} else {
					firstIndex++;
					secondIndex++;
				}
			}
			return true;
		} else if (first.length() == second.length() + 1) {
			return areOneStepAway(second, first);
		}
		return false;
	}
}
