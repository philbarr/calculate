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
		SlideTransition slideTransition = new OverlapTransition(true, true);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition OverlapRight()
	{
		SlideTransition slideTransition = new OverlapTransition(false, true);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition OverlapDown()
	{
		SlideTransition slideTransition = new OverlapTransition(true, false);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition OverlapUp()
	{
		SlideTransition slideTransition = new OverlapTransition(false, false);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition UnderlapLeft()
	{
		SlideTransition slideTransition = new UnderlapTransition(true, true);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition UnderlapRight()
	{
		SlideTransition slideTransition = new UnderlapTransition(false, true);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition UnderlapDown()
	{
		SlideTransition slideTransition = new UnderlapTransition(true, false);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
	
	public static Transition UnderlapUp()
	{
		SlideTransition slideTransition = new UnderlapTransition(false, false);
		slideTransition.setDuration(1f);
		slideTransition.setInterpolation(Interpolation.pow5);
		return slideTransition;
	}
}
