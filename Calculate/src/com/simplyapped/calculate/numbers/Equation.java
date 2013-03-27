package com.simplyapped.calculate.numbers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Equation
{
	private List<EquationElement> elements = new ArrayList<EquationElement>();
	private int operandCount;
	private Generator generator = new Generator();
	private Stack<Integer> numbers = new Stack<Integer>();
	private int total;

	public Equation(int... nums)
	{
		for(int x : nums)
		{
			numbers.push(x);
		}
		setOperandCount(nums.length);
	}
	
	public void construct()
	{
		Stack<Integer> tempNumbers = new Stack<Integer>();
		tempNumbers.addAll(numbers);
		elements.clear();
		Collections.shuffle(tempNumbers);
		
		total = (tempNumbers.pop());
		elements.add(new Number(getTotal()));
		
		int count = getOperandCount();
		while(count-- > 1)
		{
			Operator operator = generator.generateOperator();
			int operand2 = tempNumbers.pop();
			
			// use a PLUS if it's a DIVIDE that would result in a non-integer
			if (operator == Operator.DIVIDE && getTotal() % operand2 != 0)
			{
				operator = Operator.PLUS;
			}
			total = (operator.apply(getTotal(), operand2));

			elements.add(operator);
			elements.add(new Number(operand2));
		}
	}
	
	@Override
	public String toString()
	{
		String str = "";
		for(EquationElement element : elements)
		{
			str += element.toString() + " ";
		}
		str += "= " + total;
		return str;
	}

	public int getOperandCount()
	{
		return operandCount;
	}

	public void setOperandCount(int operandCount)
	{
		this.operandCount = operandCount;
	}

	public int getTotal()
	{
		return total;
	}
}
