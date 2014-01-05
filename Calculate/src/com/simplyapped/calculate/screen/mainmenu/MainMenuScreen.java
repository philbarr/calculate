package com.simplyapped.calculate.screen.mainmenu;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.flat.FlatUIButton;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class MainMenuScreen extends DefaultScreen{
	public static boolean assetsLoaded;
	private Table window;
	private Skin skin;
	private Table buttonBorder;
	private FlatUIButton playMenu;
	private FlatUIButton shopMenu;
	private Image spinner;
	
	public MainMenuScreen(DefaultGame game) {
		super(game);
		skin = game.getAssets().get("data/mainmenuscreen.json");
	}
	@Override
	public void render(float delta) {
		super.render(delta);
		if (game.getAssets().update())
		{
			buttonBorder.setVisible(true);
			playMenu.setVisible(true);
			shopMenu.setVisible(true);
			spinner.setVisible(false);
			assetsLoaded = true;
		}
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
	    float buttonWidth = CalculateGame.SCREEN_WIDTH / 1.5f;
	    
	    buttonBorder = new Table();
	    int padding = 20;
		buttonBorder.setPosition(CalculateGame.SCREEN_WIDTH/2-buttonWidth/2-padding, emptyRowHeight - padding);
	    buttonBorder.setSize(buttonWidth + padding*2, emptyRowHeight + (buttonHeight*2) + padding);
	    buttonBorder.setBackground(createBackground(0.9f,0.9f,0.9f,0.8f));
	    buttonBorder.setVisible(assetsLoaded);
	    
	    playMenu = new FlatUIButton("PLAY", skin, "play");
	    playMenu.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
	        	game.getAssets().finishLoading(); //ensure everything is loaded so we can transition
	        	game.transitionTo(CalculateGame.STAGE_SELECT_SCREEN, TransitionFixtures.OverlapLeft());
	        }
	    });
	    playMenu.setSize(buttonWidth, buttonHeight);
	    playMenu.setPosition(CalculateGame.SCREEN_WIDTH/2-playMenu.getWidth()/2, -emptyRowHeight + (buttonHeight*2));
	    playMenu.setVisible(assetsLoaded);
	    disposables.add(playMenu);
	    
	    shopMenu = new FlatUIButton("SHOP", skin, "shop");
	    shopMenu.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
	        	game.getAssets().finishLoading(); //ensure everything is loaded so we can transition
	        	game.transitionTo(CalculateGame.SHOP_SCREEN, TransitionFixtures.OverlapLeft());
	        }
	    });
	    shopMenu.setSize(buttonWidth, buttonHeight);
	    shopMenu.setPosition(CalculateGame.SCREEN_WIDTH/2-playMenu.getWidth()/2, emptyRowHeight);
	    shopMenu.setVisible(assetsLoaded);
	    disposables.add(shopMenu);
	    
	    window.setBackground(skin.getDrawable("mainmenubackground"));
	    
	    spinner = new Image(skin.getDrawable("spinner"));
	    spinner.setSize(CalculateGame.SCREEN_WIDTH * 0.7f, CalculateGame.SCREEN_WIDTH*0.7f);//make it sqaure
	    spinner.setPosition(CalculateGame.SCREEN_WIDTH/2f-spinner.getWidth()/2f, CalculateGame.SCREEN_HEIGHT /2f - spinner.getHeight()/2f);
	    spinner.setOrigin(spinner.getHeight()/2f, spinner.getWidth()/2f);
	    spinner.addAction(repeat(RepeatAction.FOREVER, sequence( delay(0.1f), ( rotateBy(-30)))));
	    spinner.setVisible(!assetsLoaded);
	    
	    stage.addActor(window);
	    stage.addActor(buttonBorder);
	    stage.addActor(playMenu);
	    stage.addActor(shopMenu);
	    stage.addActor(spinner);
	    
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