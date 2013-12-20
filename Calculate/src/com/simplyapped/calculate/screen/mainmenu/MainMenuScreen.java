package com.simplyapped.calculate.screen.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.flat.FlatUI;
import com.simplyapped.libgdx.ext.scene2d.flat.FlatUIButton;
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
				if (keycode == Keys.BACK || keycode == Keys.BACKSPACE)
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
	    float emptyRowHeight = CalculateGame.SCREEN_HEIGHT / 17;
	    float buttonHeight = CalculateGame.SCREEN_HEIGHT / 7;
	    float buttonWidth = CalculateGame.SCREEN_WIDTH / 2f;
	    
	    Table buttonBorder = new Table();
	    int padding = 20;
		buttonBorder.setPosition(CalculateGame.SCREEN_WIDTH/2-buttonWidth/2-padding, emptyRowHeight - padding);
	    buttonBorder.setSize(buttonWidth + padding*2, emptyRowHeight + (buttonHeight*2) + padding);
	    buttonBorder.setBackground(createBackground(0.9f,0.9f,0.9f,0.8f));
	    
	    // buttons
	    FlatUIButton playMenu = new FlatUIButton("PLAY", skin, "play");
	    playMenu.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
	        	game.transitionTo(CalculateGame.STAGE_SELECT_SCREEN, TransitionFixtures.OverlapLeft());
	        }
	    });
	    playMenu.setSize(buttonWidth, buttonHeight);
	    playMenu.setPosition(CalculateGame.SCREEN_WIDTH/2-playMenu.getWidth()/2, -emptyRowHeight + (buttonHeight*2));
	    disposables.add(playMenu);
	    
	    FlatUIButton shopMenu = new FlatUIButton("SHOP", skin, "shop");
	    shopMenu.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
	        	game.transitionTo(CalculateGame.SHOP_SCREEN, TransitionFixtures.OverlapLeft());
	        }
	    });
	    shopMenu.setSize(buttonWidth, buttonHeight);
	    shopMenu.setPosition(CalculateGame.SCREEN_WIDTH/2-playMenu.getWidth()/2, emptyRowHeight);
	    disposables.add(shopMenu);
	    
	    window.setBackground(skin.getDrawable("mainmenubackground"));
	    
	    stage.addActor(window);
	    stage.addActor(buttonBorder);
	    stage.addActor(playMenu);
	    stage.addActor(shopMenu);
	    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}
	
	private TextureRegionDrawable createBackground(float r, float g, float b, float a)
	{
		Pixmap pix = new Pixmap(1,1,Format.RGBA4444);
		pix.setColor(r,g,b,a);
		pix.fill();
		disposables.add(pix);
		Texture texture = new Texture(pix);
		TextureRegionDrawable trd = new TextureRegionDrawable(new TextureRegion(texture));
		disposables.add(texture);
		return trd;
	}
}