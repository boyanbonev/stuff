package com.bobo.galebook.two;

import com.bobo.galebook.two.LList.Node;

public class KtoTheLast_2_2 {
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
		// list.print();

		int k = 4;
		Node<String> res = new KtoTheLast_2_2().getKtoTheLastIterative(list, k);
		System.out.println(k + "th elem to the end is " + res.getData());
	}

	private <T> Node<T> getKtoTheLastIterative(LList<T> l, int k) {
		Node<T> p1 = l.head;
		Node<T> p2 = p1;
		while (p1.getNext() != null && k-- > 0) {
			p1 = p1.getNext();
		}
		while (p1.getNext() != null) {
			p1 = p1.getNext();
			p2 = p2.getNext();
		}

		return p2;
	}

	private <T> Node<T> getKtoTheLastInternal(Node<T> n, int k, int[] i) {
		if (n == null) {
			return null;
		}
		Node<T> res = getKtoTheLastInternal(n.getNext(), k, i);
		if (i[0]++ == k) {
			return n;
		}
		return res;
	}

	public <T> Node<T> getKtoTheLast(LList<T> l, int k) {
		return getKtoTheLastInternal(l.head, k, new int[] { 0 });
	}
}
