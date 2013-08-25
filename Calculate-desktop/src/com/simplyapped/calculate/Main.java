package com.simplyapped.calculate;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Calculate";
		cfg.useGL20 = false;
		cfg.width = 600;
		cfg.height = 800;
		
		new LwjglApplication(new CalculateGame(), cfg);
	}
}
