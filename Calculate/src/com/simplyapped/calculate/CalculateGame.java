package com.simplyapped.calculate;

import com.badlogic.gdx.Game;
import com.simplyapped.calculate.screen.GameScreen;

public class CalculateGame extends Game {
	public GameScreen gameScreen;
	
	@Override
	public void create() {
		gameScreen = new GameScreen(this);
		setScreen(gameScreen);
	}
}
