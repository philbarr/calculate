package com.simplyapped.calculate.screen.levelintro;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.math.Interpolation.*;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Interpolation;
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

public class StageIntroScreen extends DefaultScreen
{
	private final class CardClickListener extends ClickListener
	{
		private final TextButton card;
		private String styleName;

		private CardClickListener(TextButton card, boolean isCardRed)
		{
			this.card = card;
			this.styleName = isCardRed ? "cardfrontred" : "cardfrontblue";
		}

		@Override
		public void clicked(InputEvent event, float x, float y)
		{
			TextButton button = new TextButton("100", skin, styleName);
			button.setPosition(card.getX(), card.getY());
			button.setSize(cardSize, cardSize);
			card.setVisible(false);
			stage.addActor(button);
		}
	}

	enum Scene
	{
		SELECTING,
		GENERATING_TARGET_NUMBER, 
		SHUFFLING
	}
	
	private Skin skin = new Skin(Gdx.files.internal("data/stageintroscreen.json"));
	private List<TextButton> redCards = new ArrayList<TextButton>();
	private List<TextButton> blueCards = new ArrayList<TextButton>();
	final int cardSize = CalculateGame.SCREEN_WIDTH / 6;
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
		final int redCardsHeight = (int) (CalculateGame.SCREEN_HEIGHT/1.5f);
		final int blueCardsHeight = (int) (CalculateGame.SCREEN_HEIGHT/2.2f);
		final int redSpacing = (int) (cardSize/2.7f);
		final int blueSpacing = (int) (cardSize/2.2f);
		final int redMargin = (CalculateGame.SCREEN_WIDTH - (3 * redSpacing) - (4 * cardSize))/2; // 4 cards, 3 spaces
		final int blueMargin = (CalculateGame.SCREEN_WIDTH - (2 * blueSpacing) - (3 * cardSize))/2; // 3 cards, 2 spaces

		final int shuffleWidth = CalculateGame.SCREEN_WIDTH / 2 - cardSize / 2;
		final int shuffleHeightRed = redCardsHeight + cardSize/2;
		final int shuffleHeightBlue = blueCardsHeight - cardSize;
		
		final float shuffleduration = 0.5f;
		final float shuffledelayduration = 0.1f;
		final Interpolation interpolation = circle;
		
		for (int cardindex = 0; cardindex < 13; cardindex++)
		{
			TextButton card;
			if (cardindex <= 3)
			{
				card = new TextButton("", skin, "cardbackred");
				int width = redMargin + (cardindex * redSpacing ) + (cardindex * cardSize);
				card.setPosition(width, redCardsHeight );
				card.addAction(sequence(
						moveTo(shuffleWidth, shuffleHeightRed, shuffleduration, interpolation), 
						delay(shuffledelayduration), 
						moveTo(width, redCardsHeight, shuffleduration, interpolation)));
				redCards.add(card);
			}
			else
			{
				int blueCardIndex = cardindex - 4;
				int row = blueCardIndex / 3;
				card = new TextButton("", skin, "cardbackblue");
				int width = blueMargin + ((blueCardIndex % 3) * blueSpacing) + ((blueCardIndex % 3) * cardSize);
				int height = blueCardsHeight - (blueSpacing + cardSize) * row;
				card.setPosition(width, height);
				card.addAction(sequence(
						delay(2*shuffleduration + shuffledelayduration),
						moveTo(shuffleWidth, shuffleHeightBlue, shuffleduration, interpolation), 
						delay(shuffledelayduration), 
						moveTo(width, height, shuffleduration, interpolation)));
				blueCards.add(card);
			}
			card.setSize(cardSize, cardSize);
			stage.addActor(card);
		}
		
		Gdx.input.setInputProcessor(stage);
		scene = Scene.SHUFFLING;
	}
	
	@Override
	public void render(float delta)
	{
		super.render(delta);
		switch (scene)
		{
		case SHUFFLING:
			renderShuffling(delta);
			break;
		case GENERATING_TARGET_NUMBER:
			break;
		case SELECTING:
			break;
		default:
			break;
		
		}
	}

	private void renderShuffling(final float delta)
	{
		if (blueCards.get(0).getActions().size == 0)
		{
			for (final TextButton card : redCards)
			{
				card.addListener(new CardClickListener(card, true));
			}
			for (TextButton card : blueCards)
			{
				card.addListener(new CardClickListener(card, false));
			}
			scene = Scene.SELECTING;
		}

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
