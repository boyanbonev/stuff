/* **********************************************************************
 * Copyright 2017 VMware, Inc.  All rights reserved. VMware Confidential
 ************************************************************************/
package com.bobo.galebook.eight;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.Validate;

public class RobotInGrid_8_2 {

	public static void main(String[] args) {
		int n = 50;
		Set<Point> obstacles = getObstacles(n);

		Point startPoint = new Point(0, 0);
		Point endPoint = new Point(n - 1, n - 1);

		Grid grid = new Grid(n, n, obstacles, startPoint, endPoint);

		long startTime = System.currentTimeMillis();
		List<Point> path = findPath(grid);
		System.out.println("Path found for " + (System.currentTimeMillis() - startTime) + "ms");
		System.out.println("Path with len=" + path.size() + " found: " + path);
	}

	private static Set<Point> getObstacles(int n) {
		Set<Point> obstacles = new HashSet<>();
		for (int i = 0; i < n - 1; i++) {
			obstacles.add(new Point(n - i - 1, i));
		}

		return obstacles;
	}

	public static List<Point> findPath(Grid grid) {
		if (grid == null) {
			return Collections.emptyList();
		}

		List<Point> path = new LinkedList<>();
		findPath(grid, grid.getStartPoint(), path);

		// the result is in reversed order, so fix this
		Collections.reverse(path);

		return path;
	}

	public static boolean findPath(Grid grid, Point currentPoint,
			List<Point> path) {
		if (grid.isPointVisited(currentPoint)) {
			return false;
		}
		if (!grid.isPointInGrid(currentPoint)) {
			grid.visitPoint(currentPoint);
			return false;
		}

		if (currentPoint.equals(grid.getEndPoint()) || findPath(grid, currentPoint.moveDown(), path)
				|| findPath(grid, currentPoint.moveRight(), path)) {
			path.add(currentPoint);
			return true;
		}

		grid.visitPoint(currentPoint);
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

		@Override
		public int hashCode() {
			return Integer.hashCode(row) * 13 + Integer.hashCode(col);
		}

		public Point moveRight() {
			return new Point(row, col + 1);
		}

		public Point moveDown() {
			return new Point(row + 1, col);
		}
	}

	static class Grid {
		private final int gridRows;
		private final int gridCols;
		private final Set<Point> obstacles;
		private final Point startPoint;
		private final Point endPoint;
		private final Set<Point> visited;

		public Grid(int gridRows, int gridCols, Set<Point> obstacles, Point startPoint, Point endPoint) {

			Validate.isTrue(gridRows > 0);
			Validate.isTrue(gridCols > 0);
			Validate.notNull(obstacles);
			Validate.notNull(startPoint);
			Validate.notNull(endPoint);

			this.gridRows = gridRows;
			this.gridCols = gridCols;
			this.obstacles = obstacles;
			this.startPoint = startPoint;
			this.endPoint = endPoint;
			visited = new HashSet<>();
		}

		public Point getStartPoint() {
			return startPoint;
		}

		public Point getEndPoint() {
			return endPoint;
		}

		public void visitPoint(Point point) {
			visited.add(point);
		}

		public boolean isPointVisited(Point point) {
			return visited.contains(point);
		}

		public boolean isPointInGrid(Point point) {
			if (point == null) {
				return false;
			}

			return point.getCol() >= 0 && point.getCol() < gridCols && point.getRow() >= 0
					&& point.getRow() < gridRows && !obstacles.contains(point);

		}
	}
}
