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
	
	@Test(expected=NumberFormatException.class)
	public void OperatorDivideExceptionTest()
	{
		Operator.DIVIDE.apply(3,2);
	}
	
	@Test
	public void OperatorDivideTest()
	{
		int res = Operator.DIVIDE.apply(6,3);
		Assert.assertSame(2, res);
	}
}
