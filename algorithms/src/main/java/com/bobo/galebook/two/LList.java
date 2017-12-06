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

	public void addFirst(T data) {
		Node<T> node = new Node<>(data);
		node.setNext(head);
		head = node;
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
			if (n.getNext().getData().equals(data)) {
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

	public int length() {
		Node<T> n = head;
		int cnt = 0;
		while (n != null) {
			n = n.next;
			cnt++;
		}

		return cnt;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LList<?>)) {
			return false;
		}

		@SuppressWarnings("unchecked")
		Node<T> otherPtr = ((LList<T>) obj).head;
		Node<T> thisPtr = head;
		while (otherPtr != null && thisPtr != null) {
			if (!otherPtr.equals(thisPtr)) {
				return false;
			}
			otherPtr = otherPtr.getNext();
			thisPtr = thisPtr.getNext();
		}

		return otherPtr == null && thisPtr == null;
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

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Node)) {
				return false;
			}

			@SuppressWarnings("unchecked")
			Node<T> object = (Node<T>) obj;

			return object.getData().equals(data);
		}

		@Override
		public int hashCode() {
			return data.hashCode();
		}
	}
}