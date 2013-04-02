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
	public void EquationSingle()
	{
		Equation e = new Equation(new int[] { 5 });
		System.out.println(e);
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
		}
	}

	@Test
	public void ToStringTest()
	{
		Equation e = new Equation(5);
		Assert.assertEquals("5", e.toString());
	}
}
