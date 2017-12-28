package com.bobo.galebook.three;

import com.bobo.galebook.two.LList.Node;

public class Stack<T> {
	private Node<T> top;

	private int size;

	public T pop() {
		if (top == null) {
			return null;
		}
		T data = top.getData();
		top = top.getNext();
		size--;

		return data;
	}

	public void push(T data) {
		Node<T> newNode = new Node<>(data);
		if (top != null) {
			newNode.setNext(top);
		}
		top = newNode;
		size++;
	}

	public T peek() {
		if (top == null) {
			return null;
		}

		return top.getData();
	}

	public boolean isEmpty() {
		return top == null;
	}

	public int size() {
		return size;
	}

	public void print() {
		Node<T> n = top;
		while (n != null) {
			System.out.print(n + " ");
			n = n.getNext();
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<>();
		for (int i = 1; i < 5; i++) {
			System.out.print(i + " ");
			stack.push(i);
		}

		// stack.print();

		System.out.println();
		for (int i = 1; i < 5; i++) {
			System.out.print(stack.pop() + " ");
		}
	}

}
