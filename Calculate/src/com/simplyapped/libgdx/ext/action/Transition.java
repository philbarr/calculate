package com.simplyapped.libgdx.ext.action;

import com.badlogic.gdx.math.Interpolation;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;
import com.simplyapped.libgdx.ext.screen.StagedScreen;


public interface Transition
{
	void setDuration(float duration);
	void setInterpolation(Interpolation interpolation);
	void apply(StagedScreen currentScreen, StagedScreen nextScreen);
	void render(StagedScreen currentScreen, StagedScreen nextScreen);
}
