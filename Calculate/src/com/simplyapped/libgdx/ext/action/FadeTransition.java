package com.simplyapped.libgdx.ext.action;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.simplyapped.libgdx.ext.screen.StagedScreen;

public class FadeTransition extends Transition
{

	@Override
	public void apply(StagedScreen currentScreen, StagedScreen nextScreen)
	{
		if (currentScreen != null && nextScreen != null)
		{
			nextScreen.show();
			nextScreen.getStage().getRoot().getColor().a = 0;
			currentScreen.getStage().getRoot().addAction(Actions.fadeOut(duration));
			nextScreen.getStage().getRoot().addAction(Actions.fadeIn(duration));
		}
	}

}
