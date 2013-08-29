package com.simplyapped.calculate.screen.levelintro;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class StageIntroScreen extends DefaultScreen
{
	private final class CardClickListener extends ClickListener
	{
		private final TextButton card;
		private boolean isCardRed;

		private CardClickListener(TextButton card, boolean isCardRed)
		{
			this.card = card;
			this.isCardRed = isCardRed;
		}

		@Override
		public void clicked(InputEvent event, float x, float y)
		{
			if (StageIntroScreen.this.scene == Scene.SELECTING) // only respond to events during the Selecting Numbers stage
			{
				String styleName = isCardRed ? "cardfrontred" : "cardfrontblue";
				int number = isCardRed ? StageIntroScreen.this.state.selectBigNumber() : StageIntroScreen.this.state.selectSmallNumber();
				TextButton button = new TextButton(number + "", skin, styleName);
				button.setPosition(card.getX(), card.getY());
				button.setSize(cardSize, cardSize);
				card.setVisible(false);
				title.setText(String.format("Select %s Cards", StageIntroScreen.this.state.cardsLeftForUserSelect()));
				if (StageIntroScreen.this.state.cardsLeftForUserSelect() == 0)
				{
					StageIntroScreen.this.scene = Scene.DISMISS_CARDS;
				}
				if (isCardRed)
				{
					redCards.add(button);
				}
				else
				{
					blueCards.add(button);
				}
				stage.addActor(button);
			}
		}
	}

	enum Scene
	{
		SHUFFLING,
		SELECTING,
		DISMISS_CARDS,
		GENERATING_TARGET_NUMBER, 
		FINISHED
	}
	
	private Label title;
	private Label targetTitle;
	private Label target;
	private GameState state = GameStateFactory.getInstance();

	private Skin skin = new Skin(Gdx.files.internal("data/stageintroscreen.json"));
	private List<TextButton> redCards = new ArrayList<TextButton>();
	private List<TextButton> blueCards = new ArrayList<TextButton>();
	final int cardSize = CalculateGame.SCREEN_WIDTH / 6;
	private Scene scene;
	private float finishWait;
	
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
		
		title = new Label(String.format("Select %s Cards", StageIntroScreen.this.state.cardsLeftForUserSelect()), skin, "title");
		title.setAlignment(Align.center);
		back.add(title).expandX().fillX().center().top().pad(CalculateGame.SCREEN_HEIGHT/10f);
		back.row();
		targetTitle = new Label("Target Number", skin, "targetTitle");
		targetTitle.setAlignment(Align.center);
		targetTitle.setVisible(false);
		back.add(targetTitle).expandX().fillX().center().top().pad(CalculateGame.SCREEN_HEIGHT/10f);
		back.row();
		target = new Label("354", skin, "target");
		target.setAlignment(Align.center);
		target.setFontScale(2f);
		target.setVisible(false);
		back.add(target).expandX().fillX().center().top().pad(CalculateGame.SCREEN_HEIGHT/10f);
		back.row();
		back.add().expand().fill();
		back.debug();

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
		case DISMISS_CARDS:
			renderDismissCards(delta);
			break;
		case GENERATING_TARGET_NUMBER:
			renderGeneratingTargetNumber(delta);
			break;
		case FINISHED:
			renderFinished(delta);
			break;
		default:
			break;			
		}
//		Table.drawDebug(stage);
	}

	private void renderFinished(float delta)
	{
		finishWait += delta;
		if (finishWait > 1)
		{
			game.transitionTo(CalculateGame.GAME_SCREEN, TransitionFixtures.Fade());
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

	private void renderGeneratingTargetNumber(float delta)
	{
		title.setText("Your Target Number");
		title.setVisible(true);
		title.getColor().a = 0;
		title.addAction(fadeIn(1));
		scene = Scene.FINISHED;
	}

	private void renderDismissCards(float delta)
	{
		title.setVisible(false);
		synchronized (this)
		{
			for (TextButton card : redCards)
			{
				int xPos = card.getX() < CalculateGame.SCREEN_WIDTH/2 ? -CalculateGame.SCREEN_WIDTH : CalculateGame.SCREEN_WIDTH * 2; 
				card.addAction(moveTo(xPos, CalculateGame.SCREEN_HEIGHT, 2, pow5));
			}
			for (TextButton card : blueCards)
			{
				int xPos = card.getX() < CalculateGame.SCREEN_WIDTH/2 ? -CalculateGame.SCREEN_WIDTH : CalculateGame.SCREEN_WIDTH * 2; 
				card.addAction(moveTo(xPos, 0, 2, pow5));
			}
			scene = Scene.GENERATING_TARGET_NUMBER;
		}
	}
}
