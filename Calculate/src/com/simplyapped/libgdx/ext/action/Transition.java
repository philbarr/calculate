package com.simplyapped.libgdx.ext.action;

import com.badlogic.gdx.math.Interpolation;
import com.simplyapped.libgdx.ext.DefaultScreen;


public interface Transition
{
	void setDuration(float duration);
	void setInterpolation(Interpolation interpolation);
	void apply(DefaultScreen current, DefaultScreen next);
}
