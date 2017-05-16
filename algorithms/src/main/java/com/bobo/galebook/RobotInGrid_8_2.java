/* **********************************************************************
 * Copyright 2017 VMware, Inc.  All rights reserved. VMware Confidential
 ************************************************************************/
package com.bobo.galebook;

import java.util.LinkedList;
import java.util.List;

public class RobotInGrid_8_2 {

   private static Point end;

   public static void main(String[] args) {
      boolean[][] grid = generateGrid(10, 10);
      end = new Point(9, 9);
      Point start = new Point(0, 0);

      List<Point> path = findPath(grid, start);
      System.out.println("Path with len=" + path.size() + " found: " + path);
   }

   private static boolean[][] generateGrid(int cols, int rows) {
      boolean[][] grid = new boolean[rows][cols];
      for (int i = 0; i < grid.length; i++) {
         for (int j = 0; j < grid[i].length; j++) {
            grid[i][j] = true;
         }
      }
      putObstacles(grid);
      return grid;
   }

   private static void putObstacles(boolean[][] grid) {
      int cols = grid[0].length;
      for (int i = 0; i < grid.length - 1; i++) {
         grid[cols - i - 1][i] = false;
      }
   }

   public static List<Point> findPath(boolean[][] grid, Point startingPoint) {
      if (grid == null || grid.length == 0) {
         return null;
      }

      List<Point> path = new LinkedList<>();
      findPath(grid, startingPoint, path);

      return path;
   }

   public static boolean findPath(boolean[][] grid, Point currentPoint, List<Point> path) {
      if (isPointNotOk(currentPoint, grid)) {
         return false;
      }

      if (currentPoint.equals(end) ||
            findPath(grid, currentPoint.moveDown(), path) ||
            findPath(grid, currentPoint.moveRight(), path)) {
         path.add(currentPoint);
      }

      return false;
   }

   private static boolean isPointNotOk(Point point, boolean[][] grid) {
      return point.row >= grid.length ||
            point.row < 0 || // row
            point.col >= grid[0].length ||
            point.col < 0 || // col
            !grid[point.row][point.col];
   }

   static class Point {
      private int row, col;

      public Point(int row, int col) {
         this.row = row;
         this.col = col;
      }

      public int getCol() {
         return col;
      }

      public int getRow() {
         return row;
      }

      @Override
      public String toString() {
         return "(" + row + "," + col + ")";
      }

      @Override
      public boolean equals(Object obj) {
         if (!(obj instanceof Point)) {
            // instanceof makes null check
            return false;
         }

         Point other = (Point) obj;
         return this.row == other.row && this.col == other.col;
      }

      public Point moveRight() {
         return new Point(row, col + 1);
      }

      public Point moveDown() {
         return new Point(row + 1, col);
      }
   }
}
