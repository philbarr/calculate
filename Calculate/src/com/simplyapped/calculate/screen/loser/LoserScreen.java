package com.simplyapped.calculate.screen.loser;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.repeat;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class LoserScreen extends DefaultScreen
{
	private Skin skin;

	public LoserScreen(DefaultGame game)
	{
		super(game);
		skin = game.getAssets().get("data/loserscreen.json");
	}

	@Override
	public void show()
	{
		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, true);
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
	    
	    // calculate width and heights for the table
	    float emptyRowHeight = stage.getHeight() / 17;
	    float buttonHeight = stage.getHeight() / 7;
	    float buttonWidth = stage.getWidth() / 1.5f;
	    float offset = stage.getHeight() / 3f;
	    Table buttonBorder = new Table();
	    int padding = 20;
		buttonBorder.setPosition(stage.getWidth()/2-buttonWidth/2-padding, emptyRowHeight - padding + offset);
	    buttonBorder.setSize(buttonWidth + padding*2, emptyRowHeight + (buttonHeight*2) + padding);
	    buttonBorder.setBackground(skin.getDrawable("buttonborder"));
	    
	    Image image = new Image(skin, "loserballoon");
	    image.setScale(0.3f);
	    int imageX = (int)(stage.getWidth()/7);
		image.setPosition(imageX, stage.getHeight());
	    int pad = 30;
		image.addAction(
	    		repeat(RepeatAction.FOREVER, 
	    			sequence(
	    				moveBy(0,-stage.getHeight() - (image.getHeight() * image.getScaleY()), 10), 
	    				moveTo(imageX, stage.getHeight()))));
	    stage.addActor(image);
	    
	    final GameState state = GameStateFactory.getInstance();
	    
	    TextButton playAgainButton = new TextButton("Play Again", skin, "green");
	    playAgainButton.addListener(new ClickListener()
	    {
	    	@Override
	    	public void clicked(InputEvent event, float x, float y)
	    	{
	    		game.transitionTo(CalculateGame.STAGE_INTRO_SCREEN, TransitionFixtures.UnderlapRight());
	    	}
	    });
	    playAgainButton.getLabel().setFontScale(0.8f);
	    playAgainButton.setSize(buttonWidth, buttonHeight);
	    playAgainButton.setPosition(stage.getWidth()/2-playAgainButton.getWidth()/2, -emptyRowHeight + (buttonHeight*2) + offset);
	    
	    TextButton viewSolutionButton = new TextButton("View Solution (" + state.getRemainingSolutions() + ")", skin, "green");
	    viewSolutionButton.addListener(new ClickListener(){
	    	@Override
	    	public void clicked(InputEvent event, float x, float y)
	    	{
	    		if (state.getRemainingSolutions() > 0)
				{
					game.transitionTo(CalculateGame.VIEW_SOLUTION_SCREEN, TransitionFixtures.Fade());
				}
				else
				{
					state.setViewingSolution(true);
					game.transitionTo(CalculateGame.SHOP_SCREEN, TransitionFixtures.OverlapLeft());
				}
	    	}
	    });
	    viewSolutionButton.pad(pad);
	    viewSolutionButton.setSize(buttonWidth, buttonHeight);
	    viewSolutionButton.setPosition(stage.getWidth()/2-playAgainButton.getWidth()/2, emptyRowHeight + offset);	    
	    viewSolutionButton.getLabel().setFontScale(0.7f);
	    
	    stage.addActor(buttonBorder);
	    stage.addActor(playAgainButton);
	    stage.addActor(viewSolutionButton);
	    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}
}
