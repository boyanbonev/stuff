package com.bobo.galebook.two;

import com.bobo.galebook.two.LList.Node;

public class Intersection_2_7 {

	private static class Result<T> {
		public Result(Node<T> tail, int size) {
			this.tail = tail;
			this.size = size;
		}

		Node<T> tail;
		int size;

	}

	public static <T> Node<T> findIntersection(Node<T> node1, Node<T> node2) {
		if (node1 == node2) {
			return node1;
		}

		Result<T> tailAndSize1 = getTailAndSize(node1);
		Result<T> tailAndSize2 = getTailAndSize(node2);

		if (tailAndSize1.tail != tailAndSize2.tail) {
			return null;
		}

		Node<T> shorter;
		Node<T> longer;
		if (tailAndSize1.size < tailAndSize2.size) {
			shorter = node1;
			longer = node2;
		} else {
			shorter = node2;
			longer = node1;
		}

		// after skipping the size diff the 2 list must be with equal size
		longer = getKthElement(longer, Math.abs(tailAndSize1.size - tailAndSize2.size));
		while (longer != null && shorter != null) {
			if (shorter.equals(longer)) {
				return longer;
			}
			shorter = shorter.getNext();
			longer = longer.getNext();
		}

		return null;
	}

	private static <T> Node<T> getKthElement(Node<T> node, int k) {
		while (node != null && k-- != 0) {
			node = node.getNext();
		}

		return node;
	}

	private static <T> Result<T> getTailAndSize(Node<T> node) {
		Node<T> current = node;
		int size = 1;// the size is min 1
		while(current.getNext() != null) {
			current = current.getNext();
			size++;
		}

		return new Result<>(current, size);
	}

	public static void main(String[] args) {
		LList<Integer> list1 = new LList<>();
		list1.add(3);
		list1.add(1);
		list1.add(5);
		list1.add(9);
		list1.add(7);
		list1.add(2);
		list1.add(1);

		LList<Integer> list2 = new LList<>();
		list2.add(4);
		list2.add(6);
		Node<Integer> list1Current = list1.head;
		while (list1Current != null && list1Current.getData() != 7) {
			list1Current = list1Current.getNext();
		}

		Node<Integer> list2Current = list2.head;
		while (list2Current.getNext() != null) {
			list2Current = list2Current.getNext();
		}

		list2Current.setNext(list1Current);

		System.out.print("List1: ");
		list1.print();

		System.out.print("List2: ");
		list2.print();

		Node<Integer> intersection = findIntersection(list1.head, list2.head);
		System.out.println("Intersecting node: " + intersection);
	}
}
