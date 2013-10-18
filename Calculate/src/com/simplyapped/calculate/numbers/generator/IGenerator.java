package com.simplyapped.calculate.numbers.generator;

import java.util.List;
import java.util.Stack;

import com.simplyapped.calculate.numbers.Operator;

public interface IGenerator
{

	public abstract Operator generateOperator();

	public abstract void shuffle(List<Integer> numbers);

	public abstract Stack<Integer> shuffledBigNumbers();

	public abstract Stack<Integer> shuffledSmallNumbers();

}