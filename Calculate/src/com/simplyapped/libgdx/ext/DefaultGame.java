package com.simplyapped.libgdx.ext;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncTask;
import com.simplyapped.libgdx.ext.action.Transition;
import com.simplyapped.libgdx.ext.billing.BillingService;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;
import com.simplyapped.libgdx.ext.ui.OSDialog;

public abstract class DefaultGame implements ApplicationListener
{
	protected Map<String, Class<? extends DefaultScreen>> screens = new HashMap<String, Class<? extends DefaultScreen>>();
	private Map<String, Stack<DefaultScreen>> screenInstances = new HashMap<String, Stack<DefaultScreen>>();
	private DefaultScreen currentScreen;
	private DefaultScreen nextScreen;
	private boolean isTransitioning;
	private Transition transition;
	private AsyncExecutor async = new AsyncExecutor(100);
	protected AssetManager assets;

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
			else // transitioning has finished swap nextScreen to currentScreen and dispose of old screen
			{
				final DefaultScreen toDispose = currentScreen;

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
				async.submit(new AsyncTask<Void>() {

					@Override
					public Void call() throws Exception {
						toDispose.dispose();
						return null;
					}
				});
			}
		} 
		else if (currentScreen != null) // else just render the current screen as normal
		{
			currentScreen.render(Gdx.graphics.getDeltaTime());
		}

	}

	public void setScreen(DefaultScreen next)
	{
		if (!isTransitioning)
		{
			if (this.currentScreen != null)
			{
				this.currentScreen.hide();
				async.submit(new AsyncTask<Void>() {

					@Override
					public Void call() throws Exception {
						currentScreen.dispose();
						return null;
					}
				});
			}
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
			nextScreen = createNextScreenInstance(screenTag);
			this.transition = transition;
			transition.apply(currentScreen, nextScreen);
		}
	}

	private DefaultScreen createNextScreenInstance(String screenTag)
	{
		DefaultScreen newScreen = null;
		try 
		{
			newScreen = screens.get(screenTag).getConstructor(DefaultGame.class).newInstance(this);
		} 
		catch (Exception e)
		{
			Gdx.app.log(DefaultGame.class.toString(), "Failed to create next screen");
		}
		return newScreen;
	}

	private void generateScreenAsync(final String screenTag) {
		async.submit(new AsyncTask<Void>() {

			@Override
			public Void call() throws Exception {
				try {
					if (screenInstances.get(screenTag) == null)
					{
						Stack<DefaultScreen> screens = new Stack<DefaultScreen>();
						screenInstances.put(screenTag, screens);
					}
					
					DefaultScreen newScreen = screens.get(screenTag).getConstructor(DefaultGame.class).newInstance(DefaultGame.this);
					screenInstances.get(screenTag).push(newScreen);
				} catch (Exception e) {
					Gdx.app.log("DefaultGame.AsyncTask", "Failed to add new screen",e);
				}
				return null;
			}
		});
	}

	@Override
	public void dispose()
	{
		System.out.println("disposing screen");
		if (currentScreen != null)
		{
			currentScreen.dispose();
		}
		if (nextScreen != null)
		{
			nextScreen.dispose();
		}
		for (Stack<DefaultScreen> screens : screenInstances.values()) {
			if (screens != null && screens.size() > 0)
			{
				for (DefaultScreen screen : screens) {
					screen.dispose();
				}
			}
		}
		async.dispose();
		getAssets().dispose();
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

	public void addScreen(String tag, Class<? extends DefaultScreen> screen)
	{
		screens.put(tag, screen);
	}

	protected void setScreen(String screen)
	{
		try 
		{
			setScreen(createNextScreenInstance(screen));
		} 
		catch (Exception e)
		{
			Gdx.app.log(DefaultGame.class.toString(), "Failed to SET next screen");
		}
	}

	public abstract BillingService getBilling();
	public abstract OSDialog getDialog();

	public AssetManager getAssets() {
		if (assets == null)
		{
			assets = new AssetManager();
		}
		return assets;
	}
}
