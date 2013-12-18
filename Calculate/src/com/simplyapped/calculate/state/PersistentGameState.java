package com.simplyapped.calculate.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.simplyapped.calculate.CalculateGame;

public class PersistentGameState extends GameState
{
	private static final String IS_VIEWING_SOLUTION_KEY = "isViewingSolution";
	private static final String KEY_SOLUTIONS = "solutions";
	private Preferences preferences;
	
	public PersistentGameState()
	{
		preferences = Gdx.app.getPreferences("CalculateGame");
	}

	@Override
	public LevelDetails getLevelDetails(int level)
	{
		String pref = preferences.getString(level + "");
		if (pref == null || pref == "")
		{
			LevelDetails detail = new LevelDetails();
			detail.setLocked(true);
			setLevelDetails(level, detail);
			pref = preferences.getString(level + "");
		}
		LevelDetails deserialize = LevelDetails.deserialize(pref);
		return deserialize;
	}

	@Override
	public int getRemainingSolutions()
	{
		return preferences.getInteger(KEY_SOLUTIONS, CalculateGame.STARTING_SOLUTIONS);
	}

	@Override
	public void setLevelDetails(int level, LevelDetails details)
	{
		String serialize = LevelDetails.serialize(details);
		preferences.putString(level + "", serialize);
		preferences.flush();
	}

	@Override
	public void increaseRemainingSolutions(int increase)
	{
		int remainingSolutions = getRemainingSolutions();
		remainingSolutions += increase;
		preferences.putInteger(KEY_SOLUTIONS, remainingSolutions);
		preferences.flush();
	}

	@Override
	public void decreaseSolutions()
	{
		int remainingSolutions = getRemainingSolutions();
		remainingSolutions = remainingSolutions == 0 ? 0 : remainingSolutions - 1;
		preferences.putInteger(KEY_SOLUTIONS, remainingSolutions);
		preferences.flush();
	}

	@Override
	public boolean isViewingSolution() {
		return preferences.getBoolean(IS_VIEWING_SOLUTION_KEY, false);
	}

	@Override
	public void setViewingSolution(boolean isViewing) {
		preferences.putBoolean(IS_VIEWING_SOLUTION_KEY, isViewing);
	}
}
