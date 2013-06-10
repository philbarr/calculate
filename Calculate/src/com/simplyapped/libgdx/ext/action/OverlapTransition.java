package com.simplyapped.libgdx.ext.action;

import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.simplyapped.libgdx.ext.screen.StagedScreen;


public class OverlapTransition extends SlideTransition
{

	public OverlapTransition(boolean leftOrDown, boolean horizontal)
	{
		super(leftOrDown, horizontal);
	}
	
	@Override
	public void apply(StagedScreen currentScreen, StagedScreen nextScreen)
	{
		if (currentScreen != null && nextScreen != null)
		{
			nextScreen.show();

			MoveByAction actionIn = createAction(nextScreen, duration);

			if (horizontal && !leftOrDown) // SlideRight
			{
				nextScreen.getStage().getRoot().setPosition(-nextScreen.getStage().getWidth(), 0);
			}
			else if (!horizontal && !leftOrDown) // SlideUp
			{
				nextScreen.getStage().getRoot().setPosition(0, -nextScreen.getStage().getHeight());
			}
			
			nextScreen.getStage().addAction(actionIn);
		}	
	}
}
