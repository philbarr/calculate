package com.simplyapped.calculate.screen.tween;

import aurelienribon.tweenengine.TweenAccessor;

import com.badlogic.gdx.math.Vector2;

public class Vector2TweenAccessor implements TweenAccessor<Vector2>
{
	@Override
	public int getValues(Vector2 target, int tweenType, float[] returnValues)
	{
		returnValues[0] = target.x;
		returnValues[1] = target.y;
		return 2;
	}

	@Override
	public void setValues(Vector2 target, int tweenType, float[] newValues)
	{
		target.x = newValues[0];
		target.y = newValues[1];
	}

}
