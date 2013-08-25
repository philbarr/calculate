package com.simplyapped.calculate.screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Operator;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.FlatUIButton;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class GameScreen extends DefaultScreen
{
	private Table window;
	private Skin skin = new Skin(Gdx.files.internal("data/gamescreen.json"));
	private Skin cards = new Skin(Gdx.files.internal("data/stageintroscreen.json")); // because in future we need to think more carefully about how we group assets
	private ScrollPane calculationPane;
	private Table calculationTable;
	private Texture texture;

	public GameScreen(DefaultGame game)
	{
		super(game);
		Pixmap pix = new Pixmap(1, 1, Format.RGBA4444);
		pix.setColor(0.979f,0.979f,0.979f,0.8f);
		pix.fill();
		texture = new Texture(pix);
	}

	@Override
	public void show()
	{
		float panelwidth = CalculateGame.SCREEN_WIDTH/1.4f; // used for the width of the operatorsTable, titleTable, and numbersTable

		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, false);
	    stage.addListener(new ClickListener()
		{
			@Override
			public boolean keyDown(InputEvent event, int keycode)
			{
				if (keycode == Keys.BACK || keycode == Keys.BACKSPACE)
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
	    calculationPane.setHeight(CalculateGame.SCREEN_HEIGHT/3f);
	    calculationPane.setPosition(CalculateGame.SCREEN_WIDTH/2 - calculationPane.getWidth()/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/2.2f);
	    calculationPane.setScrollingDisabled(true, false);
	    
	    Table operatorsTable = new Table();
	    operatorsTable.row().expandX().top().fill().pad(10);
	    operatorsTable.add(getOperatorButton(Operator.PLUS));
	    operatorsTable.add(getOperatorButton(Operator.MINUS));
	    operatorsTable.add(getOperatorButton(Operator.MULTIPLY));
	    operatorsTable.add(getOperatorButton(Operator.DIVIDE));
	    operatorsTable.add().width(20f).expand(false, false).pad(0);
	    operatorsTable.add(getCEButton());
		operatorsTable.setWidth(panelwidth);                                                                                               
	    operatorsTable.setHeight(CalculateGame.SCREEN_HEIGHT/15f);                                                                                             
	    operatorsTable.setPosition(CalculateGame.SCREEN_WIDTH/2 - panelwidth/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/1.8f);
	    operatorsTable.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));
//	    operatorsTable.debug();
	    
	    Table operandsTable = new Table();
	    operandsTable.row();
	    operandsTable.add(new FlatUIButton("23", skin, "operand"));
	    operandsTable.add(new FlatUIButton("24", skin, "operand"));
	    operandsTable.add(new FlatUIButton("23", skin, "operand"));
	    operandsTable.setWidth(panelwidth);
	    operandsTable.setPosition(CalculateGame.SCREEN_WIDTH/2 - panelwidth/2, CalculateGame.SCREEN_HEIGHT - CalculateGame.SCREEN_HEIGHT/1.1f);
	    operandsTable.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));
	    
	    window.setBackground(skin.getDrawable("gamescreenbackground"));
	    stage.addActor(window);
	    stage.addActor(calculationPane);
	    stage.addActor(operandsTable);
	    stage.addActor(operatorsTable);
	    
	    drawCalculationTable();
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

	private Actor getCEButton()
	{
		FlatUIButton button = new FlatUIButton("CE", skin, "ce");
		return button;
	}

	private Actor getOperatorButton(Operator operator)
	{
		String text = "";
		switch(operator)
		{
			case PLUS: text = "+"; break;
			case MINUS: text = "-"; 
			break;
			case MULTIPLY: text = "X"; break;
			case DIVIDE: text = "%"; break;
		}
		Button button = new FlatUIButton(text, skin, "operator");
		return button;
	}

	private void drawCalculationTable()
	{
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
