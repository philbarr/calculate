package com.simplyapped.calculate;

import com.badlogic.gdx.Screen;
import com.simplyapped.calculate.screen.DefaultGame;
import com.simplyapped.calculate.screen.GameScreen;
import com.simplyapped.calculate.screen.MainMenuScreen;

public class CalculateGame extends DefaultGame {

	public Screen gameScreen;
	public Screen mainMenuScreen;

	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		setScreen(mainMenuScreen);
	}

}
