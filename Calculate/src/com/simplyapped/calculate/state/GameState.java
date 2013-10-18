package com.simplyapped.calculate.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.numbers.generator.GeneratorFactory;
import com.simplyapped.calculate.numbers.generator.IGenerator;
import com.simplyapped.calculate.numbers.generator.RandomGenerator;

public abstract class GameState
{
	private IGenerator generator = GeneratorFactory.getGenerator();
	private int currentLevel;
	private List<Integer> bigCards = new ArrayList<Integer>();
	private List<Integer> smallCards = new ArrayList<Integer>();
	private Stack<Integer> shuffledBigNumbers;
	private Stack<Integer> shuffledSmallNumbers;
	private Equation equation;
	
	public abstract LevelDetails getLevelDetails(int level);
	public abstract void setLevelDetails(int level, LevelDetails details);

	public abstract void increaseRemainingSolutions(int increase);
	public abstract int getRemainingSolutions();
	public abstract void decreaseSolutions();
	
	public GameState()
	{
		resetCurrentGameInfo();
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
		getBigCards().add(big);
		addAttempt();
		return big;
	}
	
	public int selectSmallNumber()
	{
		int small = shuffledSmallNumbers.pop();
		getSmallCards().add(small);
		addAttempt();
		return small;
	}
	
	private void addAttempt()
	{
		// the user has made an attempt to play the game the first time they select a card
		if (getLevelInfo().getNumberOfCards() - cardsLeftForUserSelect() == 1)
		{
			getLevelDetails(getCurrentLevel()).increaseAttempts();
		}
	}
	public int cardsLeftForUserSelect()
	{
		return getLevelInfo().getNumberOfCards() - getBigCards().size() - getSmallCards().size();
	}
	public void setCurrentEquation(Equation eq)
	{
		this.equation = eq;
	}
	public Equation getCurrentEquation()
	{
		return equation;
	}
	public List<Integer> getBigCards()
	{
		return bigCards;
	}
	public List<Integer> getSmallCards()
	{
		return smallCards;
	}
	public void resetCurrentGameInfo()
	{
		shuffledBigNumbers = generator.shuffledBigNumbers();
		shuffledSmallNumbers = generator.shuffledSmallNumbers();		
		smallCards.clear();
		bigCards.clear();
		this.equation = null;
	}
}