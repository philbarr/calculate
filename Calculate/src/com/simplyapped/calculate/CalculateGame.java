package com.simplyapped.calculate;

import com.simplyapped.calculate.screen.DefaultGame;
import com.simplyapped.calculate.screen.GameScreen;
import com.simplyapped.calculate.screen.MainMenuScreen;
import com.simplyapped.calculate.screen.StageSelectScreen;

public class CalculateGame extends DefaultGame {

	public final static int SCREEN_HEIGHT = 800;
	public final static int SCREEN_WIDTH = 600;
	public final static String MAIN_MENU_SCREEN = "MainMenuScreen";
	public final static String GAME_SCREEN = "GameScreen";
	public final static String STAGE_SELECT_SCREEN = "StageSelectScreen";
	
	@Override
	public void create() {
		addScreen(MAIN_MENU_SCREEN, new MainMenuScreen(this));
		addScreen(GAME_SCREEN, new GameScreen(this));
		addScreen(STAGE_SELECT_SCREEN, new StageSelectScreen(this));
		
		setScreen(MAIN_MENU_SCREEN);
	}
}
