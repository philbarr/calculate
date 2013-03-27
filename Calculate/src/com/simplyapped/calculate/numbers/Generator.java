package com.simplyapped.calculate.numbers;

import java.util.Random;

public class Generator
{
//	private final int[] cards={1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10, 25, 50, 75, 100};
	
	private final Operator[] OPERATORS = new Operator[]{Operator.PLUS, Operator.MINUS, Operator.DIVIDE, Operator.MULTIPLY};
	
	public Operator generateOperator()
	{
		Random random = new Random();
		return OPERATORS[random.nextInt(OPERATORS.length)];
	}
}
