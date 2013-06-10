package com.simplyapped.libgdx.ext.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;

public interface StagedScreen extends Screen
{
	Stage getStage();
}
