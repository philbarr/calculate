package com.simplyapped.calculate.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
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
	private ScrollPane pane;
	private Table calculationTable;

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
	    
	    calculationTable = new Table();
	    calculationTable.setBackground(skin.getTiledDrawable("graphtile"));
	    
	    pane = new ScrollPane(calculationTable);
	    pane.getStyle().background = new NinePatchDrawable( skin.getPatch("calcbackground"));
	    pane.setWidth(CalculateGame.SCREEN_WIDTH/1.2f);
	    pane.setHeight(CalculateGame.SCREEN_HEIGHT/2.2f);
	    pane.setPosition(CalculateGame.SCREEN_WIDTH/2 - pane.getWidth()/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/1.8f);
	    pane.setScrollingDisabled(true, false);
	    
	    window.setBackground(skin.getDrawable("gamescreenbackground"));
	    stage.addActor(window);
	    stage.addActor(pane);
	    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

	private void drawCalculationTable()
	{
		createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    createRow(calculationTable);
	    calculationTable.row();
	    calculationTable.add().expandY();
	    
	    pane.setScrollY(calculationTable.getHeight());
	}

	private void createRow(Table calculation)
	{
		calculation.row();
	    calculation.add(getLabel()).expandX().top().left().padLeft(20).padTop(20).fillX();
	    Label actor = new Label("=", skin, "calculation");
	    actor.setAlignment(Align.center);
		calculation.add(actor).width(60).padTop(20).top();
	    calculation.add(new Label("99999", skin, "calculation")).minWidth(100).padRight(40).padTop(20).top().fillX();
	}
	
	@Override
	public void render(float delta)
	{
		super.render(delta);
		
		Table.drawDebug(stage);
	}

	private Label getLabel()
	{
		Label label = new Label("50 + 3", skin, "calculation");
		label.setAlignment(Align.left, Align.left);
		return label;
	}
}
