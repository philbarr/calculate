package com.simplyapped.libgdx.ext.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.simplyapped.libgdx.ext.DefaultGame;

public abstract class DefaultScreen implements StagedScreen
{
	protected Stage stage;
	protected DefaultGame game;

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
		Gdx.app.log(DefaultScreen.class.toString(), "RESIZING SCREEN " + this.toString() + ", heap:" + Gdx.app.getJavaHeap() + " native heap:" + Gdx.app.getNativeHeap());
	}

	@Override
	public abstract void show();

	@Override
	public void hide()
	{
		Gdx.app.log(DefaultScreen.class.toString(), "HIDING SCREEN" + this.toString() + ", heap:" + Gdx.app.getJavaHeap() + " native heap:" + Gdx.app.getNativeHeap());
	}

	@Override
	public void pause()
	{
		Gdx.app.log(DefaultScreen.class.toString(), "PAUSING SCREEN " + this.toString() + ", heap:" + Gdx.app.getJavaHeap() + " native heap:" + Gdx.app.getNativeHeap());
	}

	@Override
	public void resume()
	{
		Gdx.app.log(DefaultScreen.class.toString(), "RESUMING SCREEN " + this.toString() + ", heap:" + Gdx.app.getJavaHeap() + " native heap:" + Gdx.app.getNativeHeap());
	}

	@Override
	public void dispose()
	{
		
		try {
			if (stage != null)
			{
				Gdx.app.log(DefaultScreen.class.toString(), "DISPOSING SCREEN " + this.toString() + ", heap:" + Gdx.app.getJavaHeap() + " native heap:" + Gdx.app.getNativeHeap());
				stage.dispose();
			}
		} catch (Exception e1) {
			Gdx.app.error(DefaultScreen.class.toString(), "DISPOSING SCREEN ERROR " + this.toString(), e1);
		}
	}
}
