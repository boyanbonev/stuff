package com.bobo.galebook.three;

import com.bobo.galebook.two.LList.Node;

public class Queue<T> {

	private Node<T> first;
	private Node<T> last;

	public void add(T data) {
		Node<T> newNode = new Node<>(data);

		if (last == null) {
			first = last = newNode;
		} else {
			last.setNext(newNode);
			last = newNode;
		}
	}

	public T remove() {
		if (first == null) {
			return null;
		}

		T data = first.getData();
		first = first.getNext();
		if (first == null) {
			last = null;
		}

		return data;
	}

	public T peek() {
		if (first == null) {
			return null;
		}

		return first.getData();
	}

	public boolean isEmpty() {
		return first == last && last == null;
	}

	public void print() {
		Node<T> n = first;
		while (n != null) {
			System.out.print(n + " ");
			n = n.getNext();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Queue<Integer> queue = new Queue<>();
		for (int i = 1; i < 5; i++) {
			System.out.print(i + " ");
			queue.add(i);
		}

		// System.out.println();
		// queue.print();

		System.out.println();
		for (int i = 1; i < 5; i++) {
			System.out.print(queue.peek() + " ");
			queue.remove();
		}
		System.out.println();

		System.out.println("Is empty = " + queue.isEmpty());
	}

}
