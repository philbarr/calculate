package com.simplyapped.calculate.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.simplyapped.calculate.CalculateGame;

public class GameScreen implements Screen
{
	private Stage stage;
	private Table window;
	private Skin uiSkin = new Skin(Gdx.files.internal("data/gamescreen.json"));
	private CalculateGame game;

	public GameScreen(CalculateGame game)
	{
		this.game = game;
	}

	@Override
	public void render(float delta) {
	    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    stage.act();
	    stage.draw();
//	    Table.drawDebug(ui);
	}
	@Override
	public void resize(int width, int height) {
	   // stage.setViewport(width, height, true);
	}

	@Override
	public void show()
	{
	    stage = new Stage(600, 800, false);

	    window = new Table();	    
	    window.setFillParent(true);
	    window.setX(0);
	    window.setY(0);
	    window.debug();
	    
	    window.setBackground(uiSkin.getDrawable("gamescreenbackground"));
	    
	    stage.addActor(window);
	    Gdx.input.setInputProcessor(stage);
	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

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
