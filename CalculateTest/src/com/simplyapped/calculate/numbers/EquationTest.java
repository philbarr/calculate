package com.simplyapped.calculate.numbers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.simplyapped.calculate.numbers.generator.FakeGenerator;
import com.simplyapped.calculate.numbers.generator.GeneratorFactory;
import com.simplyapped.calculate.numbers.generator.RandomGenerator;

public class EquationTest
{
	@Before
	public void setup()
	{
		GeneratorFactory.setGenerator(new RandomGenerator());
	}
	
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
		FakeGenerator stub = new FakeGenerator();
		stub.pushOperator(Operator.DIVIDE);
		stub.pushOperator(Operator.PLUS);
		GeneratorFactory.setGenerator(stub);
		Equation e = new Equation();
		e.construct(new int[] { 6, 5 });
		Assert.assertEquals(11, e.getTotal());
	}
	
	@Test
	public void IsSimpleTrue() throws NonIntegerDivisionException
	{
		List<EquationElement> elements = new ArrayList<EquationElement>();
		elements.add(new Equation(5));
		elements.add(Operator.PLUS);
		elements.add(new Equation(5));
		elements.add(Operator.PLUS);
		elements.add(new Equation(5));
		elements.add(Operator.PLUS);
		elements.add(new Equation(5));
		elements.add(Operator.PLUS);
		elements.add(new Equation(5));
		Equation e = new Equation(elements );
		Assert.assertTrue(e.isSimple());
	}
	
	@Test
	public void IsSimpleFalse() throws NonIntegerDivisionException
	{
		List<EquationElement> elements = new ArrayList<EquationElement>();
		elements.add(new Equation(5));
		elements.add(Operator.PLUS);
		elements.add(new Equation(5));
		elements.add(Operator.MULTIPLY);
		elements.add(new Equation(5));
		elements.add(Operator.PLUS);
		elements.add(new Equation(5));
		elements.add(Operator.MINUS);
		elements.add(new Equation(5));
		Equation e = new Equation(elements );
		Assert.assertFalse(e.isSimple());
	}
	
	@Test
	public void NewOperatorOnZeroResult()
	{
		// expect Equation to skip MINUS and go straight to PLUS
		FakeGenerator generator = new FakeGenerator();
		generator.pushOperator(Operator.MINUS);
		generator.pushOperator(Operator.PLUS);
		GeneratorFactory.setGenerator(generator);
		Equation e = new Equation();
		e.construct(new int[] { 6, 6 });
		Assert.assertEquals(12, e.getTotal());
	}

	@Test
	public void EquationRun()
	{
		GeneratorFactory.setGenerator(new RandomGenerator());
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
