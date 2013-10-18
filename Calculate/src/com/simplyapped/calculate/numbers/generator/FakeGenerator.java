package com.simplyapped.calculate.numbers.generator;

import java.util.List;
import java.util.Stack;

import com.simplyapped.calculate.numbers.Operator;

public class FakeGenerator implements IGenerator
{

	private Stack<Operator> operators;
	private Stack<Integer> numbers;

	public FakeGenerator()
	{
		operators = new Stack<Operator>();
		numbers = new Stack<Integer>();
	}
	
	public void pushOperator(Operator op)
	{
		operators.push(op);
	}
	
	public void pushNumber(int number)
	{
		numbers.push(number);
	}
	
	@Override
	public Operator generateOperator()
	{
		if (operators.size() == 0)
		{
			return Operator.MULTIPLY;
		}
		else
		{
			final Operator pop = operators.remove(0);
			return pop;
		}
	}

	@Override
	public void shuffle(List<Integer> numbers)
	{
	}

	@Override
	public Stack<Integer> shuffledBigNumbers()
	{
		return numbers;
	}

	@Override
	public Stack<Integer> shuffledSmallNumbers()
	{
		return numbers;
	}

}
