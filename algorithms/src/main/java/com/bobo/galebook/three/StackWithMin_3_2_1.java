package com.bobo.galebook.three;

public class StackWithMin_3_2_1 extends Stack<Integer> {

	private Stack<Integer> mins = new Stack<>();

	@Override
	public void push(Integer data) {
		if (data <= min()) {
			mins.push(data);
		}
		super.push(data);
	}

	@Override
	public Integer pop() {
		Integer result = super.pop();
		if (result == min()) {
			mins.pop();
		}

		return result;
	}

	public int min() {
		if (mins.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return mins.peek();
		}
	}

	public static void main(String[] args) {
		StackWithMin_3_2_1 stack = new StackWithMin_3_2_1();
		stack.push(5);
		stack.push(6);
		stack.push(3);
		stack.push(7);
		stack.print();
		System.out.println("Current min = " + stack.min());

		stack.pop();
		stack.pop();

		stack.print();
		System.out.println("Current min = " + stack.min());
	}

}
