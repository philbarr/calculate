package com.simplyapped.calculate;

import com.simplyapped.calculate.screen.DefaultGame;
import com.simplyapped.calculate.screen.DefaultScreen;
import com.simplyapped.calculate.screen.GameScreen;
import com.simplyapped.calculate.screen.MainMenuScreen;

public class CalculateGame extends DefaultGame {

	public final static String MAIN_MENU_SCREEN = "MainMenuScreen";
	public final static String GAME_SCREEN = "GameScreen";
	
	public DefaultScreen gameScreen;
	public DefaultScreen mainMenuScreen;

	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		addScreen(MAIN_MENU_SCREEN, mainMenuScreen);
		addScreen(GAME_SCREEN, gameScreen);
		
		setScreen(mainMenuScreen);
	}
}
