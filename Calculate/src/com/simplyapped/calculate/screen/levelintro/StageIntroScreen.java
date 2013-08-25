package com.simplyapped.calculate.screen.levelintro;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class StageIntroScreen extends DefaultScreen
{
	enum Scene
	{
		SHUFFLING_REDS,
		SHUFFLING_BLUES,
		SELECTING,
		GENERATING_TARGET_NUMBER
	}
	
	private Skin skin = new Skin(Gdx.files.internal("data/stageintroscreen.json"));
	private List<ImageTextButton> redCards = new ArrayList<ImageTextButton>();
	private List<ImageTextButton> blueCards = new ArrayList<ImageTextButton>();
	private Scene scene;
	
	public StageIntroScreen(DefaultGame game)
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
		//background
		Table back = new Table();
		back.setBackground(skin.getDrawable("stageintroscreenbackground"));
		back.setFillParent(true);
		stage.addActor(back);
		
		//cards
		final int cardSize = CalculateGame.SCREEN_WIDTH / 5;
		final int leftRedMargin = CalculateGame.SCREEN_WIDTH / 9;
		final int leftBlueMargin = CalculateGame.SCREEN_WIDTH / 7;
		final int redCardsHeight = CalculateGame.SCREEN_HEIGHT/2;
		final int blueCardsHeight = CalculateGame.SCREEN_HEIGHT/4;
		final int redSpacing = 0;
		final int blueSpacing = 0;
		for (int cardindex = 0; cardindex < 13; cardindex++)
		{
			ImageTextButton card;
			if (cardindex <= 3)
			{
				card = new ImageTextButton("", skin, "cardbackred");
				card.setPosition(leftRedMargin + redSpacing + (cardindex * cardSize), redCardsHeight );
				
				redCards.add(card);
			}
			else
			{
				int blueCardIndex = cardindex - 4;
				card = new ImageTextButton("", skin, "cardbackblue");
				card.setPosition(leftBlueMargin + blueSpacing + blueCardIndex * cardSize, blueCardsHeight );
				blueCards.add(card);
			}
			card.size(cardSize);
			stage.addActor(card);
		}
		scene = Scene.SHUFFLING_REDS;
	}
	
	@Override
	public void render(float delta)
	{
		super.render(delta);
		switch(scene)
		{
		case GENERATING_TARGET_NUMBER:
			renderGeneratingTargetNumber(delta);
			break;
		case SELECTING:
			renderSelecting(delta);
			break;
		case SHUFFLING_BLUES:
			renderShufflingBlues(delta);
			break;
		case SHUFFLING_REDS:
			renderShufflingReds(delta);
			break;
		default:
			break;
		}
	}

	private void renderShufflingReds(float delta)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderShufflingBlues(float delta)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderSelecting(float delta)
	{
		// TODO Auto-generated method stub
		
	}

	private void renderGeneratingTargetNumber(float delta)
	{
		// TODO Auto-generated method stub
		
	}
}
