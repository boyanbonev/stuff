package com.bobo.galebook.two;

import com.bobo.galebook.two.LList.Node;

public class LoopDetection_2_8 {

	private static <T> Node<T> findLoop(Node<T> node) {

		Node<T> fast = node;
		Node<T> slow = node;
		while (fast != null && slow != null) {
			fast = fast.getNext().getNext();
			slow = slow.getNext();
			if (fast == slow) {
				break;
			}
		}

		// if the fast pointer doesn't catch up the slow one, there's no loop
		if (fast == null || fast.getNext() == null) {
			return null;
		}

		slow = node;
		while (slow != fast) {
			slow = slow.getNext();
			fast = fast.getNext();
		}

		return fast;
	}

	public static void main(String[] args) {
		LList<String> list = new LList<>();

		list.add("A");
		list.add("B");
		list.add("C");
		list.add("D");
		list.add("E");
		list.print();

		Node<String> node = list.head;
		Node<String> cNode = null;
		while (node.getNext() != null) {
			if (node.getData().equals("C")) {
				cNode = node;
			}
			node = node.getNext();
		}
		node.setNext(cNode);

		Node<String> loopStart = findLoop(list.head);

		System.out.println("Loop start at node: " + loopStart);
	}
}
