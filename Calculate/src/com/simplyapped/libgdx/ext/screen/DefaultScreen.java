package com.simplyapped.libgdx.ext.screen;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.simplyapped.libgdx.ext.DefaultGame;

public abstract class DefaultScreen implements StagedScreen
{
	protected Stage stage;
	protected DefaultGame game;
	protected List<Disposable> disposables = new ArrayList<Disposable>();

	public DefaultScreen(DefaultGame game)
	{
		this.game = game;
	}

	public Stage getStage()
	{
		return stage;
	}

	@Override
	public void render(float delta) {
	    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    if (stage == null)
	    {
	    	Gdx.app.log(DefaultScreen.class.toString(), "WARNING: No Stage set on DefaultScreen. Nothing will be drawn.");
	    }
	    else
	    {
		    stage.act();
		    stage.draw();
	    }
	}

	@Override
	public void resize(int width, int height)
	{
	}

	@Override
	public abstract void show();

	@Override
	public void hide()
	{
		//dispose();
	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose()
	{
		if (stage != null)
		{
			stage.dispose();
		}
		for (Disposable disposable : disposables)
		{
			try
			{
				System.out.println("disposing: " + disposable.toString());
				disposable.dispose();
				
			} catch (GdxRuntimeException e)
			{
				System.out.println(e.toString());
				// already disposed not bothered
			}
		}
	}
}
