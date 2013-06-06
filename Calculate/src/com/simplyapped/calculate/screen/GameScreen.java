package com.simplyapped.calculate.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class GameScreen extends DefaultScreen
{
	private Table window;
	private Skin uiSkin = new Skin(Gdx.files.internal("data/gamescreen.json"));

	public GameScreen(DefaultGame game)
	{
		super(game);
	}

	@Override
	public void show()
	{
	    stage = new Stage(600, 800, false);

	    window = new Table();	    
	    window.setFillParent(true);
	    window.setX(0);
	    window.setY(0);
	    window.debug();
	    
	    Table table = new Table();
	    table.setColor(Color.WHITE);
	    table.row();
	    Skin skin = new Skin(Gdx.files.internal("data/uiSkin.json"));
	    Label label = new Label("asfd", skin);
	    label.setFillParent(true);
		table.add(label);
	    table.size(400);
	    table.debug();
	    
	    window.row();
	    window.add(table);
	    
	    window.setBackground(uiSkin.getDrawable("gamescreenbackground"));
	    stage.addActor(window);
	    Gdx.input.setInputProcessor(stage);
	}
}
