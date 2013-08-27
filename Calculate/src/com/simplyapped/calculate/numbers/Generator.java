package com.simplyapped.calculate.numbers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Generator
{
//	private final int[] cards={1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100};
	
	private final Operator[] OPERATORS = new Operator[]{Operator.PLUS, Operator.MINUS, Operator.DIVIDE, Operator.MULTIPLY};
	
	/* (non-Javadoc)
	 * @see com.simplyapped.calculate.numbers.Generator#generateOperator()
	 */
	
	public Operator generateOperator()
	{
		Operator operator = OPERATORS[new Random().nextInt(OPERATORS.length)];
		return operator;
	}

	
	public void shuffle(List<Integer> numbers)
	{
		Collections.shuffle(numbers);
	}
	
	public Stack<Integer> shuffledBigNumbers()
	{
		Stack<Integer> stack = new Stack<Integer>();
		stack.addAll(Arrays.asList(25,50,75,100));
		Collections.shuffle(stack);
		return stack;
	}
	
	public Stack<Integer> shuffledSmallNumbers()
	{
		Stack<Integer> stack = new Stack<Integer>();
		stack.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,0));
		Collections.shuffle(stack);
		return stack;
	}
}
