package com.simplyapped.calculate.screen.winner;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.scene2d.spinner.NumberSpinner;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;


public class WinnerScreen extends DefaultScreen
{
	private static final int BALLOON_COUNT = 4;
	private static final int MAX_BALLOON_HEIGHT = CalculateGame.SCREEN_HEIGHT/4;
	private static final float BALLOON_MOVE_DURATION = 10;
	private Skin skin = new Skin(Gdx.files.internal("data/winnerscreen.json"));

	public WinnerScreen(DefaultGame game)
	{
		super(game);
	}

	@Override
	public void show()
	{
		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, false);
		Table background = new Table();
		background.setBackground(skin.getDrawable("winnerscreenbackground"));
		background.setFillParent(true);
		stage.addActor(background);
		
		float width = CalculateGame.SCREEN_WIDTH / (BALLOON_COUNT + 1);
		Random r = new Random();
		for(int i = 0; i < BALLOON_COUNT; i++)
		{
			Image image = new Image(skin, "winnerballoon");
			float height = r.nextInt(MAX_BALLOON_HEIGHT);
			image.setPosition((width * (i+1))-width/2, height);
			image.setScale(0.3f);
			image.addAction(moveBy(0, CalculateGame.SCREEN_HEIGHT, BALLOON_MOVE_DURATION));
			stage.addActor(image);
		}
		
		Table frontTable = new Table();
		frontTable.setFillParent(true);
		Label label = new Label("Excellent!", skin, "text");
		label.setAlignment(Align.center);
		label.setFontScale(1.5f);
		frontTable.add(label).expand().fill().padBottom(CalculateGame.SCREEN_HEIGHT/4);
		stage.addActor(frontTable);
		
		
	}

}
