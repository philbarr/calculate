package com.simplyapped.libgdx.ext.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.simplyapped.libgdx.ext.screen.StagedScreen;

public class SlideTransition implements Transition
{
	protected boolean leftOrDown;
	protected boolean horizontal;
	protected float duration;
	private Interpolation interpolation;

	public SlideTransition(boolean leftOrDown, boolean horizontal)
	{
		this.leftOrDown = leftOrDown;
		this.horizontal = horizontal;
	}

	@Override
	public void apply(StagedScreen currentScreen, StagedScreen nextScreen)
	{
		if (currentScreen != null && nextScreen != null)
		{
			nextScreen.show();

			MoveByAction actionOut = createAction(currentScreen, duration);
			MoveByAction actionIn = createAction(nextScreen, duration);

			if (horizontal && leftOrDown) // SlideRight
			{
				currentScreen.getStage().getRoot().setPosition(-currentScreen.getStage().getWidth(), 0);
			} else if (horizontal && !leftOrDown) // SlideLeft
			{
				nextScreen.getStage().getRoot().setPosition(-nextScreen.getStage().getWidth(), 0);
			} else if (!horizontal && leftOrDown) // SlideDown
			{
				currentScreen.getStage().getRoot().setPosition(0, -currentScreen.getStage().getHeight());
			} else if (!horizontal && !leftOrDown) // SlideUp
			{
				nextScreen.getStage().getRoot().setPosition(0, -nextScreen.getStage().getHeight());
			}

			currentScreen.getStage().addAction(actionOut);
			nextScreen.getStage().addAction(actionIn);
		}
	}

	protected MoveByAction createAction(StagedScreen screen, float duration)
	{
		MoveByAction action = new MoveByAction();
		action.setDuration(duration);
		if (horizontal)
		{
			action.setAmountX(screen.getStage().getWidth());
		} else
		{
			action.setAmountY(screen.getStage().getHeight());
		}
		action.setInterpolation(interpolation);
		action.setReverse(leftOrDown);
		return action;
	}

	@Override
	public void setDuration(float duration)
	{
		this.duration = duration;
	}

	@Override
	public void setInterpolation(Interpolation interpolation)
	{
		this.interpolation = interpolation;
	}

	@Override
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
