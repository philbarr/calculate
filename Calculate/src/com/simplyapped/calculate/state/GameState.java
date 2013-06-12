package com.simplyapped.calculate.state;

public interface GameState
{

	public abstract int getSelectedLevel();

	public abstract void setSelectedLevel(int selectedLevel);

	public abstract int getMaximumAchievedLevel();

	public abstract void setMaximumAchievedLevel(int maximumAchievedLevel);

}