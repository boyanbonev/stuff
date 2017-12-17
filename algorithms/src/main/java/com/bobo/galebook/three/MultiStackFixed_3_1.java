package com.bobo.galebook.three;

public class MultiStackFixed_3_1<T> {

	T[] dataArray;
	private int[] stackPointers;
	private int numberOfStacks;
	private int stackSize;

	@SuppressWarnings("unchecked")
	public MultiStackFixed_3_1(int stackSize, int numberOfStacks) {
		this.stackSize = stackSize;
		this.numberOfStacks = numberOfStacks;
		this.dataArray = (T[]) new Object[stackSize * numberOfStacks];
		stackPointers = new int[numberOfStacks];
	}

	public void push(int stackNum, T data) {
		if (stackNum > numberOfStacks) {
			throw new IllegalArgumentException(
					"There no such stack number = " + stackNum + ". Max number of stacks is "
							+ numberOfStacks);
		}

		if (stackPointers[stackNum] == stackSize) {
			throw new RuntimeException("The stack number " + stackNum + " is full.");
		}

		int elementLocation = getElementLocation(stackNum);
		this.dataArray[elementLocation] = data;
		stackPointers[stackNum]++;
	}

	private int getElementLocation(int stackNum) {
		return stackNum * stackSize + stackPointers[stackNum];
	}

	public T pop(int stackNum) {
		if (stackNum > numberOfStacks) {
			throw new IllegalArgumentException("There no such stack number = " + stackNum
					+ ". Max number of stacks is " + numberOfStacks);
		}

		if (stackPointers[stackNum] == 0) {
			return null;
		}
		// Return the pointer 1 step back to the last written element
		stackPointers[stackNum]--;

		int elementLocation = getElementLocation(stackNum);
		T data = dataArray[elementLocation];

		// Although the pointer is moved, we must clear the old data so that
		// there's no dangling reference to it
		dataArray[elementLocation] = null;
		return data;
	}

	// public T peek(int stackNum) {
	//
	// }

	public static void main(String[] args) {
		MultiStackFixed_3_1<Integer> stack = new MultiStackFixed_3_1<>(3, 4);
		stack.push(2, 5);
		stack.push(2, 6);

		stack.push(0, 1);
		stack.push(0, 2);

		stack.push(1, 3);
		stack.push(1, 4);

		Object[] arr = stack.dataArray;
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}

		System.out.println();
		System.out.print(stack.pop(2) + " ");
		System.out.print(stack.pop(2));

		System.out.println();
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + ", ");
		}

	}

}
