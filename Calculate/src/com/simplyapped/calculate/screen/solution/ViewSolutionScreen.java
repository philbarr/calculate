package com.simplyapped.calculate.screen.solution;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class ViewSolutionScreen extends DefaultScreen
{
	private Skin skin = new Skin(Gdx.files.internal("data/gamescreen.json"));
	private GameState state;
	private Table calculationTable;
	private ScrollPane calculationPane;

	public ViewSolutionScreen(DefaultGame game)
	{
		super(game);
	}

	@Override
	public void show()
	{
		float panelwidth = CalculateGame.SCREEN_WIDTH/1.05f; // used for the width of the operatorsTable, titleTable, and numbersTable
		
		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, false);
		state = GameStateFactory.getInstance();
	    calculationTable = new Table();
	    calculationTable.setBackground(skin.getTiledDrawable("graphtile"));
	    
	    calculationPane = new ScrollPane(calculationTable);
	    calculationPane.getStyle().background = new NinePatchDrawable( skin.getPatch("calcbackground"));
	    calculationPane.setWidth(panelwidth);
	    calculationPane.setHeight(CalculateGame.SCREEN_HEIGHT/3f);
	    calculationPane.setPosition(CalculateGame.SCREEN_WIDTH/2 - calculationPane.getWidth()/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/2.2f);
	    calculationPane.setScrollingDisabled(true, false);
	}

}
