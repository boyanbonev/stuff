package com.bobo.dijkstra;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DijsktraAlg {
	private int[][] neighbours;
	private int[][] weights;

	private int[] previous;
	private int[] minDistance;

	private Set<Integer> verteses;

	public DijsktraAlg(int[][] neighbours, int[][] weights) {
		this.neighbours = neighbours;
		this.weights = weights;
		this.minDistance = new int[weights.length];
		this.verteses = new HashSet<>(weights.length);
		this.previous = new int[weights.length];

		for (int i = 0; i < weights.length; i++) {
			minDistance[i] = Integer.MAX_VALUE;
			verteses.add(i);
		}
	}

	public void process(int startNode) {
		minDistance[startNode] = 0;
		while (!verteses.isEmpty()) {
			int currentVertex = selectVertexWithMinDistance();
			verteses.remove(currentVertex);

			int[] neighboursOfVertex = getNeighbours(currentVertex);

			for (int neighboutIndex = 0; neighboutIndex < neighboursOfVertex.length; neighboutIndex++) {
				int neighbour = neighboursOfVertex[neighboutIndex];
				long altDist = 0L + minDistance[currentVertex] + getWeight(currentVertex, neighboutIndex);
				if (altDist < minDistance[neighbour]) {
					minDistance[neighbour] = (int) altDist;
					previous[neighbour] = currentVertex;
				}
			}
		}
	}

	private int selectVertexWithMinDistance() {
		int minLength = Integer.MAX_VALUE;
		int vertexId = 0;
		for (int i = 0; i < minDistance.length; i++) {
			if (minDistance[i] <= minLength && verteses.contains(i)) {
				minLength = minDistance[i];
				vertexId = i;
			}
		}

		return vertexId;
	}

	private int getWeight(int vertex, int neighbourIndex) {
		return weights[vertex][neighbourIndex];
	}

	private int[] getNeighbours(int nodeId) {
		return neighbours[nodeId];
	}

	public List<Integer> getPathToVertex(int destVertex) {
		if (destVertex > previous.length - 1) {
			throw new IllegalArgumentException("The requested vertext #" + destVertex
					+ " is bigger than the maximum vertex in the graph #" + (previous.length - 1));
		}
		int currentVertex = destVertex;
		List<Integer> result = new LinkedList<Integer>();
		while (previous[currentVertex] != 0) {
			result.add(currentVertex);
			currentVertex = previous[currentVertex];
		}
		result.add(currentVertex);
		// add the first element
		result.add(0);
		Collections.reverse(result);

		return result;
	}

	private int getDestinationCost(int destVertex) {
		return minDistance[destVertex];
	}

	public static void main(String[] args) {
		//@formatter:off
		int [][]neighbours = new int[][] {
				{1, 2, 3 },
				{4},
				{4, 5},
				{5},
				{},
				{ 6, 4 },
				{} };
		int[][] weights = new int[][] {
				{ 7, 5, 10 },
				{ 17 },
				{ 2, 20 },
				{ 1 },
				{},
				{ 8, 1 },
				{} };

		//		int [][]neighbours = new int[][] {
		//				{1, 2, 4 },
		//				{10},
		//				{6,7},
		//				{7},
		//				{9},
		//				{8},
		//				{},
		//				{9},
		//				{9},
		//				{10},
		//				{}
		//		};
		//
		//		int[][] weights = new int[][] {
		//				{85, 217, 173 },
		//				{600},
		//				{186, 103},
		//				{183},
		//				{502},
		//				{250},
		//				{},
		//				{167},
		//				{84},
		//				{40},
		//				{}};
		//@formatter:on

		DijsktraAlg dijsktra = new DijsktraAlg(neighbours, weights);
		int startVertex = 0;
		int destVertex = 6;

		dijsktra.process(startVertex);
		List<Integer> pathToVertex = dijsktra.getPathToVertex(destVertex);

		System.out.println("Path from node #" + startVertex + " to #" + destVertex + ": " + pathToVertex);
		System.out.println("Dest cost: " + dijsktra.getDestinationCost(destVertex));

	}

}
