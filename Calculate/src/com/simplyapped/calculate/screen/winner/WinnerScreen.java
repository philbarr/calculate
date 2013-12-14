package com.simplyapped.calculate.screen.winner;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures; 
import com.simplyapped.libgdx.ext.scene2d.flat.FlatUI;
import com.simplyapped.libgdx.ext.scene2d.flat.FlatUIButton;
import com.simplyapped.libgdx.ext.scene2d.spinner.NumberSpinnerTable;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;


public class WinnerScreen extends DefaultScreen
{
	private static final int BALLOON_COUNT = 30;
	
	private Skin skin = new Skin(Gdx.files.internal("data/winnerscreen.json"));
	private int BALLOON_RANDOM_OFFSET = CalculateGame.SCREEN_WIDTH;
	

	public WinnerScreen(DefaultGame game)
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
		
		Table background = new Table();
		background.setBackground(skin.getDrawable("winnerscreenbackground"));
		background.setFillParent(true);
		stage.addActor(background);
		
	    CelebrationActor lefteffect = new CelebrationActor(skin.getAtlas(), 0, 0, "data/particle/celebrationleft.p", "data/particle");
	    CelebrationActor righteffect = new CelebrationActor(skin.getAtlas(), CalculateGame.SCREEN_WIDTH, 0, "data/particle/celebrationright.p", "data/particle");
	    stage.addActor(lefteffect);
	    stage.addActor(righteffect);
		
		Random r = new Random();
		for(int i = 0; i < BALLOON_COUNT; i++)
		{
			Image image = new Image(skin, "winnerballoon");
			float height = r.nextInt(CalculateGame.SCREEN_HEIGHT) - CalculateGame.SCREEN_HEIGHT;
			float x =  r.nextInt(BALLOON_RANDOM_OFFSET-(int)image.getWidth());
			float ballonMoveDuration = (r.nextFloat())*10f + 2f; // random between 2 and 12
			image.setPosition(x, height);
			image.setScale(0.3f);
			image.addAction(
					sequence(
						moveBy(0, CalculateGame.SCREEN_HEIGHT - height, ballonMoveDuration + (CalculateGame.SCREEN_HEIGHT/height)),
						repeat(RepeatAction.FOREVER, 
							sequence(
								moveTo(x, -image.getHeight()),
								moveBy(0, CalculateGame.SCREEN_HEIGHT*2, ballonMoveDuration)
								))));
			stage.addActor(image);
		}

		// number spinner
		int total = GameStateFactory.getInstance().getCurrentEquation().getTotal();
		Table numberTable = new NumberSpinnerTable(Math.abs(total), Interpolation.elasticOut, 2, 0.2f);
		numberTable.setPosition(CalculateGame.SCREEN_WIDTH/2 - numberTable.getWidth()/2, CalculateGame.SCREEN_HEIGHT/2f - numberTable.getHeight()/2);
		stage.addActor(numberTable);

		// "Correct" label
		Label label = new Label("Correct", skin, "text");
		label.setAlignment(Align.center);
		label.setFontScale(0.6f);
		label.setSize(CalculateGame.SCREEN_WIDTH/1.3f, CalculateGame.SCREEN_HEIGHT/4.5f);
		label.setPosition(CalculateGame.SCREEN_WIDTH/2 - label.getWidth()/2, CalculateGame.SCREEN_HEIGHT/1.4f);
		TextureRegionDrawable back = FlatUI.CreateBackgroundDrawable(0.2f, 0.2f, 0.2f, 0.9f, label.getWidth(), label.getHeight());
		disposables.add(back.getRegion().getTexture());
		label.getStyle().background = back;
		stage.addActor(label); 
		
	    // calculate width and heights for the table 
	    float emptyRowHeight = CalculateGame.SCREEN_HEIGHT / 17;
	    float buttonHeight = CalculateGame.SCREEN_HEIGHT / 7;
	    float buttonWidth = CalculateGame.SCREEN_WIDTH / 2f;
	    
	    // buttons
	    FlatUIButton playMenu = new FlatUIButton("Play Again", skin, "playagain");
	    playMenu.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
	        	game.transitionTo(CalculateGame.STAGE_SELECT_SCREEN, TransitionFixtures.UnderlapRight());
	        }
	    });
	    playMenu.setSize(buttonWidth, buttonHeight);
	    playMenu.setPosition(CalculateGame.SCREEN_WIDTH/2-playMenu.getWidth()/2, emptyRowHeight);
	    disposables.add(playMenu);
	    stage.addActor(playMenu);
	    
	    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}
	

}
