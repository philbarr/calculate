package com.simplyapped.calculate.screen.shop;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Disposable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.flat.FlatUIButton;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class ShopScreen extends DefaultScreen{
	
	private Skin skin = new Skin(Gdx.files.internal("data/gamescreen.json"));
	private Table window;
	private float buttonHeight;
	private float buttonWidth;

	public ShopScreen(DefaultGame game) {
		super(game);
	}

	@Override
	public void show() {
		if (disposables==null)
		{
			disposables=new ArrayList<Disposable>();
		}
		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, false);
		stage.addListener(new ClickListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if (keycode == Keys.BACK || keycode == Keys.BACKSPACE)
				{
					game.transitionTo(CalculateGame.MAIN_MENU_SCREEN, TransitionFixtures.UnderlapRight());
					return true;
				}
				return false;
			}
		});
		window = new Table();	    
	    window.setFillParent(true);
	    window.setBackground(skin.getDrawable("gamescreenbackground"));

	    buttonHeight = CalculateGame.SCREEN_HEIGHT / 7;
	    buttonWidth = CalculateGame.SCREEN_WIDTH / 2f;
	    float height = CalculateGame.SCREEN_HEIGHT/15f;

	    stage.addActor(window);
		addButton(height, "Buy 10 Solutions");
		addButton(height*2+buttonHeight, "Buy 25 Solutions");
		addButton(height*3 + buttonHeight*2, "Buy 50 Solutions");
	    
		
		
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

	private void addButton(float height, String text) {
		FlatUIButton purchase = new FlatUIButton(text, skin, "dialogWhite");
		purchase.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				//TODO add purchasing here
			}
		});
		purchase.getLabel().setFontScale(0.8f);
	    purchase.setSize(buttonWidth, buttonHeight);
		purchase.setPosition(CalculateGame.SCREEN_WIDTH/2 - purchase.getWidth()/2, height);
		disposables.add(purchase);
	    stage.addActor(purchase);
	}

}
