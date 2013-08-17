package com.simplyapped.calculate.state;

import com.badlogic.gdx.Gdx;

public class LevelInfo
{
	public static final int NUMBER_OF_LEVELS = 10; // alter switch statement when this changes

	static LevelInfo getLevel(int level)
	{
		
		switch (level) // change NUMBER_OF_LEVELS when more cases are added / removed.
		{
			case 1: return new LevelInfo(4, false, 60, 5);
			case 2: return new LevelInfo(4, true, 60, 5);
			case 3: return new LevelInfo(5, false, 60, 5);
			case 4: return new LevelInfo(5, true, 60, 5);
			case 5: return new LevelInfo(6, false, 60, 5);
			case 6: return new LevelInfo(6, true, 60, 7);
			case 7: return new LevelInfo(6, false, 30, 7);
			case 8: return new LevelInfo(6, true, 30, 7);
			case 9: return new LevelInfo(7, false, 60, 7);
			case 10: return new LevelInfo(7, true, 60, 7);
			default:
				Gdx.app.error(LevelInfo.class.toString(), "Returning Level 1. Requested level not available: " + level);
				return new LevelInfo(4, false, 60, 5);
		}
			
	}
	
	private int numberOfCards;
	private boolean useAllCards;
	private int timeLimit;
	private int consecutiveWinsRequired;
	
	public int getConsecutiveWinsRequired()
	{
		return consecutiveWinsRequired;
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

	public LevelInfo(int numberOfCards, boolean useAllCards, int timeLimit, int consectutiveWinsRequired)
	{
		this.numberOfCards = numberOfCards;
		this.useAllCards = useAllCards;
		this.timeLimit = timeLimit;
		this.consecutiveWinsRequired = consectutiveWinsRequired;
	}
	
}
