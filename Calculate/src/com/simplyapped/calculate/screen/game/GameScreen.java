package com.simplyapped.calculate.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Operator;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class GameScreen extends DefaultScreen
{
	private Table window;
	private Skin skin = new Skin(Gdx.files.internal("data/gamescreen.json"));
	private ScrollPane calculationPane;
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
	    
	    calculationPane = new ScrollPane(calculationTable);
	    calculationPane.getStyle().background = new NinePatchDrawable( skin.getPatch("calcbackground"));
	    calculationPane.setWidth(CalculateGame.SCREEN_WIDTH/1.2f);
	    calculationPane.setHeight(CalculateGame.SCREEN_HEIGHT/2.2f);
	    calculationPane.setPosition(CalculateGame.SCREEN_WIDTH/2 - calculationPane.getWidth()/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/1.8f);
	    calculationPane.setScrollingDisabled(true, false);
	    
	    Table operatorsTable = new Table();
	    operatorsTable.row().expandX().pad(100).top();
	    operatorsTable.add(getOperatorButton(Operator.PLUS));
	    operatorsTable.add(getOperatorButton(Operator.MINUS));
	    operatorsTable.add(getOperatorButton(Operator.MULTIPLY));
	    operatorsTable.add(getOperatorButton(Operator.DIVIDE));
	    operatorsTable.setWidth(CalculateGame.SCREEN_WIDTH/1.2f);                                                                                               
	    operatorsTable.setHeight(CalculateGame.SCREEN_HEIGHT/2.2f);                                                                                             
	    operatorsTable.setPosition(CalculateGame.SCREEN_WIDTH/2 - calculationPane.getWidth()/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/1.19f);
	    
	    window.setBackground(skin.getDrawable("gamescreenbackground"));
	    stage.addActor(window);
	    stage.addActor(calculationPane);
	    stage.addActor(operatorsTable);
	    
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

	private Actor getOperatorButton(Operator operator)
	{
		Button button = new Button(skin, operator.name().toLowerCase());
		return button;
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
	    
	    calculationPane.setScrollY(calculationTable.getHeight());
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
