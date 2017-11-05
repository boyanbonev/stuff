package com.bobo.galebook.two;

import java.util.HashSet;
import java.util.Set;

import com.bobo.galebook.two.LList.Node;

public class RemoveDups_2_1 {
	public static void main(String[] args) {
		LList<String> list = new LList<>();
		list.add("a");
		list.add("b");
		list.add("a");
		list.add("d");
		list.add("c");
		list.add("d");
		list.add("d");
		list.add("c");
		list.add("a");
		list.add("a");
		list.add("b");
		list.print();

		LList<String> l = new RemoveDups_2_1().removeDuplicatesWithoutBuffer(list);
		l.print();
	}

	public LList<String> removeDuplicates(LList<String> list) {
		Set<String> duplicates = new HashSet<>();
		Node<String> n = list.head;
		duplicates.add(n.getData());
		while (n.getNext() != null) {
			String nextData = n.getNext().getData();
			if (duplicates.contains(nextData)) {
				n.setNext(n.getNext().getNext());
			} else {
				duplicates.add(nextData);
				n = n.getNext();
			}
		}
		return list;
	}

	public LList<String> removeDuplicatesWithoutBuffer(LList<String> list) {
		Node<String> current = list.head;
		while (current != null) {
			Node<String> n = current;
			String data = current.getData();
			while (n.getNext() != null) {
				if (n.getNext().getData().equals(data)) {
					n.setNext(n.getNext().getNext());
				} else {
					n = n.getNext();
				}
			}
			current = current.getNext();
		}
		return list;
	}

}
