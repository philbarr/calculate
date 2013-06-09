package com.simplyapped.libgdx.ext;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.simplyapped.libgdx.ext.action.Transition;

public abstract class DefaultGame implements ApplicationListener
{
	protected Map<String, DefaultScreen> screens = new HashMap<String, DefaultScreen>();
	private DefaultScreen currentScreen;
	private DefaultScreen nextScreen;
	private boolean isTransitioning;

	@Override
	public void render()
	{
		// if transitioning then do the render
		if (nextScreen != null)
		{
			isTransitioning = nextScreen.getStage().getRoot().getActions().size > 0;
			if (isTransitioning)
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
				setScreen(nextScreen);
				nextScreen = null;
				isTransitioning = false;
			}
		} 
		else if (currentScreen != null) // else just render the current screen as normal
		{
			currentScreen.render(Gdx.graphics.getDeltaTime());
		}

	}

	public void setScreen(DefaultScreen screen)
	{
		if (!isTransitioning)
		{
			this.currentScreen = screen;
			if (this.currentScreen != null)
				this.currentScreen.hide();
			this.currentScreen = screen;
			if (this.currentScreen != null)
			{
				this.currentScreen.show();
				this.currentScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			}
		}
	}

	public Screen getScreen()
	{
		return currentScreen;
	}

	public void transitionTo(String screenTag, Transition transition)
	{
		if (!isTransitioning)
		{
			nextScreen = screens.get(screenTag);

			transition.apply(currentScreen, nextScreen);
		}
	}

	@Override
	public void dispose()
	{
		if (currentScreen != null)
			currentScreen.hide();
	}

	@Override
	public void pause()
	{
		if (currentScreen != null)
			currentScreen.pause();
	}

	@Override
	public void resume()
	{
		if (currentScreen != null)
			currentScreen.resume();
	}

	@Override
	public void resize(int width, int height)
	{
		if (currentScreen != null)
			currentScreen.resize(width, height);
	}

	public void addScreen(String tag, DefaultScreen screen)
	{
		screens.put(tag, screen);
	}

	protected void setScreen(String screen)
	{
		setScreen(screens.get(screen));
	}

}
