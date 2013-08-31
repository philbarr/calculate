package com.simplyapped.calculate.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.numbers.Generator;

public abstract class GameState
{
	private Generator generator = new Generator();
	private int currentLevel;
	private List<Integer> bigCards = new ArrayList<Integer>();
	private List<Integer> smallCards = new ArrayList<Integer>();
	private Stack<Integer> shuffledBigNumbers;
	private Stack<Integer> shuffledSmallNumbers;
	private Equation equation;
	
	public abstract LevelDetails getLevelDetails(int level);
	public GameState()
	{
		shuffledBigNumbers = generator.shuffledBigNumbers();
		shuffledSmallNumbers = generator.shuffledSmallNumbers();		
	}
	public LevelInfo getLevelInfo()
	{
		return LevelInfo.getLevel(getCurrentLevel());
	}

	public int getCurrentLevel()
	{
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel)
	{
		this.currentLevel = currentLevel;
	}
	
	public int selectBigNumber()
	{
		int big = shuffledBigNumbers.pop();
		bigCards.add(big);
		return big;
	}
	
	public int selectSmallNumber()
	{
		int small = shuffledSmallNumbers.pop();
		smallCards.add(small);
		return small;
	}
	
	public int cardsLeftForUserSelect()
	{
		return getLevelInfo().getNumberOfCards() - bigCards.size() - smallCards.size();
	}
	public void setCurrentEquation(Equation eq)
	{
		this.equation = eq;
	}
	public Equation getCurrentEquation()
	{
		return equation;
	}
}