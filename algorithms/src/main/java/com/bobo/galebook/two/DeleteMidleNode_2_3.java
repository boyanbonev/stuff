package com.bobo.galebook.two;

import com.bobo.galebook.two.LList.Node;

public class DeleteMidleNode_2_3 {

	public static <T> void deleteNode(LList<T> list, Node<T> node) {
		Node<T> head = list.head;
		if (head.equals(node)) {
			list.head = head.getNext();
		}
		while (head.getNext() != null) {
			if (head.getNext().equals(node)) {
				head.setNext(head.getNext().getNext());
				break;
			}

			head = head.getNext();
		}
	}

	public static <T> void deleteNode2(LList<T> list, Node<T> node) {
		list.remove(node.getData());
	}

	public static void main(String[] args) {
		LList<String> list = new LList<>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		list.add("9");
		list.add("10");

		list.print();

		deleteNode2(list, new Node<>("6"));

		list.print();
	}
}
