package com.simplyapped.calculate.state;

public class GameState
{
	private static GameState state;
	private int selectedLevel;
	private int maximumAchievedLevel;
	
	private GameState(){}
	
	public synchronized static GameState Instance()
	{
		if (state == null)
		{
			state = new GameState();
		}
		return state;
	}

	public int getSelectedLevel()
	{
		return selectedLevel;
	}

	public void setSelectedLevel(int selectedLevel)
	{
		this.selectedLevel = selectedLevel;
	}

	public int getMaximumAchievedLevel()
	{
		return maximumAchievedLevel;
	}

	public void setMaximumAchievedLevel(int maximumAchievedLevel)
	{
		this.maximumAchievedLevel = maximumAchievedLevel;
	}
	
	
}
