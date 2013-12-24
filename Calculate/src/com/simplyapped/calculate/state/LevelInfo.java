package com.simplyapped.calculate.state;

import com.badlogic.gdx.Gdx;

public class LevelInfo
{
	public static final int NUMBER_OF_LEVELS = 10; // alter switch statement when this changes

	public static LevelInfo getLevel(int level)
	{
		
		switch (level) // change NUMBER_OF_LEVELS when more cases are added / removed.
		{
			case 1: return new LevelInfo(4, false, 60, 10, 1, 100);
			case 2: return new LevelInfo(4, false, 30, 20, 1, 300);
			case 3: return new LevelInfo(5, false, 60, 30, 1, 1000);
			case 4: return new LevelInfo(5, false, 60, 40, 1, 2000);
			case 5: return new LevelInfo(6, false, 60, 50, 1, 200);
			case 6: return new LevelInfo(6, true, 60, 50, 1, 200);
			case 7: return new LevelInfo(6, false, 30, 50, 1, 500);
			case 8: return new LevelInfo(6, true, 30, 50, 1, 1000);
			case 9: return new LevelInfo(7, false, 60, 100, 1, 2000);
			case 10: return new LevelInfo(8, true, 30, 100, 1, 2000);
			default:
				Gdx.app.error(LevelInfo.class.toString(), "Returning Level 1. Requested level not available: " + level);
				return new LevelInfo(4, false, 60, 5, 1, 100);
		}
			
	}
	
	private int numberOfCards;
	private boolean useAllCards;
	private int timeLimit;
	private int completedRequired;
	private int min;
	private int max;
	
	public int getCompletedRequired()
	{
		return completedRequired;
	}

	public int getNumberOfCards()
	{
		return numberOfCards;
	}

	public boolean isUseAllCards()
	{
		return useAllCards;
	}

	public int getTimeLimit()
	{
		return timeLimit;
	}

	public LevelInfo(int numberOfCards, boolean useAllCards, int timeLimit, int completedRequired, int min, int max)
	{
		this.numberOfCards = numberOfCards;
		this.useAllCards = useAllCards;
		this.timeLimit = timeLimit;
		this.completedRequired = completedRequired;
		this.min = min;
		this.max = max;
	}

	public int getMinRange() {
		return min;
	}

	public int getMaxRange() {
		return max;
	}
	
}
