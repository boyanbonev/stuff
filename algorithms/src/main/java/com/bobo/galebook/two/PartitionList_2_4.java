package com.bobo.galebook.two;

import com.bobo.galebook.two.LList.Node;

public class PartitionList_2_4 {
	public static void main(String[] args) {
		LList<Integer> list = new LList<>();
		list.add(3);
		list.add(5);
		list.add(8);
		list.add(5);
		list.add(10);
		list.add(2);
		list.add(1);

		System.out.println("Raw list:");
		list.print();

		System.out.println("");
		System.out.println("Partitioned list:");
		Node<Integer> res = partitionList(list.head, 5);

		print(res);
	}

	static Node<Integer> partitionList(Node<Integer> head, int x) {
		Node<Integer> current = head;

		Node<Integer> biggerCurrentPtr = null;
		Node<Integer> smallerCurrentPtr = null;

		Node<Integer> smallerHead = null;
		Node<Integer> biggerHead = null;

		while (current != null) {
			Integer data = current.getData();
			Node<Integer> tmp = new Node<>(data);
			if (data >= x) {
				if (biggerCurrentPtr == null) {
					biggerCurrentPtr = biggerHead = tmp;
				} else {
					biggerCurrentPtr.setNext(tmp);
					biggerCurrentPtr = tmp;
				}
			} else {
				if (smallerCurrentPtr == null) {
					smallerCurrentPtr = smallerHead = tmp;
				} else {
					smallerCurrentPtr.setNext(tmp);
					smallerCurrentPtr = tmp;
				}
			}

			current = current.getNext();
		}

		// print(smallerHead);
		// print(biggerHead);

		if (smallerCurrentPtr != null) {
			smallerCurrentPtr.setNext(biggerHead);
		} else {
			smallerHead = biggerHead;
		}

		return smallerHead;
	}

	private static <T> void print(Node<T> head) {
		while (head != null) {
			System.out.print(head.getData() + " ");
			head = head.getNext();
		}
		System.out.println();
	}
}
