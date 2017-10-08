/* **********************************************************************
 * Copyright 2017 VMware, Inc.  All rights reserved. VMware Confidential
 ************************************************************************/
package com.bobo.galebook.eight;

public class TripleStep_8_1 {

   public static void main(String[] args) {
      int stepsCount = 100;
      long variations = calcVariationsWithMemoization(stepsCount, new long[stepsCount + 1]/* 1 based array*/);
      //      long variations = calcVariations(stepsCount);

      System.out.println("There are " + variations + " ways to hop over " + stepsCount + " steps");
   }

   public static long calcVariations(int stepsCount) {
      if (stepsCount == 0) {
         return 1;
      } else if (stepsCount < 0) {
         return 0;
      }

      return calcVariations(stepsCount - 1) + calcVariations(stepsCount - 2) + calcVariations(stepsCount - 3);
   }

   public static long calcVariationsWithMemoization(int stepsCount, long[] memo) {
      if (stepsCount == 0) {
         return 1;
      } else if (stepsCount < 0) {
         return 0;
      }

      if (memo[stepsCount] == 0) {
         memo[stepsCount] = calcVariationsWithMemoization(stepsCount - 1, memo) +
               calcVariationsWithMemoization(stepsCount - 2, memo) +
               calcVariationsWithMemoization(stepsCount - 3, memo);
      }

      return memo[stepsCount];
   }
}
