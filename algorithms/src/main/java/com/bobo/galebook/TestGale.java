/* **********************************************************************
 * Copyright 2017 VMware, Inc.  All rights reserved. VMware Confidential
 ************************************************************************/
package com.bobo.galebook;

import java.util.ArrayList;

public class TestGale {

   public static void main(String[] args) {
      boolean[][] grid = generateGrid(10, 10);

      ArrayList<Point> path = getPath(grid);
      System.out.println("Path with len=" + path.size() + " found: " + path);
   }


   private static boolean[][] generateGrid(int cols, int rows) {
      boolean[][] grid = new boolean[rows][cols];
      for (int i=0;i<grid.length; i++) {
         for (int j=0;j<grid[i].length; j++) {
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

   static ArrayList<Point> getPath(boolean[][] maze) {
      if (maze == null || maze.length == 0)
         return null;
      ArrayList<Point> path = new ArrayList<>();
      if (getPath(maze, maze.length - 1, maze[0].length - 1, path)) {
         return path;
      }
      return null;

   }

   static boolean getPath(boolean[][] maze, int row, int col, ArrayList<Point> path) {
      /* If out of bounds or not available, return. */
      if (col < 0 || row < 0 || !maze[row][col]) {
         return false;
      }
      boolean isAtOrigin = (row == 0) && (col == 0);
      /* If there's a path from the start to here, add my location. */
      if (isAtOrigin ||
            getPath(maze, row - 1, col, path) ||
            getPath(maze, row, col - 1, path)) {
         Point p = new Point(row, col);
         path.add(p);
         return true;
      }

      return false;
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
