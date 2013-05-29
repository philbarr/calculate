package com.simplyapped.calculate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.simplyapped.calculate.screen.GameScreen;
import com.simplyapped.calculate.screen.MainMenuScreen;

public class CalculateGame extends Game {
	public Screen gameScreen;
	public Screen mainMenuScreen;
	
	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		mainMenuScreen = new MainMenuScreen(this);
		setScreen(gameScreen);
	}
}
