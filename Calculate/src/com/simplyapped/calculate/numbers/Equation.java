package com.simplyapped.calculate.numbers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Equation implements EquationElement
{
	private List<EquationElement> elements = new ArrayList<EquationElement>();
	private Generator generator = new Generator();
	private Stack<Integer> numbers = new Stack<Integer>();
	private int total;

	public Equation(int... nums)
	{
		if (nums.length == 0)
		{
			return; // leave total as 0
		}
		if (nums.length == 1)
		{
			total = nums[0];
			return;
		}
		for(int x : nums)
		{
			numbers.push(x);
		}
		construct();
	}
	
	public Equation(List<EquationElement> elements)
	{
		setElements(elements);
	}
	
	private void construct()
	{
		Stack<Integer> tempNumbers = new Stack<Integer>();
		tempNumbers.addAll(numbers);
		getElements().clear();
		Collections.shuffle(tempNumbers);
		
		total = tempNumbers.pop();
		Equation operand1 = new Equation(getTotal());
		getElements().add(operand1);
		Operator oldOperator = null;
		
		while(tempNumbers.size() > 0)
		{
			int nextNumber = tempNumbers.pop();
			Operator newOperator = generator.generateOperator();
			// get a new operator if it's a DIVIDE that would result in a non-integer
			while (newOperator == Operator.DIVIDE && getTotal() % nextNumber != 0)
			{
				newOperator = generator.generateOperator();
			}
			
			if (oldOperator != null && !oldOperator.isEquivalent(newOperator))
			{
				operand1 = new Equation(getElements());
				getElements().clear();
				getElements().add(operand1);
				getElements().add(newOperator);
				Equation operand2 = new Equation(nextNumber);
				getElements().add(operand2);
				total = newOperator.apply(operand1, operand2);
			}
			else
			{
				getElements().add(newOperator);
				Equation operand2 = new Equation(nextNumber);
				getElements().add(operand2);
				total = newOperator.apply(getTotal(), operand2);
			}
			oldOperator = newOperator;
		}
	}
	
	@Override
	public String toString()
	{
		// if only one element then just return that element
		if (getElements().size() == 0)
		{
			return total + "";
		}
		
		// else create and return an equation
		String str = "( ";
		for(EquationElement element : getElements())
		{
			str += element.toString() + " ";
		}
		
		return str + " )";
	}

	public int getOperandCount()
	{
		if (getElements() == null)
		{
			return 0;
		}
		int operandCount = 0;
		for(EquationElement element : getElements())
		{
			if (element instanceof Operator)
			{
				operandCount++;
			}
		}
		return operandCount;
	}

	public int getTotal()
	{
		return total;
	}

	public List<EquationElement> getElements()
	{
		return elements;
	}

	public void setElements(List<EquationElement> elements)
	{
		this.getElements().addAll(elements);
		Operator operator = null;
		for (int i = 0; i < elements.size(); i++)
		{
			if (i == 0)
			{
				total = ((Equation)elements.get(0)).getTotal();
			}
			else if (elements.get(i) instanceof Operator)
			{
				operator = (Operator)elements.get(i);
			}
			else
			{
				total = operator.apply(getTotal(), (Equation)elements.get(i));
			}
		}
	}
}
