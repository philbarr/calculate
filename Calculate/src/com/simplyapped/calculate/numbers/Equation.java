package com.simplyapped.calculate.numbers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import com.badlogic.gdx.Gdx;

public class Equation implements EquationElement
{
	private List<EquationElement> elements = new ArrayList<EquationElement>();
	private Generator generator = new Generator();
	private Stack<Integer> numbers = new Stack<Integer>();
	private int total;
	
	public Equation(){}

	public Equation(int... nums)
	{
		construct(nums);
	}
	
	public Equation(List<EquationElement> elements) throws NonIntegerDivisionException
	{
		setElements(elements);
	}
	

	public void construct(int... nums)
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
		
		Stack<Integer> tempNumbers = new Stack<Integer>();
		tempNumbers.addAll(numbers);
		getElements().clear();
		generator.shuffle(tempNumbers);
		
		total = tempNumbers.pop();
		Equation operand1 = new Equation(getTotal());
		getElements().add(operand1);
		Operator oldOperator = null;
		
		while(tempNumbers.size() > 0)
		{
			int nextNumber = tempNumbers.pop();
			
			Operator newOperator = getGenerator().generateOperator();
			
			if (oldOperator != null && !oldOperator.isEquivalent(newOperator))
			{
				try
				{
					operand1 = new Equation(getElements());
				} catch (NonIntegerDivisionException e1)
				{
					Gdx.app.error(Equation.class.toString(), e1.getMessage());
				}
				Equation operand2 = new Equation(nextNumber);
				try
				{
					while ((newOperator == Operator.DIVIDE && operand1.getTotal() % nextNumber != 0) || 
							newOperator.apply(operand1.getTotal(), operand2) == 0)
					{
						newOperator = getGenerator().generateOperator();
					}
					total = newOperator.apply(operand1, operand2);
				} catch (NonIntegerDivisionException e)
				{
					Gdx.app.error(Equation.class.toString(), e.getMessage());
				}
				getElements().clear();
				getElements().add(operand1);
				getElements().add(newOperator);
				getElements().add(operand2);
			}
			else
			{
				Equation operand2 = new Equation(nextNumber);
				try
				{
					while ((newOperator == Operator.DIVIDE && getTotal() % nextNumber != 0) ||
							newOperator.apply(getTotal(), operand2) == 0)
					{
						newOperator = getGenerator().generateOperator();
					}
					total = newOperator.apply(getTotal(), operand2);
				} catch (NonIntegerDivisionException e)
				{
					Gdx.app.error(Equation.class.toString(), e.getMessage());
					e.printStackTrace();
				}
				
				getElements().add(newOperator);
				getElements().add(operand2);
			}
			oldOperator = newOperator;
		}
	}
	
	/**
	 * Constructs the equation from the simplest elements of the equation and returns 
	 * @return
	 */
	public List<Equation> getEquationConstruction()
	{
		List<Equation> list = createConstruction(new ArrayList<Equation>(), this);;
		Collections.reverse(list);
		return list;
	}
	
	private List<Equation> createConstruction(List<Equation> list, Equation equation)
	{
		list.add(equation);
		// for each Equation in equation
		for (EquationElement element : equation.elements)
		{
			if (element instanceof Equation)
			{
				Equation e = (Equation)element;
				
				if (e.getOperandCount() > 1)
				{
					createConstruction(list, e);
				}
			}
		}
		return list;
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
			if (element instanceof Equation)
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

	public void setElements(List<EquationElement> elements) throws NonIntegerDivisionException
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

	public Generator getGenerator()
	{
		return generator;
	}

	public void setGenerator(Generator generator)
	{
		this.generator = generator;
	}
}
