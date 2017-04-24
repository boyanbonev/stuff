package com.bobo.problems;

public class ABBA {
   private static final char A = 'A';

   private static final String POSSIBLE = "Possible";
   private static final String IMPOSSIBLE = "Impossible";

   public String canObtain(String initial, String target) {
      if (initial == null ||
            initial.trim().length() < 1 ||
            initial.trim().length() > 999) {
         return IMPOSSIBLE;
      }

      if (target == null ||
            target.trim().length() < 2 ||
            target.trim().length() > 1000) {
         return IMPOSSIBLE;
      }

      return isPossible(initial, target) ? POSSIBLE : IMPOSSIBLE;
   }

   private boolean isPossible(String initial, String target) {
      final StringBuilder sb = new StringBuilder(target);
      while (initial.length() != sb.length()) {
         char targetLastChar = sb.charAt(sb.length() - 1);
         if (targetLastChar == A) {
            unApplyA(sb);
         } else {
            unApplyRevB(sb);
         }
      }

      return initial.equals(sb.toString());
   }

   private void unApplyA(StringBuilder sb) {
      sb.deleteCharAt(sb.length() - 1);
   }

   private void unApplyRevB(StringBuilder sb) {
      sb.deleteCharAt(sb.length() - 1);
      sb.reverse();
   }

   public static void main(String[] args) {
      ABBA abba = new ABBA();
      String initial = "AB";
      //      StringBuilder sb = new StringBuilder();
      //      for (int i = 0; i < 1000; i++) {
      //         sb.append("A");
      //      }
      String target = "ABB";

      System.out.println(abba.canObtain(initial, target));
   }
}
