package com.simplyapped.calculate.screen.stageintro;

import static com.badlogic.gdx.math.Interpolation.circle;
import static com.badlogic.gdx.math.Interpolation.pow5;
import static com.badlogic.gdx.math.Interpolation.swingOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.run;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.DelayAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.tablelayout.Cell;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.spinner.NumberSpinnerTable;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class StageIntroScreen extends DefaultScreen
{
	private static final int PAUSE_BEFORE_TRANSITION = 4;

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
				int number = isCardRed ? state.selectBigNumber() : state.selectSmallNumber();
				selectedNumbers.add(number);
				TextButton button = new TextButton(number + "", skin, styleName);
				button.setPosition(card.getX(), card.getY());
				button.setSize(CARD_SIZE, CARD_SIZE);
				card.setVisible(false);
				int count = state.cardsLeftForUserSelect();
				String format = count > 1 ? "Select %s Cards" : "Select %s Card";
				title.setText(String.format(format, count));
				
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
	private GameState state;

	private Skin skin;
	private List<TextButton> redCards = new ArrayList<TextButton>();
	private List<TextButton> blueCards = new ArrayList<TextButton>();
	public static float CARD_SIZE;
	private Scene scene;
	private float finishWait;
	private Cell<?> titleCell;
	private List<Integer> selectedNumbers;
	private Table targetTable;
	private NumberSpinnerTable spinner;
	private boolean justSwitchAlready;
	
	public StageIntroScreen(DefaultGame game)
	{
		super(game);
		skin = game.getAssets().get("data/stageintroscreen.json");
	}

	@Override
	public void show()
	{
		justSwitchAlready = false;
		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, true);
		CARD_SIZE = stage.getWidth() / 6;
		state = GameStateFactory.getInstance();
		redCards = new ArrayList<TextButton>();
		blueCards = new ArrayList<TextButton>();
		state.resetCurrentGameInfo();
		selectedNumbers = new ArrayList<Integer>();
		
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
		title.getStyle().background = skin.getDrawable("title");
		titleCell = back.add(title).expandX().fillX().center().top().pad(stage.getHeight()/8f);
		back.row();
		targetTable = new Table();
		back.add(targetTable).expandX().fillX().center().top().pad(stage.getHeight()/8f);
		back.row();
		back.row();
		back.add().expand().fill();
		
		stage.addListener(new ClickListener(){
			@Override
			public synchronized void clicked(InputEvent event, float x, float y)
			{
				if (scene == Scene.SHUFFLING)
				{
					for(TextButton card : redCards)
					{
						finishCardActions(card);
					}
					for(TextButton card : blueCards)
					{
						finishCardActions(card);
					}
				}
				if (spinner != null && scene == Scene.FINISHED)
				{
					if (spinner.isSpinning())
					{
						spinner.finishSpinning();
						justSwitchAlready = true;
					}
				}
			}

			private void finishCardActions(TextButton card)
			{
				for (Action action : card.getActions())
				{
					if (action != null && action instanceof SequenceAction)
					{
						Array<Action> actions = ((SequenceAction)action).getActions();
						for (Action a : actions)
						{
							if (a instanceof TemporalAction)
							{
								((TemporalAction)a).finish();
							}
							else if(a instanceof DelayAction)
							{
								((DelayAction)a).finish();
							}
						}
					}
				}
			}
		});
		
		Gdx.input.setInputProcessor(stage);
		scene = Scene.SHUFFLING;
		finishWait = 0;
		addCardActions();
	}

	private void addCardActions() {
		
		
		//cards
		final int redCardsHeight = (int) (stage.getHeight()/1.5f);
		final int blueCardsHeight = (int) (stage.getHeight()/2.2f);
		final int redSpacing = (int) (CARD_SIZE/2.7f);
		final int blueSpacing = (int) (CARD_SIZE/2.2f);
		final float redMargin = (stage.getWidth() - (3 * redSpacing) - (4 * CARD_SIZE))/2; // 4 cards, 3 spaces
		final float blueMargin = (stage.getWidth() - (2 * blueSpacing) - (3 * CARD_SIZE))/2; // 3 cards, 2 spaces

		final float shuffleWidth = stage.getWidth() / 2 - CARD_SIZE / 2;
		final float shuffleHeightRed = redCardsHeight + CARD_SIZE/2;
		final float shuffleHeightBlue = blueCardsHeight - CARD_SIZE;
		
		final float shuffleduration = 0.5f;
		final float shuffledelayduration = 0.1f;
		final Interpolation interpolation = circle;
		
		for (int cardindex = 0; cardindex < 13; cardindex++)
		{
			TextButton card;
			if (cardindex <= 3)
			{
				card = new TextButton("", skin, "cardbackred");
				float width = redMargin + (cardindex * redSpacing ) + (cardindex * CARD_SIZE);
				card.setPosition(width, redCardsHeight );
				card.addAction(sequence(
						delay(0.8f),
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
				float width = blueMargin + ((blueCardIndex % 3) * blueSpacing) + ((blueCardIndex % 3) * CARD_SIZE);
				float height = blueCardsHeight - (blueSpacing + CARD_SIZE) * row;
				card.setPosition(width, height);
				if (blueCardIndex == 0)
				{
					SequenceAction action = sequence(
							delay(2*shuffleduration + shuffledelayduration),
							moveTo(shuffleWidth, shuffleHeightBlue, shuffleduration, interpolation), 
							delay(shuffledelayduration), 
							moveTo(width, height, shuffleduration, interpolation),
							run(new Runnable(){
								public void run()
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
							}));
					
					card.addAction(action);
				}
				else
				{
					card.addAction(sequence(
							delay(2*shuffleduration + shuffledelayduration),
							moveTo(shuffleWidth, shuffleHeightBlue, shuffleduration, interpolation), 
							delay(shuffledelayduration), 
							moveTo(width, height, shuffleduration, interpolation)));
				}
				blueCards.add(card);
			}
			
			card.setSize(CARD_SIZE, CARD_SIZE);
			stage.addActor(card);
		}
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
		if (finishWait > StageIntroScreen.PAUSE_BEFORE_TRANSITION || justSwitchAlready)
		{
			game.transitionTo(CalculateGame.GAME_SCREEN, TransitionFixtures.Fade());
		}
			
	}

	private void renderShuffling(final float delta)
	{
		// do nothing whilst the runnable attached to the Action in one of the blue cards runs
//		Gdx.app.log("DELTA", delta + "");
	}

	private void renderGeneratingTargetNumber(float delta)
	{
		synchronized(this)
		{
			titleCell.pad(stage.getWidth()/5f);
			
			title.getStyle().background = skin.getDrawable("title");
			title.setText("\nYour\n\nTarget\n\nNumber\n ");
			title.setVisible(true);
			title.getColor().a = 0;
			title.addAction(fadeIn(1));
			
			int[] nums = new int[selectedNumbers.size()];
			for (int i = 0; i < nums.length; i++)
			{
				nums[i] = selectedNumbers.get(i);
			}
			Equation eq = new Equation(nums);
			int attempts = 0;
			// try and find a number between min and max range using selected numbers
			// that doesn't use any of the cards (because then the answer would just be a single card)
			// have 1000 attempts then just give up and use the last one
			while ((eq.getTotal() < state.getCurrentLevelInfo().getMinRange() || 
					eq.getTotal() > state.getCurrentLevelInfo().getMaxRange() ||
					state.getBigCards().contains(eq.getTotal()) || 
					state.getSmallCards().contains(eq.getTotal()) ||
					eq.isSimple()) 
						&& attempts++ < 1000)
			{
				eq = new Equation(nums);
			}
			GameStateFactory.getInstance().setCurrentEquation(eq);
			
			int targetNumber = eq.getTotal();
			Gdx.app.log("target", targetNumber+"");
			TextureAtlas atlas = game.getAssets().get(CalculateGame.NUMBER_STRIP_ALTAS);
			AtlasRegion region = atlas.findRegion(CalculateGame.NUMBER_STRIP_REGION);
			spinner = new NumberSpinnerTable(region, targetNumber, swingOut, 3, 0.2f);
			spinner.setPosition(stage.getWidth()/2 - spinner.getWidth()/2, stage.getHeight()/5f);
			stage.addActor(spinner);
			scene = Scene.FINISHED;
		}
	}

	private void renderDismissCards(float delta)
	{
		title.setVisible(false);
		synchronized (this)
		{
			for (TextButton card : redCards)
			{
				float xPos = card.getX() < stage.getWidth()/2 ? -stage.getWidth() : stage.getWidth() * 2; 
				card.addAction(moveTo(xPos, stage.getHeight(), 1, pow5));
			}
			for (TextButton card : blueCards)
			{
				float xPos = card.getX() < stage.getWidth()/2 ? -stage.getWidth() : stage.getWidth() * 2; 
				card.addAction(moveTo(xPos, 0, 1, pow5));
			}
			scene = Scene.GENERATING_TARGET_NUMBER;
		}
	}
}
