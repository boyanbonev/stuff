package com.bobo.galebook.two;

import com.bobo.galebook.two.LList.Node;

public class PalindromeList_2_6 {

	public static <T> boolean isPalindrome(LList<T> list) {
		Node<T> current = list.head;

		LList<T> reversed = reverseList(current);

		return list.equals(reversed);
	}

	private static <T> LList<T> reverseList(Node<T> current) {
		LList<T> reversed = new LList<>();
		while (current != null) {
			reversed.addFirst(current.getData());
			current = current.getNext();
		}

		return reversed;
	}

	public static <T> boolean isPalindromeRecursive(LList<T> list) {
		return recurse(list.head, list.length()).value;
	}

	private static class Result<T> {
		boolean value;
		Node<T> pointer;

		public Result(boolean value, Node<T> pointer) {
			this.value = value;
			this.pointer = pointer;
		}
	}

	public static <T> Result<T> recurse(Node<T> node, int length) {
		if (length == 0) {
			return new Result<>(true, node);
		}
		if (length == 1) {
			return new Result<>(true, node.getNext());
		}

		Result<T> res = recurse(node.getNext(), length - 2);
		if (!res.value || res.pointer == null) {
			return res;
		}
		Node<T> middle = res.pointer;
		return new Result<>(middle.getData().equals(node.getData()), middle.getNext());
	}

	public static void main(String[] args) {
		LList<String> list = new LList<>();
		list.add("a");
		list.add("b");
		list.add("c");
		list.add("g");
		list.add("a");

		list.print();
		System.out.println("The list is palindrome: " + isPalindromeRecursive(list));
	}

}
