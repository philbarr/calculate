package com.simplyapped.libgdx.ext.action;

import com.badlogic.gdx.math.Interpolation;

public class TransitionFixtures
{
	public static Transition SlideLeft()
	{
		SlideTransition slideTransition = new SlideTransition(true, true);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition SlideRight()
	{
		SlideTransition slideTransition = new SlideTransition(false, true);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition SlideDown()
	{
		SlideTransition slideTransition = new SlideTransition(true, false);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition SlideUp()
	{
		SlideTransition slideTransition = new SlideTransition(false, false);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition OverlapLeft()
	{
		SlideTransition slideTransition = new SlideTransition(true, true);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		slideTransition.setOverlap(true);
		return slideTransition;
	}
	
	public static Transition OverlapRight()
	{
		SlideTransition slideTransition = new SlideTransition(false, true);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		slideTransition.setOverlap(true);
		return slideTransition;
	}
	
	public static Transition OverlapDown()
	{
		SlideTransition slideTransition = new SlideTransition(true, false);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		slideTransition.setOverlap(true);
		return slideTransition;
	}
	
	public static Transition OverlapUp()
	{
		SlideTransition slideTransition = new SlideTransition(false, false);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		slideTransition.setOverlap(true);
		return slideTransition;
	}
}
