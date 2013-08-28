package com.simplyapped.calculate.numbers;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GeneratorStub// implements Generator
{
	
	private Iterator<Operator> operatorIterator;

	public GeneratorStub(Operator[] operators)
	{
		operatorIterator = Arrays.asList(operators).iterator();
	}

//	@Override
	public Operator generateOperator()
	{
		return operatorIterator.next();
	}

//	@Override
	public void shuffle(List<Integer> tempNumbers)
	{
		// do nothing
	}

}
