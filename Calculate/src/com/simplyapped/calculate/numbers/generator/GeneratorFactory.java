package com.simplyapped.calculate.numbers.generator;


public class GeneratorFactory
{
	private static IGenerator generator;
	
	public static IGenerator getGenerator()
	{
		if (generator == null)
		{
			generator=new RandomGenerator();
		}
		return generator;
	}
	
	public static void setGenerator(IGenerator generator)
	{
		GeneratorFactory.generator = generator;
	}
}
