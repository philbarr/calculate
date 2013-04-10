package com.simplyapped.calculate.numbers;

import java.util.List;

public interface Generator
{

	public Operator generateOperator();

	public void shuffle(List<Integer> tempNumbers);

}