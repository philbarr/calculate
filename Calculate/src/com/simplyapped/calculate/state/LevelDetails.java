package com.simplyapped.calculate.state;

public class LevelDetails
{
	private boolean isLocked;
	private int attempts;
	private int completed;
	private int consecutive;
	
	public boolean isLocked()
	{
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
