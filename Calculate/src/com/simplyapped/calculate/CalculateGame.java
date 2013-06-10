package com.simplyapped.calculate;

import com.badlogic.gdx.math.Interpolation;
import com.simplyapped.calculate.screen.GameScreen;
import com.simplyapped.calculate.screen.MainMenuScreen;
import com.simplyapped.calculate.screen.StageSelectScreen;
import com.simplyapped.calculate.state.GameState;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;

public class CalculateGame extends DefaultGame {

	public final static int SCREEN_HEIGHT = 800;
	public final static int SCREEN_WIDTH = 600;
	public final static String MAIN_MENU_SCREEN = "MainMenuScreen";
	public final static String GAME_SCREEN = "GameScreen";
	public final static String STAGE_SELECT_SCREEN = "StageSelectScreen";
	
	@Override
	public void create() {
		GameState.Instance().setMaximumAchievedLevel(3);
		
		TransitionFixtures.setInterpolation(Interpolation.bounceOut);
		
		addScreen(MAIN_MENU_SCREEN, new MainMenuScreen(this));
		addScreen(GAME_SCREEN, new GameScreen(this));
		addScreen(STAGE_SELECT_SCREEN, new StageSelectScreen(this));
		
		setScreen(STAGE_SELECT_SCREEN);
	}
}
