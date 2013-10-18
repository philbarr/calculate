package com.simplyapped.calculate.screen.solution;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.numbers.EquationElement;
import com.simplyapped.calculate.screen.actor.CalculationTable;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class ViewSolutionScreen extends DefaultScreen
{
	private Skin skin = new Skin(Gdx.files.internal("data/gamescreen.json"));
	private GameState state;
	private CalculationTable calculationTable;
	private Table window;

	public ViewSolutionScreen(DefaultGame game)
	{
		super(game);
	}

	@Override
	public void show()
	{
		window = new Table();	    
	    window.setFillParent(true);
	    window.setBackground(skin.getDrawable("gamescreenbackground"));
		
	    stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, false);
		state = GameStateFactory.getInstance();

		float panelwidth = CalculateGame.SCREEN_WIDTH/1.05f; 
		calculationTable = new CalculationTable();
	    calculationTable.setPanelWidth(panelwidth);
	    calculationTable.setPanelHeight(CalculateGame.SCREEN_HEIGHT/3f);
	    
	    List<Equation> currentEquation = state.getCurrentEquation().getEquationConstruction();
	    for (Equation eq : currentEquation)
		{
				for (EquationElement element : eq.getElements())
				{
					if (element instanceof Equation && ((Equation)element).getOperandCount() > 1)
					{
						//calculationTable.addElement(new Equation(((Equation)element).getTotal()));
					}
					else
					{
						calculationTable.addElement(element);
					}
				}
				calculationTable.carryLine();
		}
	    calculationTable.update();
	    
	    stage.addActor(window);
	    stage.addActor(calculationTable.getPanel());
	    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

}
