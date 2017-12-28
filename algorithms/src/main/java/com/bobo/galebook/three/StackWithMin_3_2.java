package com.bobo.galebook.three;

import com.bobo.galebook.three.StackWithMin_3_2.NodeWithMin;
import com.bobo.galebook.two.LList.Node;

public class StackWithMin_3_2 extends Stack<NodeWithMin> {

	public void push(int data) {
		int currentMin = Math.min(min(), data);
		NodeWithMin node = new NodeWithMin(data, currentMin);
		super.push(node);
	}

	public int min() {
		if (this.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return peek().min;
		}
	}

	public static class NodeWithMin extends Node<Integer> {
		int min;

		public NodeWithMin(Integer data, int min) {
			super(data);
			this.min = min;
		}
	}

	public static void main(String[] args) {
		StackWithMin_3_2 stack = new StackWithMin_3_2();
		stack.push(5);
		stack.push(6);
		stack.push(3);
		stack.push(7);
		System.out.println("Current min = " + stack.min());

		stack.pop();
		stack.pop();

		System.out.println("Current min = " + stack.min());
	}
}
