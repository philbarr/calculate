package com.simplyapped.calculate.screen.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class MainMenuScreen extends DefaultScreen{
	
	private Table window;
	private Skin skin = new Skin(Gdx.files.internal("data/mainmenuscreen.json"));
	
	public MainMenuScreen(DefaultGame game) {
		super(game);
	}

	@Override
	public void show()
	{
		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, false);
		stage.addListener(new ClickListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if (keycode == Keys.BACK)
				{
					Gdx.app.exit();
				}
				return false;
			}
		});
		
	    window = new Table();	    
	    window.setFillParent(true);
	    window.setX(0);
	    window.setY(0);
	    window.debug();
	    
	    // calculate width and heights for the table
	    float emptyRowHeight = CalculateGame.SCREEN_HEIGHT / 30;
	    float buttonHeight = CalculateGame.SCREEN_HEIGHT / 6;
	    float buttonWidth = CalculateGame.SCREEN_WIDTH / 1.25f;
	    
	    // buttons
	    TextButton playMenu = new TextButton("PLAY", skin);
	    playMenu.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
	        	game.transitionTo(CalculateGame.STAGE_SELECT_SCREEN, TransitionFixtures.OverlapLeft());
	        }
	    });
	    playMenu.padBottom(CalculateGame.SCREEN_HEIGHT/30);
	    TextButton optionMenu = new TextButton("Options", skin);
	    optionMenu.padBottom(CalculateGame.SCREEN_HEIGHT/30);
	    TextButton tutorialMenu = new TextButton("Tutorial", skin);
	    tutorialMenu.padBottom(CalculateGame.SCREEN_HEIGHT/30);
	    
	    window.row().padTop(emptyRowHeight * 3);
	    window.add(playMenu).width(buttonWidth).height(buttonHeight);
	    window.row().padTop(emptyRowHeight);
	    window.add(optionMenu).width(buttonWidth).height(buttonHeight);
	    window.row().padTop(emptyRowHeight);
	    window.add(tutorialMenu).width(buttonWidth).height(buttonHeight);
	    window.row().padTop(emptyRowHeight);
	    window.setBackground(skin.getDrawable("mainmenubackground"));
	    
	    stage.addActor(window);
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}
}