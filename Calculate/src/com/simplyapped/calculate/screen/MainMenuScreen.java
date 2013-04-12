package com.simplyapped.calculate.screen;

import sun.rmi.runtime.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;

public class MainMenuScreen implements Screen{
	private Stage ui;
	private Table window;
	
	public MainMenuScreen(final CalculateGame game) {
	    TextureRegion image = new TextureRegion(new Texture(Gdx.files.internal(Art.badlogicSmall)));
	    Label fps = new Label("fps: ", Art.sSkin.getStyle(LabelStyle.class),"fps");
	    ui = new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
	    Gdx.input.setInputProcessor(ui);
	    window = new Table();
	    window.setWidth(ui.getWidth());
	    window.setHeight(ui.getHeight());
	    window.setX(0);
	    window.setY(0);
	    window.debug();
	    Label title = new Label("Title",Art.sSkin.getStyle(LabelStyle.class),"title");
	    Button newGame = new Button("New Game",Art.sSkin.getStyle(ButtonStyle.class),"new");
	    newGame.addListener(new ClickListener() {
	        public void click(Actor actor) {
	            game.setScreen(game.gameScreen);               
	        }
	    });
	    Button optionMenu = new Button("Option",Art.sSkin.getStyle(ButtonStyle.class),"Options");
	    Button helpMenu = new Button("Help",Art.sSkin.getStyle(ButtonStyle.class),"Help");
	    Image libgdx = new Image("libgdx", image);
	    window.row().fill(false,false).expand(true,false).padTop(50).padBottom(50);
	    window.add(title);
	    Table container = new Table("menu");
	    container.row().fill(true, true).expand(true, true).pad(10, 0, 10, 0);
	    container.add(newGame);
	    container.row().fill(true, true).expand(true, true).pad(10, 0, 10, 0);
	    container.add(optionMenu);
	    container.row().fill(true, true).expand(true, true).pad(10, 0, 10, 0);
	    container.add(helpMenu);
	    window.row().fill(0.5f,1f).expand(true,false);
	    window.add(container);
	    Table extras = new Table("extras");
	    extras.row().fill(false,false).expand(true,true);
	    extras.add(fps).left().center().pad(0,25,25,0); 
	    extras.add(libgdx).right().center().pad(0,0,25,25);
	    window.row().fill(true,false).expand(true,true);
	    window.add(extras).bottom();
	    ui.addActor(window);
	}

	@Override
	public void render(float arg0) {
	    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    ((Label)ui.findActor("fps")).setText("fps: " + Gdx.graphics.getFramesPerSecond());  
	    ui.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	    ui.draw();
	    Table.drawDebug(ui);
	}
	@Override
	public void resize(int width, int height) {
	    ui.setViewport(width, height, true);
	    Log.d("Resize: "+width+", "+height);
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