package com.simplyapped.calculate.screen.stageintro;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.tablelayout.Cell;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.flat.FlatUI;
import com.simplyapped.libgdx.ext.scene2d.spinner.NumberSpinner;
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
				selectedNumbers.add(number);
				TextButton button = new TextButton(number + "", skin, styleName);
				button.setPosition(card.getX(), card.getY());
				button.setSize(CARD_SIZE, CARD_SIZE);
				card.setVisible(false);
				int count = StageIntroScreen.this.state.cardsLeftForUserSelect();
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

	private Skin skin = new Skin(Gdx.files.internal("data/stageintroscreen.json"));
	private List<TextButton> redCards = new ArrayList<TextButton>();
	private List<TextButton> blueCards = new ArrayList<TextButton>();
	public final static int CARD_SIZE = CalculateGame.SCREEN_WIDTH / 6;
	private Scene scene;
	private float finishWait;
	private Cell<?> titleCell;
	private List<Integer> selectedNumbers;
	private Table targetTable;
	
	public StageIntroScreen(DefaultGame game)
	{
		super(game);
	}

	@Override
	public void show()
	{
		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, false);
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
		TextureRegionDrawable backstyle = FlatUI.CreateBackgroundDrawable(0.2f, 0.2f, 0.2f, 0.9f, title.getWidth(), title.getHeight());
		disposables.add(backstyle.getRegion().getTexture());
		title.getStyle().background = backstyle;
		titleCell = back.add(title).expandX().fillX().center().top().pad(CalculateGame.SCREEN_HEIGHT/10f);
		back.row();
		targetTable = new Table();
		back.add(targetTable).expandX().fillX().center().top().pad(CalculateGame.SCREEN_HEIGHT/10f);
		back.row();
		back.row();
		back.add().expand().fill();

		//cards
		final int redCardsHeight = (int) (CalculateGame.SCREEN_HEIGHT/1.5f);
		final int blueCardsHeight = (int) (CalculateGame.SCREEN_HEIGHT/2.2f);
		final int redSpacing = (int) (CARD_SIZE/2.7f);
		final int blueSpacing = (int) (CARD_SIZE/2.2f);
		final int redMargin = (CalculateGame.SCREEN_WIDTH - (3 * redSpacing) - (4 * CARD_SIZE))/2; // 4 cards, 3 spaces
		final int blueMargin = (CalculateGame.SCREEN_WIDTH - (2 * blueSpacing) - (3 * CARD_SIZE))/2; // 3 cards, 2 spaces

		final int shuffleWidth = CalculateGame.SCREEN_WIDTH / 2 - CARD_SIZE / 2;
		final int shuffleHeightRed = redCardsHeight + CARD_SIZE/2;
		final int shuffleHeightBlue = blueCardsHeight - CARD_SIZE;
		
		final float shuffleduration = 0.5f;
		final float shuffledelayduration = 0.1f;
		final Interpolation interpolation = circle;
		Gdx.app.log("asfd", "show called");
		
		for (int cardindex = 0; cardindex < 13; cardindex++)
		{
			TextButton card;
			if (cardindex <= 3)
			{
				card = new TextButton("", skin, "cardbackred");
				int width = redMargin + (cardindex * redSpacing ) + (cardindex * CARD_SIZE);
				card.setPosition(width, redCardsHeight );
				card.addAction(sequence(
						delay(0.5f),
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
				int width = blueMargin + ((blueCardIndex % 3) * blueSpacing) + ((blueCardIndex % 3) * CARD_SIZE);
				int height = blueCardsHeight - (blueSpacing + CARD_SIZE) * row;
				card.setPosition(width, height);
				if (blueCardIndex == 0)
				{
					card.addAction(sequence(
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
							})));
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
		
		
		Gdx.input.setInputProcessor(stage);
		scene = Scene.SHUFFLING;
		finishWait = 0;
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
		if (finishWait > 6)
		{
			game.transitionTo(CalculateGame.GAME_SCREEN, TransitionFixtures.Fade());
		}
			
	}

	private void renderShuffling(final float delta)
	{
		// do nothing whilst the runnable attached to the Action in one of the blue cards runs
	}

	private void renderGeneratingTargetNumber(float delta)
	{
		synchronized(this)
		{
			titleCell.pad(CalculateGame.SCREEN_WIDTH/5f);
			
			TextureRegionDrawable back = FlatUI.CreateBackgroundDrawable(0.2f, 0.2f, 0.2f, 0.9f, title.getWidth(), title.getHeight());
			disposables.add(back.getRegion().getTexture());
			title.getStyle().background = back;
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
			while (eq.getTotal() < 20 || eq.getTotal() > 99999)
			{
				eq = new Equation(nums);
			}
			GameStateFactory.getInstance().setCurrentEquation(eq);
			
			int targetNumber = eq.getTotal();
			Gdx.app.log("target", targetNumber+"");
			
			Table spinner = NumberSpinner.createNumberSpinnerTable(targetNumber, swingOut, 3, 0.2f);
			spinner.setPosition(CalculateGame.SCREEN_WIDTH/2 - spinner.getWidth()/2, CalculateGame.SCREEN_HEIGHT/5f);
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
				int xPos = card.getX() < CalculateGame.SCREEN_WIDTH/2 ? -CalculateGame.SCREEN_WIDTH : CalculateGame.SCREEN_WIDTH * 2; 
				card.addAction(moveTo(xPos, CalculateGame.SCREEN_HEIGHT, 1, pow5));
			}
			for (TextButton card : blueCards)
			{
				int xPos = card.getX() < CalculateGame.SCREEN_WIDTH/2 ? -CalculateGame.SCREEN_WIDTH : CalculateGame.SCREEN_WIDTH * 2; 
				card.addAction(moveTo(xPos, 0, 1, pow5));
			}
			scene = Scene.GENERATING_TARGET_NUMBER;
		}
	}
}
