package com.simplyapped.calculate.screen.loser;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.flat.FlatUIButton;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class LoserScreen extends DefaultScreen
{
	private Skin skin = new Skin(Gdx.files.internal("data/loserscreen.json"));

	public LoserScreen(DefaultGame game)
	{
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
					game.transitionTo(CalculateGame.STAGE_SELECT_SCREEN, TransitionFixtures.UnderlapRight());
					return true;
				}
				return false;
			}
		});
	    Table table = new Table();
	    table.setFillParent(true);
	    table.setBackground(skin.getDrawable("loserscreenbackground"));
	    stage.addActor(table);
	    
	    Image image = new Image(skin, "loserballoon");
	    image.setScale(0.3f);
	    int imageX = CalculateGame.SCREEN_WIDTH/7;
		image.setPosition(imageX, CalculateGame.SCREEN_HEIGHT);
	    int pad = 30;
		image.addAction(
	    		repeat(RepeatAction.FOREVER, 
	    			sequence(
	    				moveBy(0,-CalculateGame.SCREEN_HEIGHT - (image.getHeight() * image.getScaleY()), 10), 
	    				moveTo(imageX, CalculateGame.SCREEN_HEIGHT))));
	    stage.addActor(image);
	    
	    final GameState state = GameStateFactory.getInstance();
	    
	    Table buttonTable = new Table();
	    FlatUIButton playAgainButton = new FlatUIButton("Play Again", skin, "playagain");
	    playAgainButton.addListener(new ClickListener()
	    {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y)
	    	{
	    		game.transitionTo(CalculateGame.STAGE_INTRO_SCREEN, TransitionFixtures.UnderlapRight());
	    	}
	    });
	    playAgainButton.getLabel().setFontScale(0.7f);
	    playAgainButton.pad(pad);
	    
	    FlatUIButton viewSolutionButton = new FlatUIButton("View Solution (" + state.getRemainingSolutions() + ")", skin, "viewsolution");
	    viewSolutionButton.addListener(new ClickListener(){
	    	@Override
	    	public void clicked(InputEvent event, float x, float y)
	    	{
	    		if (state.getRemainingSolutions() > 0)
	    		{
	    			
	    		}
	    		else
	    		{
	    			
	    		}
	    	}
	    });
	    viewSolutionButton.pad(pad);
	    viewSolutionButton.getLabel().setFontScale(0.7f);
	    disposables.add(playAgainButton);
	    disposables.add(viewSolutionButton);
	    
	    buttonTable.add(playAgainButton).fillX().pad(pad);
	    buttonTable.row();
	    buttonTable.add(viewSolutionButton).fillX().pad(pad);
	    buttonTable.setPosition(CalculateGame.SCREEN_WIDTH / 2 - buttonTable.getWidth()/2, CalculateGame.SCREEN_HEIGHT/2);
	    stage.addActor(buttonTable);
	    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

}
