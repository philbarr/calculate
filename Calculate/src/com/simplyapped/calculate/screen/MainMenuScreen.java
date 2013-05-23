package com.simplyapped.calculate.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;

public class MainMenuScreen implements Screen{
	private Stage ui;
	private Table window;
	private Skin uiSkin = new Skin(Gdx.files.internal("data/calculate.json"));
	
	public MainMenuScreen(final CalculateGame game) {
	    ui = new Stage(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), true);
	    Gdx.input.setInputProcessor(ui);
	    window = new Table();
	    
	    window.setFillParent(true);
	    window.setX(0);
	    window.setY(0);
	    window.debug();
	    
	    // calculate width and heights for the table
	    float emptyRowHeight = Gdx.graphics.getHeight() / 10;
	    float logoHeight = Gdx.graphics.getHeight() / 4;
	    float logoWidth = Gdx.graphics.getWidth() / 1.1f;
	    float buttonHeight = Gdx.graphics.getHeight() / 6;
	    float buttonWidth = Gdx.graphics.getWidth() / 1.25f;
	    
	    // title label
	    Image logo = new Image(uiSkin, "calculatelogo");
	    
	    // buttons
	    Button playMenu = new Button(uiSkin, "play");
	    playMenu.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
	        	game.setScreen(game.gameScreen);
	        }
	    });
	    Button optionMenu = new Button(uiSkin, "options");
	    Button tutorialMenu = new Button(uiSkin, "tutorial");

//	    window.row().padTop(emptyRowHeight);
	    window.add(logo).width(logoWidth).height(logoHeight);
	    window.row().expandY();
	    window.add(playMenu).width(buttonWidth).height(buttonHeight);
	    window.row().expandY();
	    window.add(optionMenu).width(buttonWidth).height(buttonHeight);
	    window.row().expandY();
	    window.add(tutorialMenu).width(buttonWidth).height(buttonHeight);
	    window.row().padTop(emptyRowHeight);
	    window.setBackground(uiSkin.getTiledDrawable("graphytile"));
	    
	    ui.addActor(window);
	    Gdx.input.setInputProcessor(ui);
	}

	@Override
	public void render(float delta) {
	    Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
	    Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
	    ui.act();
	    ui.draw();
//	    Table.drawDebug(ui);
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