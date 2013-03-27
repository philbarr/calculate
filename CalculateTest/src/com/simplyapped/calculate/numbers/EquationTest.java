package com.simplyapped.calculate.numbers;

import org.junit.Assert;
import org.junit.Test;

public class EquationTest
{
	@Test
	public void EquationConstructorTest()
	{
		// check the constructor doesn't mess with the original array.
		int[] nums = new int[]{75,100,25,6,5,4};
		int[] assertNums = nums.clone();
		new Equation(nums);
		Assert.assertArrayEquals(nums, assertNums);
	}
	
	@Test
	public void EquationSingle()
	{
		Equation e = new Equation(new int[]{5});
		e.construct();
		System.out.println(e);
	}
	
	@Test
	public void EquationRun()
	{
		Equation e = new Equation(new int[]{100,75,25,4,6,8});
		while(e.getTotal() < 100 || e.getTotal() > 999)
		{
			e.construct();
		}
		System.out.println(e);
	}
}
