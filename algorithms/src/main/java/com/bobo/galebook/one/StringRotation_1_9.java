package com.bobo.galebook.one;

public class StringRotation_1_9 {

	/*
	 * if we split the s1 into x and y parts, then in order for s2 to be a
	 * rotation of s1 it must be equal to yx, no matter the division point.
	 * s1=xy s2=yx Having this in mind yx is always a substring of xyxy => s2 is
	 * a sub-string of s1s1
	 */
	public boolean isRotation(String s1, String s2) {
		if (s1 != null && s2 != null && s1.length() != s2.length()) {
			return false;
		}

		return (s1 + s1).indexOf(s2) > 0;
	}

	public static void main(String[] args) {
		String s1 = "waterbottle";
		String s2 = "erbottlewat";
		boolean rotation = new StringRotation_1_9().isRotation(s1, s2);
		System.out.println("'" + s1 + "' is " + (rotation ? "" : "not ") + "a rotation of '" + s2 + "'");
	}

}
