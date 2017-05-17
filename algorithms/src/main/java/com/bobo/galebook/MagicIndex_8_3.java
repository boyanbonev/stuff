/* **********************************************************************
 * Copyright 2017 VMware, Inc.  All rights reserved. VMware Confidential
 ************************************************************************/
package com.bobo.galebook;

public class MagicIndex_8_3 {
   public static void main(String[] args) {
      int[] arr = new int[] { 1, 0, 2, 4 };
      int index = findMagicIndex(arr);
      if (index != -1) {
         System.out.println("Found index = " + index);
      }

   }

   public static int findMagicIndex(int[] arr) {
      for (int i = 0; i < arr.length; i++) {
         if (arr[i] == i) {
            return i;
         }
      }

      return -1;
   }
}
