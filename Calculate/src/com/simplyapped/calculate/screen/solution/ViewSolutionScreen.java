package com.simplyapped.calculate.screen.solution;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.numbers.EquationElement;
import com.simplyapped.calculate.screen.actor.CalculationTable;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.spinner.NumberSpinnerTable;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class ViewSolutionScreen extends DefaultScreen
{
	private Skin skin;
	private GameState state;
	private CalculationTable calculationTable;
	private Table window;

	public ViewSolutionScreen(DefaultGame game)
	{
		super(game);
		skin = game.getAssets().get("data/gamescreen.json");
	}

	@Override
	public void show()
	{
		state = GameStateFactory.getInstance();
		
		window = new Table();	    
	    window.setFillParent(true);
	    window.setBackground(skin.getDrawable("gamescreenbackground"));
		
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

		float panelwidth = CalculateGame.SCREEN_WIDTH/1.05f; 
		calculationTable = new CalculationTable();
	    calculationTable.setPanelWidth(panelwidth);
	    calculationTable.setPanelHeight(CalculateGame.SCREEN_HEIGHT/1.9f);
	    
	    List<Equation> currentEquation = state.getCurrentEquation().getEquationConstruction();
	    
	    for (Equation eq : currentEquation)
		{
			for (EquationElement element : eq.getElements())
			{
				if (element instanceof Equation && ((Equation)element).getOperandCount() > 1)
				{
					calculationTable.addElement(new Equation(((Equation)element).getTotal()));
				}
				else
				{
					calculationTable.addElement(element);
				}
			}
			calculationTable.newLine();
		}
	    calculationTable.removeLine();
	    calculationTable.update();
	    
		int total = GameStateFactory.getInstance().getCurrentEquation().getTotal();
		TextureAtlas atlas = game.getAssets().get(CalculateGame.NUMBER_STRIP_ALTAS);
		AtlasRegion region = atlas.findRegion(CalculateGame.NUMBER_STRIP_REGION);
		NumberSpinnerTable numberTable = new NumberSpinnerTable(region, Math.abs(total), Interpolation.pow3Out, 2, 0.2f);
		numberTable.setPosition(CalculateGame.SCREEN_WIDTH/2 - numberTable.getWidth()/2, CalculateGame.SCREEN_HEIGHT/4.1f - numberTable.getHeight()/2);
		
		TextButton button = new TextButton("I Knew That!", skin, "green");
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				game.transitionTo(CalculateGame.STAGE_SELECT_SCREEN, TransitionFixtures.UnderlapRight());
			}
		});
	    float buttonHeight = CalculateGame.SCREEN_HEIGHT / 7;
	    float buttonWidth = CalculateGame.SCREEN_WIDTH / 2f;
	    button.setSize(buttonWidth, buttonHeight);
		button.setPosition(CalculateGame.SCREEN_WIDTH/2 - button.getWidth()/2, CalculateGame.SCREEN_HEIGHT/15f);
		
		stage.addActor(window);
		stage.addActor(numberTable);
	    stage.addActor(calculationTable.getPanel());
	    stage.addActor(button);
	    
	    state.setViewingSolution(false); // we are now showing the solution, so we can safely stop worrying about if we need to show it
	    state.decreaseSolutions();
	    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}
}
