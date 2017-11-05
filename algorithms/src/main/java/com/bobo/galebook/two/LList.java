package com.bobo.galebook.two;

public class LList<T> {
	public Node<T> head;

	public void add(T data) {
		if (head == null) {
			head = new Node<>(data);
			return;
		}

		Node<T> n = head;
		while (n.getNext() != null) {
			n = n.getNext();
		}

		n.setNext(new Node<>(data));
	}

	public void remove(T data) {
		if (head == null) {
			return;
		}
		if (head.getData().equals(data)) {
			head = head.next;
		}

		Node<T> n = head;
		while (n.getNext() != null) {
			if (n.getData().equals(data)) {
				n.next = n.next.next;
				return;
			}

			n = n.next;
		}
	}

	public void print() {
		Node<T> n = head;
		while (n != null) {
			System.out.print(n + " ");
			n = n.next;
		}
		System.out.println();
	}

	public static class Node<T> {
		private T data;
		private Node<T> next;

		public Node(T data) {
			this.setData(data);
		}

		public Node<T> getNext() {
			return next;
		}

		public void setNext(Node<T> next) {
			this.next = next;
		}

		public T getData() {
			return data;
		}

		public void setData(T data) {
			this.data = data;
		}

		@Override
		public String toString() {
			return "" + data;
		}
	}
}