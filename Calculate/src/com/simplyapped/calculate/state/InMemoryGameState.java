package com.simplyapped.calculate.state;

import java.util.HashMap;
import java.util.Map;

public class InMemoryGameState extends GameState
{
	private Map<Integer, LevelDetails> details = new HashMap<Integer, LevelDetails>();
	
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
}
