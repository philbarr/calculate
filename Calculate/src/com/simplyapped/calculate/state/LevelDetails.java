package com.simplyapped.calculate.state;

import com.badlogic.gdx.utils.Json;
import com.simplyapped.calculate.CalculateGame;

public class LevelDetails
{
	public static String serialize(LevelDetails details)
	{
		String json = new Json().toJson(details);
		return json;
	}
	
	public static LevelDetails deserialize(String json)
	{
		LevelDetails fromJson = new Json().fromJson(LevelDetails.class, json);
		return fromJson;
	}
	
	private boolean isLocked;
	private int attempts;
	private int completed;
	private int consecutive;
	
	public boolean isLocked()
	{
		if (CalculateGame.DEBUG)
			return false;
		return isLocked;
	}
	public void setLocked(boolean isLocked)
	{
		this.isLocked = isLocked;
	}
	public int getAttempts()
	{
		return attempts;
	}
	public int getCompleted()
	{
		return completed;
	}
	
	public void increaseCompleted()
	{
		completed++;
	}
	public void increaseAttempts()
	{
		attempts++;
	}
	public int getConsecutive()
	{
		return consecutive;
	}
	public void resetConsecutive()
	{
		consecutive = 0;
	}
	public void increaseConsecutive()
	{
		consecutive++;
	}
}
