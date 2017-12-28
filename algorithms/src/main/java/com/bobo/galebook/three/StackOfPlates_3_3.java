package com.bobo.galebook.three;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;

public class StackOfPlates_3_3<T> {

	private List<Stack<T>> stacks = new ArrayList<>();

	private int singleStackCapacity;

	public StackOfPlates_3_3(int singleStackCapacity) {
		this.singleStackCapacity = singleStackCapacity;
	}

	public void push(T data) {
		Stack<T> currentStack = getCurrentStack();
		if (currentStack == null || currentStack.size() == singleStackCapacity) {
			currentStack = new Stack<>();
			stacks.add(currentStack);
		}

		currentStack.push(data);
	}

	public T pop() {
		if (stacks.isEmpty()) {
			return null;
		}

		Stack<T> currentStack = getCurrentStack();
		T data = currentStack.pop();
		if (currentStack.isEmpty()) {
			stacks.remove(stacks.size() - 1);
		}

		return data;
	}

	public T popAt(int stackIndex) {
		if (stackIndex >= stacks.size() || stackIndex < 0) {
			throw new IllegalArgumentException(
					"Invalid stack index = " + stackIndex + ". It must be between 0 and "
							+ (stacks.size() - 1));
		}

		if (stacks.isEmpty()) {
			return null;
		}

		Stack<T> currentStack = stacks.get(stackIndex);
		T data = currentStack.pop();
		if (currentStack.isEmpty()) {
			stacks.remove(stackIndex);
			for (int i = stackIndex; i < stacks.size() - 1; i++) {
				// shift left all the stacks without touching the elements in
				// them
				stacks.add(i, stacks.get(i + 1));
			}
		} else if (stackIndex < stacks.size()) {
			// this is not the last stack and the
			shiftAllToTheLeftFromIndex(stackIndex);
		}

		return data;
	}

	/**
	 * Shift all the elements of the stacks that are to the right of the removed
	 * element one position to the left
	 *
	 * @param stackIndex
	 */
	private void shiftAllToTheLeftFromIndex(int stackIndex) {
		if (true) {
			throw new NotImplementedException("Too much work to implement it");
		}
		for (int i = stackIndex; i < stacks.size(); i++) {
			Stack<T> currentStack = stacks.get(i);
			// currentStack.

		}
	}

	public T peek() {
		if (stacks.isEmpty()) {
			return null;
		}

		Stack<T> currentStack = getCurrentStack();
		return currentStack.peek();
	}

	public void print() {
		for (Stack<T> stack : stacks) {
			stack.print();
		}
	}

	private Stack<T> getCurrentStack() {
		if (stacks.isEmpty()) {
			return null;
		}

		return stacks.get(stacks.size() - 1);
	}

	public static void main(String[] args) {
		StackOfPlates_3_3<Integer> stack = new StackOfPlates_3_3<>(1);
		stack.push(1);
		// stack.push(1);

		stack.push(2);
		// stack.push(2);

		stack.push(3);
		// stack.push(3);

		stack.print();

		System.out.println("=================================");
		System.out.println("Peek: " + stack.peek());
		System.out.println("=================================");

		int poppedEl = stack.popAt(0);
		System.out.println("Popped element: " + poppedEl);

		stack.print();
	}
}
