package com.simplyapped.calculate.numbers;

import junit.framework.Assert;

import org.junit.Test;

public class OperatorTest
{
	@Test
	public void OperatorEquivalency()
	{
		Assert.assertTrue(Operator.PLUS.isEquivalent(Operator.MINUS));
		Assert.assertTrue(Operator.MULTIPLY.isEquivalent(Operator.DIVIDE));
		Assert.assertFalse(Operator.PLUS.isEquivalent(Operator.DIVIDE));
		Assert.assertFalse(Operator.MINUS.isEquivalent(Operator.MULTIPLY));
	}
	
	@Test(expected=NonIntegerDivisionException.class)
	public void OperatorDivideExceptionTest() throws NonIntegerDivisionException
	{
		Operator.DIVIDE.apply(3,2);
	}
	
	@Test
	public void OperatorDivideTest() throws NonIntegerDivisionException
	{
		int res = Operator.DIVIDE.apply(6,3);
		Assert.assertSame(2, res);
	}
}
