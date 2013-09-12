package com.simplyapped.calculate;

import com.badlogic.gdx.math.Interpolation;
import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.screen.game.GameScreen;
import com.simplyapped.calculate.screen.loser.LoserScreen;
import com.simplyapped.calculate.screen.mainmenu.MainMenuScreen;
import com.simplyapped.calculate.screen.stageintro.StageIntroScreen;
import com.simplyapped.calculate.screen.stageselect.StageSelectScreen;
import com.simplyapped.calculate.screen.winner.WinnerScreen;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.calculate.state.LevelDetails;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;

public class CalculateGame extends DefaultGame {

	public final static int SCREEN_HEIGHT = 800;
	public final static int SCREEN_WIDTH = 600;
	public final static String MAIN_MENU_SCREEN = "MainMenuScreen";
	public final static String GAME_SCREEN = "GameScreen";
	public final static String STAGE_SELECT_SCREEN = "StageSelectScreen";
	public final static String STAGE_INTRO_SCREEN = "StageIntroScreen";
	public final static String WINNER_SCREEN = "WinnerScreen";
	public final static String LOSER_SCREEN = "LoserScreen";
	
	@Override
	public void create() {
		GameState state = GameStateFactory.getInstance();
		LevelDetails levelDetails = state.getLevelDetails(1);
		levelDetails.setLocked(false);
		state.setCurrentEquation(new Equation(state.selectBigNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber(),state.selectSmallNumber()));
		
		TransitionFixtures.setInterpolation(Interpolation.pow5);
		
		addScreen(MAIN_MENU_SCREEN, new MainMenuScreen(this));
		addScreen(GAME_SCREEN, new GameScreen(this));
		addScreen(STAGE_SELECT_SCREEN, new StageSelectScreen(this));
		addScreen(STAGE_INTRO_SCREEN, new StageIntroScreen(this));
		addScreen(WINNER_SCREEN, new WinnerScreen(this));
		addScreen(LOSER_SCREEN, new LoserScreen(this));
		
		state.setCurrentLevel(1);
		setScreen(MAIN_MENU_SCREEN);
	}
}
