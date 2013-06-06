package com.simplyapped.calculate.screen;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public abstract class DefaultGame extends Game
{
	private Map<String, Screen> screens = new HashMap<String, Screen>();
	private boolean isTransitioning;

	@Override
	public abstract void create();

	
	@Override
	public void render()
	{
		Screen screen = getScreen();
		if (screen != null) 
		{
			screen.render(Gdx.graphics.getDeltaTime());
		}
	}
	
	public void transitionTo(String screenTag)
	{
		Screen screen = screens.get(screenTag);
		if (screen != null)
		{
			setScreen(screen);
		}
	}
}
