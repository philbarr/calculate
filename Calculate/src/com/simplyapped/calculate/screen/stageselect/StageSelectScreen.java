package com.simplyapped.calculate.screen.stageselect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class StageSelectScreen extends DefaultScreen
{
	private Table window;
	private Skin skin = new Skin(Gdx.files.internal("data/stageselectscreen.json"));
	
    // calculate width and heights for the table
    float emptyRowHeight = CalculateGame.SCREEN_HEIGHT / 72;
    float buttonHeight = CalculateGame.SCREEN_HEIGHT / 13;
    float buttonWidth = CalculateGame.SCREEN_WIDTH / 1.8f;

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
				if (keycode == Keys.BACK)
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
	    
	    window.row().padTop(emptyRowHeight * 4);
	    for (int i = 1 ; i <= 10 ; i++)
	    {
	    	addButtonRow(window, i);
	    }
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
		if (level <= GameStateFactory.getInstance().getMaximumAchievedLevel())
	    {
			levelButton = new TextButton("Stage " + level, skin);
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
	    levelButton.getLabel().setFontScale(0.6f);
	    levelButton.align(Align.left);
	    levelButton.padBottom(10);
	    levelButton.padLeft(20);
	    
	    Button help = new Button(skin, "help");
	    help.addListener(new ClickListener()
	    	{
	    		public void clicked(InputEvent event, float x, float y) 
	    		{
	    			
	    		};
	    	}
	    );
	    
	    table.add(help).width(buttonHeight).height(buttonHeight).align(Align.left).padLeft(20);
	    table.add(levelButton).width(buttonWidth).height(buttonHeight).align(Align.left).padLeft(-100);	    
	    table.row().padTop(emptyRowHeight).expandX();
	}

}
