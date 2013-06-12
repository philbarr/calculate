package com.simplyapped.calculate.state;

public class InMemoryGameState implements GameState
{
	private int selectedLevel;
	private int maximumAchievedLevel;
	

	/* (non-Javadoc)
	 * @see com.simplyapped.calculate.state.GameState#getSelectedLevel()
	 */
	@Override
	public int getSelectedLevel()
	{
		return selectedLevel;
	}

	/* (non-Javadoc)
	 * @see com.simplyapped.calculate.state.GameState#setSelectedLevel(int)
	 */
	@Override
	public void setSelectedLevel(int selectedLevel)
	{
		this.selectedLevel = selectedLevel;
	}

	/* (non-Javadoc)
	 * @see com.simplyapped.calculate.state.GameState#getMaximumAchievedLevel()
	 */
	@Override
	public int getMaximumAchievedLevel()
	{
		return maximumAchievedLevel;
	}

	/* (non-Javadoc)
	 * @see com.simplyapped.calculate.state.GameState#setMaximumAchievedLevel(int)
	 */
	@Override
	public void setMaximumAchievedLevel(int maximumAchievedLevel)
	{
		this.maximumAchievedLevel = maximumAchievedLevel;
	}	
}
