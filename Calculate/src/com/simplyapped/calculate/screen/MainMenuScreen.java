package com.simplyapped.calculate.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;

public class MainMenuScreen extends DefaultScreen{
	
	private Table window;
	private Skin uiSkin = new Skin(Gdx.files.internal("data/mainmenuscreen.json"));
	private CalculateGame game;
	private DefaultGame game2;
	
	public MainMenuScreen(DefaultGame game) {
		super(game);
		game2 = game;
	}
	
	public void fade(){
		AlphaAction action = new AlphaAction();
		action.setDuration(3);
		if (stage !=null)
		stage.addAction(action);
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
	    
	    // calculate width and heights for the table
	    float emptyRowHeight = Gdx.graphics.getHeight() / 30;
	    float buttonHeight = Gdx.graphics.getHeight() / 6;
	    float buttonWidth = Gdx.graphics.getWidth() / 1.25f;
	    
	    // buttons
	    TextButton playMenu = new TextButton("PLAY", uiSkin);
	    playMenu.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
	        	game.setScreen(game.gameScreen);
	        }
	    });
	    playMenu.padBottom(20);
	    TextButton optionMenu = new TextButton("Options", uiSkin);
	    optionMenu.padBottom(20);
	    TextButton tutorialMenu = new TextButton("Tutorial", uiSkin);
	    tutorialMenu.padBottom(20);
	    
	    window.row().padTop(emptyRowHeight * 3);
	    window.add(playMenu).width(buttonWidth).height(buttonHeight);
	    window.row().padTop(emptyRowHeight);
	    window.add(optionMenu).width(buttonWidth).height(buttonHeight);
	    window.row().padTop(emptyRowHeight);
	    window.add(tutorialMenu).width(buttonWidth).height(buttonHeight);
	    window.row().padTop(emptyRowHeight);
	    window.setBackground(uiSkin.getDrawable("mainmenubackground"));
	    
	    stage.addActor(window);
	    Gdx.input.setInputProcessor(stage);
	}
}