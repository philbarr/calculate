package com.simplyapped.calculate.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class DefaultScreen implements Screen
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
	    stage.act();
	    stage.draw();
//	    Table.drawDebug(stage);
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
		dispose();
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
	}
}
