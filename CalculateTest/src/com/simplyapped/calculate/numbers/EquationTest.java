package com.simplyapped.calculate.numbers;

import org.junit.Assert;
import org.junit.Test;

public class EquationTest
{
	@Test
	public void EquationConstructorTest()
	{
		// check the constructor doesn't mess with the original array.
		int[] nums = new int[] { 75, 100, 25, 6, 5, 4 };
		int[] assertNums = nums.clone();
		new Equation(nums);
		Assert.assertArrayEquals(nums, assertNums);
	}

	@Test
	public void NewOperatorDivideToNonInteger()
	{
		// expect Equation to skip DIVIDE and go straight to PLUS
		GeneratorStub stub = new GeneratorStub(new Operator[]{Operator.DIVIDE, Operator.PLUS});
		Equation e = new Equation();
//		e.setGenerator(stub)
		e.construct(new int[] { 6, 5 });
		Assert.assertEquals(11, e.getTotal());
	}
	
	@Test
	public void NewOperatorOnZeroResult()
	{
		// expect Equation to skip MINUS and go straight to PLUS
		GeneratorStub stub = new GeneratorStub(new Operator[]{Operator.MINUS, Operator.PLUS});
		Equation e = new Equation();
//		e.setGenerator(stub);
		e.construct(new int[] { 6, 6 });
		Assert.assertEquals(12, e.getTotal());
	}

	@Test
	public void EquationRun()
	{
//		for (int i = 0; i < 10000; i++)
		{
			Equation e = new Equation(100, 75, 25, 4, 6, 8);
			while (e.getTotal() < 100 || e.getTotal() > 999)
			{
				e = new Equation(100, 75, 25, 4, 6, 8);
			}
			System.out.println(e);
			System.out.println(e.getTotal());
			System.out.println();
			
			for(Equation ee : e.getEquationConstruction())
			{
				print(ee);
				System.out.println(" = " + ee.getTotal());
			}
		}
	}

	private void print(Equation equation)
	{
		for (EquationElement e : equation.getElements())
		{
			if (e instanceof Operator)
			{
				System.out.print(e);
			}
			else
			{
				Equation eq = (Equation)e;
				if (eq.getOperandCount() > 1)
				{
					System.out.print("|" + eq.getTotal()+"|");
				}
				else
				{
					System.out.print(eq.getTotal());
				}
			}
			System.out.print(" ");
		}
	}

	@Test
	public void ToStringTest()
	{
		Equation e = new Equation(5);
		Assert.assertEquals("5", e.toString());
	}
}
