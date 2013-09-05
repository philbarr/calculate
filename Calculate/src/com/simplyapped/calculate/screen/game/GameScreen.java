package com.simplyapped.calculate.screen.game;

import java.util.ArrayList;
import java.util.List;

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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.numbers.EquationElement;
import com.simplyapped.calculate.numbers.NonIntegerDivisionException;
import com.simplyapped.calculate.numbers.Operator;
import com.simplyapped.calculate.screen.game.actor.EquationElementFlatUIButton;
import com.simplyapped.calculate.screen.game.actor.EquationElementTextButton;
import com.simplyapped.calculate.screen.levelintro.StageIntroScreen;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.FlatUIButton;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class GameScreen extends DefaultScreen
{
	private class OperatorClickListener extends ClickListener
	{
		@Override
		public void clicked(InputEvent event, float x, float y)
		{
			if (GameScreen.this.isOperatorToSelectNext) // can select an operator
			{
				EquationElementFlatUIButton button = (EquationElementFlatUIButton) event.getListenerActor();
				if (calculationElements.size()>0 &&
					(lastLine().size() > 6 ||
					 (lastLine().size() > 2 && !((Operator)lastLine().get(lastLine().size()-2)).isEquivalent(button.getData()))
					))
				{
					carryLine();
				}
				GameScreen.this.addElement(button.getData());
				GameScreen.this.isOperatorToSelectNext = false;
			}
		}
	}

	private class OperandListener extends ClickListener
	{
		@Override
		public void clicked(InputEvent event, float x, float y)
		{
			if (!GameScreen.this.isOperatorToSelectNext) // can select an operand
			{
				EquationElementTextButton button = (EquationElementTextButton) event.getListenerActor();
				
				try
				{
					Equation newEquation = new Equation(button.getData());

					// check that the new number won't cause a NonIntegerDivisionException
					if (calculationElements.size() > 0)
					{
						List<EquationElement> lastLine = lastLine();
						lastLine.add(newEquation);
						new Equation(lastLine);
					}
					GameScreen.this.addElement(newEquation);
					button.setVisible(false);
					GameScreen.this.isOperatorToSelectNext = true;
				} catch (NonIntegerDivisionException e)
				{
					showNonIntegerDivisionMessage();
					List<EquationElement> lastLine = lastLine();
					lastLine.remove(lastLine.size()-1); // remove the added number
					lastLine.remove(lastLine.size()-1); // remove the offending divide
					isOperatorToSelectNext = true;
					drawCalculationTable();
				}
				
			}
		}

		
	}

	private Table window;
	private Skin skin = new Skin(Gdx.files.internal("data/gamescreen.json"));
	private Skin cards = new Skin(Gdx.files.internal("data/stageintroscreen.json")); // because in future we need to think more carefully about how we group assets
	private ScrollPane calculationPane;
	private Table calculationTable;
	private Texture texture;
	private List<TextButton> cardButtons = new ArrayList<TextButton>();
	private boolean isOperatorToSelectNext;
	private List<List<EquationElement>> calculationElements = new ArrayList<List<EquationElement>>();

	public GameScreen(DefaultGame game)
	{
		super(game);
		Pixmap pix = new Pixmap(1, 1, Format.RGBA4444);
		pix.setColor(0.979f,0.979f,0.979f,0.8f);
		pix.fill();
		texture = new Texture(pix);
		disposables.add(texture);
	}

	private void showNonIntegerDivisionMessage()
	{
		// create the dialog
		Dialog dialog = new Dialog("", skin, "dialog");
		dialog.setSize(CalculateGame.SCREEN_WIDTH/1.1f, CalculateGame.SCREEN_HEIGHT/2f);
		dialog.setPosition(((CalculateGame.SCREEN_WIDTH-dialog.getWidth())/2), ((CalculateGame.SCREEN_HEIGHT-dialog.getHeight())/2));
		LabelStyle labelStyle = skin.get("dialog", LabelStyle.class);
		String text = "Whole Numbers Only!\nNo Fractions Allowed!";
		Label details = new Label(text, labelStyle);
		details.setFontScale(0.7f);
		
		FlatUIButton okButton = new FlatUIButton("Ok", skin, "dialogOk");
		disposables.add(okButton);
		dialog.getContentTable().add(details);
		dialog.getButtonTable().defaults().pad(15f).width(CalculateGame.SCREEN_WIDTH/3.5f).padBottom(45f);
		dialog.getButtonTable().add(okButton);
		
		stage.addActor(dialog);
	}
	
	public void carryLine()
	{
		List<EquationElement> line = calculationElements.get(calculationElements.size()-1);
		Equation eq;
		try
		{
			eq = new Equation(line);
			addElementLine(new Equation(eq.getTotal()));
		} catch (NonIntegerDivisionException e)
		{
			Gdx.app.error(Equation.class.toString(), e.getMessage());
		}
	}

	public void addElement(EquationElement element)
	{
		if (calculationElements.size() == 0) //blank calculation table
		{
			addElementLine(element);
		}
		else
		{
			List<EquationElement> line = calculationElements.get(calculationElements.size()-1);
			line.add(element);
		}
		drawCalculationTable();
	}

	private void addElementLine(EquationElement element)
	{
		List<EquationElement> line = new ArrayList<EquationElement>();
		line.add(element);
		calculationElements.add(line);
	}

	@Override
	public void show()
	{
		float panelwidth = CalculateGame.SCREEN_WIDTH/1.3f; // used for the width of the operatorsTable, titleTable, and numbersTable

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
	    
	    window = new Table();	    
	    window.setFillParent(true);
	    
	    calculationTable = new Table();
	    calculationTable.setBackground(skin.getTiledDrawable("graphtile"));
	    
	    calculationPane = new ScrollPane(calculationTable);
	    calculationPane.getStyle().background = new NinePatchDrawable( skin.getPatch("calcbackground"));
	    calculationPane.setWidth(CalculateGame.SCREEN_WIDTH/1.2f);
	    calculationPane.setHeight(CalculateGame.SCREEN_HEIGHT/3f);
	    calculationPane.setPosition(CalculateGame.SCREEN_WIDTH/2 - calculationPane.getWidth()/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/2.2f);
	    calculationPane.setScrollingDisabled(true, false);
	    calculationTable.debug();
	    
	    Table operatorsTable = new Table();
	    operatorsTable.row().expandX().top().fill().pad(10);
	    operatorsTable.add(getOperatorButton(Operator.PLUS));
	    operatorsTable.add(getOperatorButton(Operator.MINUS));
	    operatorsTable.add(getOperatorButton(Operator.MULTIPLY));
	    operatorsTable.add(getOperatorButton(Operator.DIVIDE));
	    operatorsTable.add().width(20f).expand(false, false).pad(0);
	    operatorsTable.add(getCEButton());
		operatorsTable.setWidth(panelwidth);                                                                                               
	    operatorsTable.setHeight(CalculateGame.SCREEN_HEIGHT/15f);
	    operatorsTable.setPosition(CalculateGame.SCREEN_WIDTH/2 - panelwidth/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/1.8f);
	    operatorsTable.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));
	    
	    Table operandsTable = new Table();
	    operandsTable.row();
	    GameState state = GameStateFactory.getInstance();
	    int index = 0;
	    for (int bigCard : state.getBigCards())
		{
			EquationElementTextButton button = new EquationElementTextButton(bigCard + "", cards, "cardfrontred");
			button.setData(bigCard);
			button.addListener(new OperandListener());
			index++;
			operandsTable.add(button).align(Align.center).expandX().fillX().size(StageIntroScreen.CARD_SIZE, StageIntroScreen.CARD_SIZE).pad(10);
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
			operandsTable.add(button).align(Align.center).expandX().fillX().size(StageIntroScreen.CARD_SIZE, StageIntroScreen.CARD_SIZE).pad(10);
			cardButtons.add(button);
		}
	    
	    operandsTable.setPosition(CalculateGame.SCREEN_WIDTH/2 - panelwidth/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/1.3f);
	    operandsTable.setWidth(panelwidth);
	    operandsTable.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));
	    
	    window.setBackground(skin.getDrawable("gamescreenbackground"));
	    stage.addActor(window);
	    stage.addActor(calculationPane);
	    stage.addActor(operatorsTable);
	    stage.addActor(operandsTable);
	    
	    resetGame();
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

	private void resetGame()
	{
		calculationElements.clear();
		for(TextButton b : cardButtons)
		{
			b.setVisible(true);
		}
		isOperatorToSelectNext = false;
		drawCalculationTable();
	}

	private Actor getCEButton()
	{
		FlatUIButton button = new FlatUIButton("CE", skin, "ce");
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				resetGame();
			}
		});
		disposables.add(button);
		return button;
	}

	private Actor getOperatorButton(Operator operator)
	{
		EquationElementFlatUIButton button = new EquationElementFlatUIButton(operator.toString(), skin, "operator");
		button.setData(operator);
		button.addListener(new OperatorClickListener());
		disposables.add(button);
		return button;
	}

	private void drawCalculationTable()
	{
		calculationTable.clear();
		for(List<EquationElement> line : calculationElements)
		{
			createRow(calculationTable, line);
		}

		calculationPane.setScrollY(calculationTable.getHeight());
	    calculationTable.row();
//	    calculationTable.add().expandY();
	}

	private void createRow(Table calculation, List<EquationElement> line)
	{
		calculation.row();
	    calculation.add(getLabel(line)).expandX().top().left().padLeft(20).padTop(20).fillX();
	    Label actor = new Label("=", skin, "calculation");
	    actor.setAlignment(Align.center);
		calculation.add(actor).width(60).padTop(20).top();
		Equation tempEq = new Equation();
		try
		{
			tempEq.setElements(line);
		} catch (NonIntegerDivisionException e)
		{
			Gdx.app.error(Equation.class.toString(), e.getMessage());
		}
	    calculation.add(new Label(tempEq.getTotal() + "", skin, "calculation")).minWidth(100).padRight(40).padTop(20).top().fillX();
	}
	
	@Override
	public void render(float delta)
	{
		super.render(delta);
		
		Table.drawDebug(stage);
	}

	private Label getLabel(List<EquationElement> line)
	{
		String lineString = "";
		for(EquationElement element : line)
		{
			lineString+=element.toString() + " ";
		}
		Label label = new Label(lineString, skin, "calculation");
		label.setAlignment(Align.left, Align.left);
		return label;
	}

	private List<EquationElement> lastLine()
	{
		return calculationElements.get(calculationElements.size()-1);
	}
}
