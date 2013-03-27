package com.simplyapped.calculate.numbers;

public class Number implements EquationElement
{
	private int number;
	
	public Number(int number)
	{
		this.setNumber(number);
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	@Override
	public String toString()
	{
		return number + "";
	}
	
	
}
