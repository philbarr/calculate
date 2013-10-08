package com.simplyapped.libgdx.ext.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Interpolation;
import com.simplyapped.libgdx.ext.screen.StagedScreen;


public abstract class Transition
{
	protected float duration;
	protected Interpolation interpolation;

	public abstract void apply(StagedScreen currentScreen, StagedScreen nextScreen);
	
	public void setDuration(float duration)
	{
		this.duration = duration;
	}

	public void setInterpolation(Interpolation interpolation)
	{
		this.interpolation = interpolation;
	}

	public void render(StagedScreen current, StagedScreen next)
	{
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		current.getStage().getRoot().act(Gdx.graphics.getDeltaTime());
		current.getStage().draw();
		next.getStage().act(Gdx.graphics.getDeltaTime());
		next.getStage().draw();
	}
}
