package com.bobo.galebook.two;

import com.bobo.galebook.two.LList.Node;

public class SumList_2_5 {

	public static LList<Integer> sum(LList<Integer> l1, LList<Integer> l2) {
		Node<Integer> l1current = l1.head;
		Node<Integer> l2current = l2.head;

		LList<Integer> res = new LList<>();
		int carry = 0;
		while (l1current != null && l2current != null) {
			int sum = l1current.getData() + l2current.getData() + carry;

			int currentDigit = sum % 10;
			carry = sum / 10;

			res.add(currentDigit);

			l1current = l1current.getNext();
			l2current = l2current.getNext();
		}

		copyRemining(l1current, res, carry);

		copyRemining(l2current, res, carry);

		return res;
	}

	private static void copyRemining(Node<Integer> sourceNode, LList<Integer> res, int carry) {
		while (sourceNode != null) {
			int sum = sourceNode.getData() + carry;

			int currentDigit = sum % 10;
			carry = sum / 10;

			res.add(currentDigit);

			sourceNode = sourceNode.getNext();
		}
		if (carry > 0) {
			res.add(carry);
		}
	}

	public static void main(String[] args) {
		LList<Integer> l1 = new LList<>();
		l1.add(7);
		l1.add(1);
		l1.add(6);
		// l1 = 617
		System.out.print("First number: ");
		l1.print();

		LList<Integer> l2 = new LList<>();
		l2.add(5);
		l2.add(9);
		l2.add(2);
		l2.add(9);
		// l2 = 9295
		System.out.print("Second number: ");
		l2.print();

		LList<Integer> res = sum(l1, l2);

		System.out.print("Result: ");
		res.print();
	}
}
