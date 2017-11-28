package com.bobo.problems;

import java.util.HashMap;
import java.util.Map;

public class RandomListPointer {

	private static class RandomListNode {
		int label;
		RandomListNode next, random;

		RandomListNode(int x) {
			this.label = x;
		}
	};
	
	public static RandomListNode copyRandomList(RandomListNode head) {
		if (head == null) {
			return null;
		}
		RandomListNode current = head.next;
		Map<RandomListNode, RandomListNode> map = new HashMap<>();
		RandomListNode res = copyNode(head, map);
		res.random = copyNode(head.random, map);

		RandomListNode resPtr = res;

		while (current != null) {
			RandomListNode newNode = copyNode(current, map);
			RandomListNode cachedRandom = copyNode(current.random, map);
			newNode.random = cachedRandom;
			resPtr.next = newNode;
			
			resPtr = resPtr.next;
			current = current.next;
		}
		
		return res;
	}
	
	private static RandomListNode copyNode(RandomListNode node, Map<RandomListNode, RandomListNode> map) {
		if (node == null) {
			return null;
		}

		RandomListNode cachedNode = map.get(node);
		if (cachedNode != null) {
			return cachedNode;
		}

		RandomListNode newNode = new RandomListNode(node.label);
		map.put(node, newNode);

		return newNode;
	}
	
	public static RandomListNode copyRandomListRecurssion(RandomListNode head) {
		if (head == null) {
			return null;
		}

		RandomListNode res = new RandomListNode(head.label);
		res.next = copyRandomListRecurssion(head.next);
		if (head.random != null) {
			res.random = new RandomListNode(head.random.label);
		}

		return res;
	}

	public static void print(RandomListNode list) {
		while (list != null) {
			System.out.println(
					"Node: " + list.label + "(" + list + "); next = " + ((list.next != null) ? list.next.label : "null")
							+ "; random: "
							+ ((list.random != null) ? list.random.label + "(" + list.random + ")" : "null"));
			list = list.next;
		}
	}
	
	public static void main(String[] args) {
		RandomListNode list = new RandomListNode(1);
		RandomListNode n2 = new RandomListNode(2);
		RandomListNode n3 = new RandomListNode(3);
		RandomListNode n4 = new RandomListNode(4);
		RandomListNode n5 = new RandomListNode(5);
		
		n5.random = list;
		n4.random = null;
		n3.random = n2;
		n2.random = n5;
		list.random = n3;
		
		list.next = n2;
		n2.next = n3;
		n3.next = n4;
		n4.next = n5;
		n5.next = null;
		
		System.out.println("Original:");
		print(list);
		
		RandomListNode copy = copyRandomListRecurssion(list);
		
		System.out.println("\nCopy:");
		print(copy);
	}
}
