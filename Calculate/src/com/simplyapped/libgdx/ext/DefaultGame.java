package com.simplyapped.libgdx.ext;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.simplyapped.libgdx.ext.action.Transition;
import com.simplyapped.libgdx.ext.billing.BillingService;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;
import com.simplyapped.libgdx.ext.screen.StagedScreen;
import com.simplyapped.libgdx.ext.ui.OSDialog;

public abstract class DefaultGame implements ApplicationListener
{
	protected Map<String, StagedScreen> screens = new HashMap<String, StagedScreen>();
	private StagedScreen currentScreen;
	private StagedScreen nextScreen;
	private boolean isTransitioning;
	private Transition transition;

	@Override
	public void render()
	{
		// if transitioning then do the render
		if (nextScreen != null)
		{
			isTransitioning = currentScreen.getStage().getRoot().getActions().size > 0 ||
								nextScreen.getStage().getRoot().getActions().size > 0;
			if (isTransitioning && transition != null)
			{
				transition.render(currentScreen, nextScreen);
			} 
			else
			{
				this.currentScreen = nextScreen;
				if (this.currentScreen != null)
				{
					this.currentScreen.hide();
				}
				this.currentScreen = nextScreen;
				
				if (this.currentScreen != null)
				{
					this.currentScreen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				}
				nextScreen = null;
				isTransitioning = false;
			}
		} 
		else if (currentScreen != null) // else just render the current screen as normal
		{
			currentScreen.render(Gdx.graphics.getDeltaTime());
		}

	}

	public void setScreen(StagedScreen next)
	{
		if (!isTransitioning)
		{
			this.currentScreen = next;
			if (this.currentScreen != null)
				this.currentScreen.hide();
			this.currentScreen = next;
			
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
			this.transition = transition;
			transition.apply(currentScreen, nextScreen);
		}
	}

	@Override
	public void dispose()
	{
		System.out.println("disposing screen");
		if (currentScreen != null)
		{
			currentScreen.dispose();
		}
	}

	@Override
	public void pause()
	{
		System.out.println("pausing screen");
		if (currentScreen != null)
			currentScreen.pause();
	}

	@Override
	public void resume()
	{
		System.out.println("resuming screen");
		if (currentScreen != null)
			currentScreen.resume();
	}

	@Override
	public void resize(int width, int height)
	{
		System.out.println("resizing screen");
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

	public abstract BillingService getBilling();
	public abstract OSDialog getDialog();
}
