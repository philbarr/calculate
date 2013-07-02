package com.simplyapped.calculate.numbers;

public enum Operator implements EquationElement
{
	PLUS, MINUS, MULTIPLY, DIVIDE;
	
	public boolean isEquivalent(Operator operator)
	{
		switch (this)
		{
		case PLUS:
		case MINUS:
			return operator == PLUS || operator == MINUS;
		case DIVIDE:
		case MULTIPLY:
			return operator == DIVIDE || operator == MULTIPLY;
		}
		return false;
	}

	public int apply(int operand1, int operand2)
	{
		// yes we could do this with a strategy pattern but really what's the point?
		switch (this)
		{
		case PLUS:
			return operand1 + operand2;
		case MINUS:
			return operand1 - operand2;
		case DIVIDE:
			if (operand1 % operand2 == 0)
			{
				return operand1 / operand2;				
			}
			throw new NumberFormatException("attempting to divide gives a non-integer result: " + operand1 + " / " + operand2);
		case MULTIPLY:
			return operand1 * operand2;
		}
		
		return 0;
	}
	
	public String toString()
	{
		switch (this)
		{
		case PLUS:
			return "+";
		case MINUS:
			return "-";
		case DIVIDE:
			return "%";
		case MULTIPLY:
			return "X";
		}
		return "";
	}

	public int apply(int operand1, Equation operand2)
	{
		return apply(operand1, operand2.getTotal());
	}

	public int apply(Equation operand1, Equation operand2)
	{
		return apply(operand1.getTotal(), operand2.getTotal());
	}
}
