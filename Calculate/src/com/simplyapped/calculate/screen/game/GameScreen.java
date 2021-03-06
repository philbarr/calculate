package com.simplyapped.calculate.screen.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.tablelayout.Cell;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.numbers.EquationElement;
import com.simplyapped.calculate.numbers.NonIntegerDivisionException;
import com.simplyapped.calculate.numbers.Operator;
import com.simplyapped.calculate.screen.actor.CalculationTable;
import com.simplyapped.calculate.screen.game.actor.EquationElementFlatUIButton;
import com.simplyapped.calculate.screen.game.actor.EquationElementTextButton;
import com.simplyapped.calculate.screen.game.actor.RowEquationElementTextButton;
import com.simplyapped.calculate.screen.stageintro.StageIntroScreen;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.calculate.state.LevelDetails;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class GameScreen extends DefaultScreen
{
	private class CardCapableCalculationTable extends CalculationTable
	{
		public CardCapableCalculationTable(float screenWidth, float screenHeight) {
			super(screenWidth, screenHeight);
		}

		@Override
		protected Actor createTotalActor(int row, Equation total) {
			if (textButtonTotalLines.contains(row))
		    {
				RowEquationElementTextButton button = new RowEquationElementTextButton(total.getTotal() + "", cards, "cardfrontgreen");
				button.setData(total.getTotal());
				button.setRow(row);
				button.setWidth(100);
				button.addListener(new OperandListener());
				return button;
		    }
			else
			{
				return super.createTotalActor(row, total);
			}
		}
		
		@Override
		protected void padTotalCell(Cell<?> cell, int row) {
			if (textButtonTotalLines.contains(row))
		    {
				cell.height(StageIntroScreen.CARD_SIZE / 2).pad(20);
				cell.minWidth(140).padRight(40).padTop(20).top().fillX();
				cell.maxWidth(140);
				
		    }
			else
			{
				super.padTotalCell(cell,row);
			}
		}
	}
	
	private class OperatorClickListener extends ClickListener
	{
		@Override
		public void clicked(InputEvent event, float x, float y)
		{
			EquationElementFlatUIButton button = (EquationElementFlatUIButton) event.getListenerActor();
			if (isOperatorToSelectNext) // can select an operator
			{
				calculationTable.addElement(button.getData());
				isOperatorToSelectNext = false;
			}
			else
			{
				// when the user has changed their mind and wants to choose a different operator
				if (calculationTable.size()>0 && calculationTable.lastLine().size() >= 2) 
				{
					calculationTable.lastLine().remove(calculationTable.lastLine().size()-1);
					calculationTable.addElement(button.getData());
					isOperatorToSelectNext = false;
				}
			}
			calculationTable.update();
		}
	}
	
	private class OperandListener extends ClickListener
	{
		@Override
		public void clicked(InputEvent event, float x, float y)
		{
			EquationElementTextButton button = (EquationElementTextButton) event.getListenerActor();
			Equation number = new Equation(button.getData());

			if (!isOperatorToSelectNext) // can select an operand
			{
				try
				{
					GameState state = GameStateFactory.getInstance();
					// check that the new number won't cause a NonIntegerDivisionException
					// and check if user has won
					if (calculationTable.size() > 0)
					{
						List<EquationElement> lastLine = new ArrayList<EquationElement>(calculationTable.lastLine());
						lastLine.add(number);
						Equation equation = new Equation(lastLine); // auto-checks for NonIntegerDivisionException
						if (equation.getTotal() == state.getCurrentEquation().getTotal())
						{
							if (state.getCurrentLevelInfo().isUseAllCards() && !allCardsUsed())
							{
								showDialogMessage("At this Level you must use\n ALL your cards");
							}
							else
							{
								final LevelDetails current = state.getLevelDetails(state.getCurrentLevel());
								current.increaseCompleted();
								
								// unlock next stage if they've got that far
								if (state.getCurrentLevelInfo().getCompletedRequired() == current.getCompleted())
								{
									int level = state.getCurrentLevel()+1;
									LevelDetails levelDetails = state.getLevelDetails(level);
									levelDetails.setLocked(false);
									state.saveLevelDetails(level, levelDetails);
								}
								state.saveLevelDetails(state.getCurrentLevel(), current);
								game.transitionTo(CalculateGame.WINNER_SCREEN, TransitionFixtures.Fade());
							}
						}
					}
					else if (number.getTotal() == state.getCurrentEquation().getTotal()) // check for the case when the first number added is the answer
					{
						game.transitionTo(CalculateGame.WINNER_SCREEN, TransitionFixtures.Fade());
					}
					calculationTable.addElement(number);
					button.setVisible(false);
					isOperatorToSelectNext = true;
					greenCardCheck(button);
				} 
				catch (NonIntegerDivisionException e)
				{
					showDialogMessage("Whole Numbers Only!\nNo Fractions Allowed!");
					List<EquationElement> lastLine = calculationTable.lastLine();
					lastLine.remove(lastLine.size()-1); // remove the offending divide
					isOperatorToSelectNext = true;
					
				}
			}
			else // deal with the case where the user wants to start a new line
			{
				if (calculationTable.lastLine().size() > 1)
				{	
					if (button instanceof RowEquationElementTextButton)
					{
						textButtonTotalLines.remove(((RowEquationElementTextButton)button).getRow());
					}
					else
					{
						textButtonTotalLines.add(calculationTable.size());
					}
					calculationTable.addElementLine(number);
					button.setVisible(false);
				}
			}
			calculationTable.update();
		}

		private void greenCardCheck(EquationElementTextButton button) {
			if (button instanceof RowEquationElementTextButton)
			{
				textButtonTotalLines.remove(((RowEquationElementTextButton)button).getRow());
			}
		}

	}
	private boolean allCardsUsed()
	{
		for (TextButton button : cardButtons)
		{
			if(button.isVisible())
			{
				return false;
			}
		}
		return true;
	}

	
	private float totalTime;
	private Table window;
	private Skin skin;
	private Skin cards;
	private Set<Integer> textButtonTotalLines = new HashSet<Integer>();
	private CalculationTable calculationTable;
	private List<TextButton> cardButtons = new ArrayList<TextButton>();
	private boolean isOperatorToSelectNext;
	
	private Label timerLabel;
	private boolean isTransitioning;
	private boolean isPaused;

	public GameScreen(DefaultGame game)
	{
		super(game);
		skin = game.getAssets().get(("data/gamescreen.json"));
		cards = game.getAssets().get("data/stageintroscreen.json"); // because in future we need to think more carefully about how we group assets
	}

	private void showDialogMessage(final String message)
	{
		final Dialog dialog = createDialog(message);
		
		TextButton okButton = new TextButton("Ok", skin, "green");
		dialog.getButtonTable().defaults().pad(15f).width(stage.getWidth()/3.5f).padBottom(45f);
		dialog.getButtonTable().add(okButton);
		okButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialog.remove();
			}
		});
		stage.addActor(dialog);
	}

	private Dialog createDialog(String message)
	{
		Dialog dialog = new Dialog("", skin, "dialog");
		dialog.setSize(stage.getWidth()/1.1f, stage.getHeight()/2f);
		dialog.setPosition(((stage.getWidth()-dialog.getWidth())/2), ((stage.getHeight()-dialog.getHeight())/2));
		LabelStyle labelStyle = skin.get("dialog", LabelStyle.class);
		Label details = new Label(message, labelStyle);
		dialog.getContentTable().add(details);
		dialog.getButtonTable().defaults().pad(15f).width(stage.getWidth()/3.5f).padBottom(45f);
		return dialog;
	}

	@Override
	public void show()
	{
		isTransitioning = false;
		textButtonTotalLines = new HashSet<Integer>();

		GameState state = GameStateFactory.getInstance();
		totalTime = state.getCurrentLevelInfo().getTimeLimit();

		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, true);
		float panelwidth = stage.getWidth()/1.05f; // used for the width of the operatorsTable, titleTable, and numbersTable
	    stage.addListener(new ClickListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if (keycode == Keys.BACK || keycode == Keys.BACKSPACE)
				{
					showQuitDialog();
					return true;
				}
				return false;
			}
		});
	    window = new Table();	    
	    window.setFillParent(true);
	    
	    calculationTable = new CardCapableCalculationTable(stage.getWidth(), stage.getHeight());
		calculationTable.debug();
	    calculationTable.setPanelWidth(panelwidth);
	    calculationTable.setPanelHeight(stage.getHeight()/3f);

	    Table operatorsTable = new Table();
		operatorsTable.row().expand().top().fill().pad(5);
	    operatorsTable.add(getOperatorButton(Operator.PLUS));
	    operatorsTable.add(getOperatorButton(Operator.MINUS));
	    operatorsTable.add(getOperatorButton(Operator.MULTIPLY));
	    operatorsTable.add(getOperatorButton(Operator.DIVIDE));
	    operatorsTable.add().width(20f).expand(false, false);
	    operatorsTable.add(getCEButton());
		operatorsTable.setWidth(panelwidth);                                                                                               
	    operatorsTable.setHeight(stage.getHeight()/11f);
	    operatorsTable.setPosition(stage.getWidth()/2 - panelwidth/2, stage.getHeight() - stage.getHeight()/1.8f);
	    operatorsTable.setBackground(skin.getDrawable("buttonborder"));
	    
	    Table operandsTable = new Table();
	    int operandsPadding = 10;
	    operandsTable.row();
	    int index = 0;
	    for (int bigCard : state.getBigCards())
		{
			EquationElementTextButton button = new EquationElementTextButton(bigCard + "", cards, "cardfrontred");
			button.setData(bigCard);
			button.addListener(new OperandListener());
			index++;
			operandsTable.add(button).align(Align.center).expandX().fillX().size(StageIntroScreen.CARD_SIZE, StageIntroScreen.CARD_SIZE).pad(operandsPadding);
			cardButtons.add(button);
		}
	    for (int smallCard : state.getSmallCards())
		{
	    	if (index++ % 4 == 0)
	    	{
	    		operandsTable.row();
	    	}
	    	EquationElementTextButton button = new EquationElementTextButton(smallCard + "", cards, "cardfrontblue");
	    	button.setData(smallCard);
			button.addListener(new OperandListener());
			operandsTable.add(button).align(Align.center).expandX().fillX().size(StageIntroScreen.CARD_SIZE, StageIntroScreen.CARD_SIZE).pad(operandsPadding);
			cardButtons.add(button);
		}
	    
	    operandsTable.setPosition(stage.getWidth()/2 - panelwidth/2 - operandsPadding, stage.getHeight() - stage.getHeight()/1.3f);
	    operandsTable.setWidth(panelwidth + operandsPadding*2);
	    operandsTable.setBackground(skin.getDrawable("buttonborder"));
	    
	    window.setBackground(skin.getDrawable("gamescreenbackground"));

	    // title labels
	    Table titleTable = new Table();
	    titleTable.setBackground(skin.getDrawable("titlebackground"));
	    titleTable.setWidth(stage.getWidth());
	    
	    float titleBackgroundHeight = stage.getHeight() / 11f;
		titleTable.setHeight(titleBackgroundHeight);
	    titleTable.setPosition(0, stage.getHeight() - titleTable.getHeight());
	    
	    // target label
	    Label targetLabel = new Label("Target: " + GameStateFactory.getInstance().getCurrentEquation().getTotal(), skin, "title");
	    targetLabel.setAlignment(Align.left, Align.bottom);
	    targetLabel.setHeight(titleBackgroundHeight);
	    float pad = -3f;
	    targetLabel.setWidth(stage.getWidth()/2 - pad);
	    float labelHeight = stage.getHeight() - titleBackgroundHeight;
		targetLabel.setPosition((stage.getWidth() - panelwidth)/2 - pad, labelHeight);
		
		// timer label
	    timerLabel = new Label("Time:   ", skin, "title");
	    timerLabel.setPosition(((stage.getWidth() - panelwidth)/2) + panelwidth - timerLabel.getWidth() + pad, labelHeight);
	    timerLabel.setHeight(titleBackgroundHeight);
	    timerLabel.setWidth(stage.getWidth()/2);
	    timerLabel.setAlignment(Align.left,Align.bottom);
	    
	    stage.addActor(window);
	    stage.addActor(calculationTable.getPanel());
	    stage.addActor(titleTable);
	    stage.addActor(timerLabel);
	    stage.addActor(targetLabel);
	    stage.addActor(operatorsTable);
	    stage.addActor(operandsTable);
	    
	    resetGame();
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

	protected void showQuitDialog()
	{
	
		// create the dialog
		final Dialog dialog = createDialog("Are you sure you\nwant to quit?");
		
		TextButton quitButton = new TextButton("Quit", skin, "red");
		TextButton playOnButton = new TextButton("Play On", skin, "green");
		TextButton viewSolutionButton = new TextButton("View\nSolution", skin, "green");
		
		dialog.getButtonTable().add(quitButton);
		dialog.getButtonTable().add(playOnButton);
		dialog.getButtonTable().row();
		dialog.getButtonTable().add(viewSolutionButton).colspan(2);
		
		playOnButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				dialog.remove();
			}
		});
		
		quitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.transitionTo(CalculateGame.STAGE_SELECT_SCREEN, TransitionFixtures.UnderlapRight());
			}
		});
		
		viewSolutionButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				GameState state = GameStateFactory.getInstance();
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
		
		stage.addActor(dialog);
	}

	private void resetGame()
	{
		calculationTable.reset();
		for(TextButton b : cardButtons)
		{
			b.setVisible(true);
		}
		textButtonTotalLines = new HashSet<Integer>();
		isOperatorToSelectNext = false;
	}

	private Actor getCEButton()
	{
		TextButton button = new TextButton("CE", skin, "ce");
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				resetGame();
			}
		});
		return button;
	}

	private Actor getOperatorButton(Operator operator)
	{
		EquationElementFlatUIButton button = new EquationElementFlatUIButton(operator.toString(), skin, "operator");
		button.setData(operator);
		button.addListener(new OperatorClickListener());
		return button;
	}




	
	@Override
	public void render(float delta)
	{
		super.render(delta);
//		Table.drawDebug(stage);
		// update time
		if (timerLabel != null && !isTransitioning && !isPaused)
		{
			int time = (int)(totalTime-=delta);
			if (time <= 0)
			{
				isTransitioning = true;
				game.transitionTo(CalculateGame.LOSER_SCREEN, TransitionFixtures.Fade());
			}
			
			String timeStr = String.valueOf(time);
			if (timeStr.toCharArray().length == 1)
			{
				timeStr = " " + timeStr;
			}
			timerLabel.setText("Time: " + timeStr);
		}
	}



	@Override
	public void pause()
	{
		isPaused = true;
	}
	
	@Override
	public void resume()
	{
		isPaused = false;
	}
	

}
