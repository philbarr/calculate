package com.simplyapped.libgdx.ext.action;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.simplyapped.libgdx.ext.DefaultScreen;

public class SlideTransition implements Transition
{
	private boolean leftOrDown;
	private boolean horizontal;
	private float duration;
	private Interpolation interpolation;
	private boolean overlap;

	public SlideTransition(boolean leftOrDown, boolean horizontal)
	{
		this.leftOrDown = leftOrDown;
		this.horizontal = horizontal;
	}

	@Override
	public void apply(DefaultScreen currentScreen, DefaultScreen nextScreen)
	{
		if (currentScreen != null && nextScreen != null)
		{
			nextScreen.show();

			MoveByAction actionOut = createAction(currentScreen, duration);
			MoveByAction actionIn = createAction(nextScreen, duration);

			if (horizontal && leftOrDown) // SlideRight
			{
				if (!isOverlap())
				{
					currentScreen.getStage().getRoot().setPosition(-currentScreen.getStage().getWidth(), 0);
				}
			} 
			else if (horizontal && !leftOrDown) // SlideLeft
			{
				nextScreen.getStage().getRoot().setPosition(-nextScreen.getStage().getWidth(), 0);
			}
			else if (!horizontal && leftOrDown) // SlideDown
			{
				if (!isOverlap())
				{
					currentScreen.getStage().getRoot().setPosition(0, -currentScreen.getStage().getHeight());
				}
			}
			else if (!horizontal && !leftOrDown) // SlideUp
			{
				nextScreen.getStage().getRoot().setPosition(0, -nextScreen.getStage().getHeight());
			}
			
			if (!isOverlap())
			{
				currentScreen.getStage().addAction(actionOut);
			}
			nextScreen.getStage().addAction(actionIn);
		}		
	}

	private MoveByAction createAction(DefaultScreen screen, float duration)
	{
		MoveByAction action = new MoveByAction();
		action.setDuration(duration);
		if (horizontal)
		{
			action.setAmountX(screen.getStage().getWidth());
		}
		else
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

	public boolean isOverlap()
	{
		return overlap;
	}

	public void setOverlap(boolean overlap)
	{
		this.overlap = overlap;
	}

}
