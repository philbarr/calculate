package com.simplyapped.calculate.screen;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public abstract class DefaultGame implements ApplicationListener
{
	protected Map<String, DefaultScreen> screens = new HashMap<String, DefaultScreen>();
	private DefaultScreen currentScreen;
	private DefaultScreen nextScreen;

	@Override
	public void render()
	{
		// if transitioning then do the render
		if (nextScreen != null)
		{
			if (nextScreen.getStage().getRoot().getActions().size > 0)
			{
			    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
			    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			    currentScreen.getStage().getRoot().act(Gdx.graphics.getDeltaTime());
			    currentScreen.getStage().draw();
			    nextScreen.getStage().act(Gdx.graphics.getDeltaTime());
			    nextScreen.getStage().draw();
			}
			else
			{
				setScreen(nextScreen, false);
				nextScreen = null;
			}
		}
		else if (currentScreen != null) // else just render the current screen as normal
		{
			currentScreen.render(Gdx.graphics.getDeltaTime());
		}
		
	}
	
	public void setScreen(DefaultScreen screen)
	{
		setScreen(screen, true);
	}
	
	public void setScreen(DefaultScreen screen, boolean show)
	{
		this.currentScreen = screen;
		if (this.currentScreen != null) this.currentScreen.hide();
		this.currentScreen = screen;
		if (this.currentScreen != null) {
			if (show){
				this.currentScreen.show();
				this.currentScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			}
		}
	}
	
	public Screen getScreen()
	{
		return currentScreen;
	}
	
	public void transitionTo(String screenTag)
	{
		nextScreen = screens.get(screenTag);

		if (currentScreen != null && nextScreen != null)
		{
			nextScreen.show();
		
			float duration = 20f;
			MoveByAction actionOut = new MoveByAction();
			actionOut.setDuration(duration);
			actionOut.setAmountX(currentScreen.getStage().getWidth());
			actionOut.setInterpolation(Interpolation.pow5);
			MoveByAction actionIn = new MoveByAction();
			actionIn.setDuration(duration);
			actionIn.setAmountX(nextScreen.getStage().getWidth());
			actionIn.setInterpolation(Interpolation.pow5);
			
			currentScreen.getStage().addAction(actionOut);
			
			nextScreen.getStage().getRoot().setPosition(-nextScreen.getStage().getWidth(), 0);
			nextScreen.getStage().addAction(actionIn);
		}
	}
	
	@Override
	public void dispose () {
		if (currentScreen != null) currentScreen.hide();
	}

	@Override
	public void pause () {
		if (currentScreen != null) currentScreen.pause();
	}

	@Override
	public void resume () {
		if (currentScreen != null) currentScreen.resume();
	}

	@Override
	public void resize (int width, int height) {
		if (currentScreen != null) currentScreen.resize(width, height);
	}

	public void addScreen(String tag, DefaultScreen screen)
	{
		screens.put(tag, screen);
	}
}
