package com.simplyapped.libgdx.ext.action;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.simplyapped.libgdx.ext.screen.StagedScreen;


public class UnderlapTransition extends SlideTransition
{

	public UnderlapTransition(boolean leftOrDown, boolean horizontal)
	{
		super(leftOrDown, horizontal);
	}
	
	@Override
	public void apply(StagedScreen currentScreen, StagedScreen nextScreen)
	{
		if (currentScreen != null && nextScreen != null)
		{
			nextScreen.show();

			MoveByAction actionOut = createAction(currentScreen, duration);

			if (horizontal && leftOrDown) // SlideLeft
			{
				currentScreen.getStage().getRoot().setPosition(-currentScreen.getStage().getWidth(), 0);
			}
			else if (!horizontal && leftOrDown) // SlideDown
			{
				currentScreen.getStage().getRoot().setPosition(0, -currentScreen.getStage().getHeight());
			}
			
			currentScreen.getStage().addAction(actionOut);
		}	
	}
	
	@Override
	public void render(StagedScreen current, StagedScreen next)
	{
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		next.getStage().act(Gdx.graphics.getDeltaTime());
		next.getStage().draw();
		current.getStage().getRoot().act(Gdx.graphics.getDeltaTime());
		current.getStage().draw();
	}
}
