package com.simplyapped.calculate.screen;

import sun.rmi.runtime.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;

public class MainMenuScreen implements Screen{
	private Stage ui;
	private Table window;
	private Skin uiSkin = new Skin(Gdx.files.internal("data/uiskin.json"));
	
	public MainMenuScreen(final CalculateGame game) {
	    ui = new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
	    Gdx.input.setInputProcessor(ui);
	    window = new Table();
	    window.setWidth(ui.getWidth());
	    window.setHeight(ui.getHeight());
	    window.setX(0);
	    window.setY(0);
	    window.debug();
	    Label title = new Label("Title", uiSkin);
	    Button newGame = new Button(uiSkin, "default");
	    newGame.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
	        	game.setScreen(game.gameScreen);
	        }
	    });
	    Button optionMenu = new Button(uiSkin, "default");
	    Button helpMenu = new Button(uiSkin, "default");
	    window.row().fill(false,false).expand(true,false).padTop(50).padBottom(50);
	    window.add(title);
	    Table container = new Table(uiSkin);
	    container.row().fill(true, true).expand(true, true).pad(10, 0, 10, 0);
	    container.add(newGame);
	    container.row().fill(true, true).expand(true, true).pad(10, 0, 10, 0);
	    container.add(optionMenu);
	    container.row().fill(true, true).expand(true, true).pad(10, 0, 10, 0);
	    container.add(helpMenu);
	    window.row().fill(0.5f,1f).expand(true,false);
	    window.add(container);
	    Table extras = new Table(uiSkin);
	    extras.row().fill(false,false).expand(true,true);
	    window.row().fill(true,false).expand(true,true);
	    window.add(extras).bottom();
	    ui.addActor(window);
	    Gdx.input.setInputProcessor(ui);
	}

	@Override
	public void render(float arg0) {
	    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    ui.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	    ui.draw();
	    Table.drawDebug(ui);
	}
	@Override
	public void resize(int width, int height) {
	    ui.setViewport(width, height, true);
	}

	@Override
	public void show()
	{
		// TODO Auto-generated method stub
		
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
		// TODO Auto-generated method stub
		
	}
}