package com.simplyapped.calculate.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class GameScreen extends DefaultScreen
{
	private Table window;
	private Skin skin = new Skin(Gdx.files.internal("data/gamescreen.json"));

	public GameScreen(DefaultGame game)
	{
		super(game);
	}

	@Override
	public void show()
	{
	    stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, false);
	    stage.addListener(new ClickListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if (keycode == Keys.BACK)
				{
					game.transitionTo(CalculateGame.STAGE_SELECT_SCREEN, TransitionFixtures.UnderlapRight());
					return true;
				}
				return false;
			}
		});
	    
	    window = new Table();	    
	    window.setFillParent(true);
	    window.setX(0);
	    window.setY(0);
	    window.debug();
	    
	    Table graph = new Table();
	    graph.setBackground(skin.getTiledDrawable("graphtile"));
	    graph.setWidth(CalculateGame.SCREEN_WIDTH/.8f);
	    graph.setHeight(CalculateGame.SCREEN_HEIGHT/.4f);
	    
	    Label label = getLabel();
	    
	    graph.row().expand();
	    graph.add(getLabel());
	    graph.row().expand();
	    graph.add(getLabel());
	    graph.row().expand();
	    graph.add(getLabel());
	    graph.row().expand();
	    graph.add(getLabel());graph.row().expand();
	    graph.add(getLabel());
	    graph.row().expand();
	    graph.add(getLabel());
	    
	    ScrollPane pane = new ScrollPane(graph);
	    pane.getStyle().background = new NinePatchDrawable( skin.getPatch("calcbackground"));
	    pane.setWidth(CalculateGame.SCREEN_WIDTH/1.2f);
	    pane.setHeight(CalculateGame.SCREEN_HEIGHT/2.2f);
	    pane.setPosition(CalculateGame.SCREEN_WIDTH/2 - pane.getWidth()/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/1.8f);
	    
	    window.setBackground(skin.getDrawable("gamescreenbackground"));
	    stage.addActor(window);
	    stage.addActor(pane);
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

	private Label getLabel()
	{
		return new Label("blah ablah", skin, "calc");
	}
}
