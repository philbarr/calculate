package com.simplyapped.calculate.screen.stageselect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.scene2d.FlatUIButton;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class StageSelectScreen extends DefaultScreen
{
	private Table window;
	private Skin skin = new Skin(Gdx.files.internal("data/stageselectscreen.json"));
	
    // calculate width and heights for the table
    float emptyRowHeight = CalculateGame.SCREEN_HEIGHT / 72;
    float buttonSize = CalculateGame.SCREEN_HEIGHT / 7;

	public StageSelectScreen(DefaultGame game)
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
				if (keycode == Keys.BACK || keycode == Keys.BACKSPACE)
				{
					game.transitionTo(CalculateGame.MAIN_MENU_SCREEN, TransitionFixtures.UnderlapRight());
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
	    
	    window.row().padTop(emptyRowHeight);
	    Table stageTable = new Table();
	    stageTable.setWidth(CalculateGame.SCREEN_WIDTH);
	    for (int i = 1 ; i <= 10 ; i++)
	    {
	    	addButtonRow(stageTable, i);
	    }
	    
	    ScrollPane pane = new ScrollPane(stageTable);
	    pane.setWidth(CalculateGame.SCREEN_WIDTH);
	    window.add(pane);
	    window.row().padBottom(emptyRowHeight * 6);
	    
	    window.setBackground(skin.getDrawable("stageselectscreenbackground"));
	    stage.addActor(window);
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

	private void addButtonRow(Table table, final int level)
	{
		// buttons
		TextButton levelButton;
		if (true)//(level <= GameStateFactory.getInstance().getMaximumAchievedLevel())
	    {
			levelButton = new FlatUIButton("" + level, skin, "l" + level);
		    levelButton.addListener(new ClickListener() {
		        @Override
		        public void clicked(InputEvent event, float x, float y)
		        {
		        	GameStateFactory.getInstance().setSelectedLevel(level);
		        	game.transitionTo(CalculateGame.GAME_SCREEN, TransitionFixtures.OverlapLeft());
		        }
		    });
	    }
	    else
	    {
	    	levelButton = new TextButton("Stage " + level, skin, "disabled");
	    	levelButton.setDisabled(true);
	    }
	    levelButton.getLabel().setFontScale(1.5f);
	    
	    /*
	    Button help = new Button(skin, "help");
	    help.addListener(new ClickListener()
	    	{
	    		public void clicked(InputEvent event, float x, float y) 
	    		{
	    			WindowStyle style = skin.get("dialog", WindowStyle.class);
	    			Dialog dialog = new Dialog("", style);
	    			dialog.setSize(CalculateGame.SCREEN_WIDTH/1.3f, CalculateGame.SCREEN_HEIGHT/1.3f);
	    			dialog.setPosition(((CalculateGame.SCREEN_WIDTH-dialog.getWidth())/2), ((CalculateGame.SCREEN_HEIGHT-dialog.getHeight())/2));
	    			LabelStyle labelStyle = skin.get("dialog", LabelStyle.class);
//	    			labelStyle.font.setScale(0.5f);
					dialog.text("blah blah ablh", labelStyle);
							
					AlphaAction action = new AlphaAction();
					action.setDuration(1f);
					action.setReverse(true);
					action.setInterpolation(Interpolation.pow5);
	    			dialog.addAction(action);
					
	    			stage.addActor(dialog);
	    		};
	    	}
	    );
	    */
	    table.add(levelButton).width(buttonSize).height(buttonSize).align(Align.left).expandX().padLeft(0f).padRight(20f);	
	    
	    Table details = new Table(skin);
	    details.add("Attempts: ", "details").align(Align.left);
	    details.add("1", "details").align(Align.right);
	    details.row();
	    details.add("Completed: ", "details").align(Align.left);
	    details.add("1", "details").align(Align.right);
	    details.setBackground(getDetailsBackground());
	    details.pad(15f);
	    table.add(details);
	    table.row().padTop(emptyRowHeight).expandX();
	    table.debug();
	}

	private TextureRegionDrawable getDetailsBackground()
	{
		Pixmap pix = new Pixmap(1,1,Format.RGBA4444);
		pix.setColor(0.2f,0.2f,0.2f,0.8f);
		pix.fill();
		Texture texture = new Texture(pix);
		TextureRegionDrawable trd = new TextureRegionDrawable(new TextureRegion(texture));
		return trd;
	}

	@Override
	public void render(float delta)
	{
		// TODO Auto-generated method stub
		super.render(delta);
//		Table.drawDebug(stage);
	}
}
