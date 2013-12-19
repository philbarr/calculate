package com.simplyapped.calculate.state;

import java.util.HashMap;
import java.util.Map;

public class InMemoryGameState extends GameState
{
	private boolean isViewingSolution;
	private Map<Integer, LevelDetails> details = new HashMap<Integer, LevelDetails>();
	private int remainingSolutions;
	
	@Override
	public LevelDetails getLevelDetails(int level)
	{
		if (!details.containsKey(level))
		{
			LevelDetails detail = new LevelDetails();
			detail.setLocked(true);
			details.put(level, detail);
		}
		
		return details.get(level);
	}

	@Override
	public int getRemainingSolutions()
	{
		return remainingSolutions;
	}

	@Override
	public void saveLevelDetails(int level, LevelDetails details)
	{
		this.details.put(level, details);
	}

	@Override
	public void increaseRemainingSolutions(int increase)
	{
		remainingSolutions += increase;
	}

	@Override
	public void decreaseSolutions()
	{
		remainingSolutions = remainingSolutions == 0 ? 0 : remainingSolutions - 1;
	}

	@Override
	public boolean isViewingSolution() {
		return isViewingSolution;
	}

	@Override
	public void setViewingSolution(boolean isViewing) {
		isViewingSolution=isViewing;
	}
}
