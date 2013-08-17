package com.simplyapped.calculate.state;

public abstract class GameState
{
	private int currentLevel;
	public abstract LevelDetails getLevelDetails(int level);
	
	public LevelInfo getLevelInfo(int level)
	{
		return LevelInfo.getLevel(level);
	}

	public int getCurrentLevel()
	{
		return currentLevel;
	}

	public void setCurrentLevel(int currentLevel)
	{
		this.currentLevel = currentLevel;
	}
	
}