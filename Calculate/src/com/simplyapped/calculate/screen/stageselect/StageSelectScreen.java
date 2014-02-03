package com.simplyapped.calculate.screen.stageselect;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.state.GameStateFactory;
import com.simplyapped.calculate.state.LevelDetails;
import com.simplyapped.calculate.state.LevelInfo;
import com.simplyapped.libgdx.ext.DefaultGame;
import com.simplyapped.libgdx.ext.action.TransitionFixtures;
import com.simplyapped.libgdx.ext.screen.DefaultScreen;

public class StageSelectScreen extends DefaultScreen
{
	protected static final String DIALOG_DETAILS_TEXT = "Number Of Cards: %s%n" +
														"Time Limit: %s Seconds%n" +
														"Answer Range: %s - %s%n" +
														"Wins Required: %s%n";
	private Table window;
	private Skin skin;
	
    // calculate width and heights for the table
    float emptyRowHeight = 0;
    float buttonSize = 0;

	public StageSelectScreen(DefaultGame game)
	{
		super(game);
		skin = game.getAssets().get("data/stageselectscreen.json");
	}

	@Override
	public void show()
	{
		stage = new Stage(CalculateGame.SCREEN_WIDTH, CalculateGame.SCREEN_HEIGHT, true);
	    emptyRowHeight = stage.getHeight() / 72;
	    buttonSize = stage.getWidth() / 3.5f;
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
	    
	    window.row().padTop(emptyRowHeight);
	    Table stageTable = new Table();
	    stageTable.setWidth(stage.getWidth()*0.8f);
	    for (int i = 1 ; i <= LevelInfo.NUMBER_OF_LEVELS ; i++)
	    {
	    	addButtonRow(stageTable, i);
	    }
	    
	    ScrollPane pane = new ScrollPane(stageTable);
	    pane.setScrollingDisabled(true, false);
	    pane.setWidth(stage.getWidth()*0.8f);
	    window.add(pane);
	    window.row().padBottom(emptyRowHeight * 6);
	    window.setBackground(skin.getDrawable("stageselectscreenbackground"));
	    stage.addActor(window);
	    Gdx.input.setInputProcessor(stage);
	    Gdx.input.setCatchBackKey(true);
	}

	private void addButtonRow(Table table, final int level)
	{
		Table rowTable = new Table();
		rowTable.setBackground(skin.getDrawable("buttonborder"));
		
		// buttons
		TextButton levelButton = new TextButton("" + level, skin, "level"+level+"button");
	    levelButton.getLabel().setFontScale(1f);
	    
	    rowTable.add(levelButton).width(buttonSize).height(buttonSize/1.3f).align(Align.left).expandX().padLeft(0f).pad(20f);	
	    rowTable.add(createDetailsTable(level)).width(buttonSize*1.5f).height(buttonSize/1.3f).pad(20f);
	    rowTable.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y)
	        {
				// create the dialog
    			final Dialog dialog = new Dialog("", skin, "dialog");
    			dialog.setSize(stage.getWidth()/1.1f, stage.getHeight()/2f);
    			dialog.setPosition(((stage.getWidth()-dialog.getWidth())/2), ((stage.getHeight()-dialog.getHeight())/2));
    			LabelStyle labelStyle = skin.get("dialog", LabelStyle.class);
    			LevelInfo info = LevelInfo.getLevel(level);
    			LevelDetails levelDetails = GameStateFactory.getInstance().getLevelDetails(level);
    			String text = String.format(DIALOG_DETAILS_TEXT, info.getNumberOfCards(), info.getTimeLimit(), info.getMinRange(), info.getMaxRange() ,info.getCompletedRequired());
    			text+= info.isUseAllCards() ? "You MUST Use All Cards" : "Use Only The Cards You Need";
				Label details = new Label(text, labelStyle);
    			details.setFontScale(0.3f);
    			
				TextButton playButton = new TextButton("Play", skin, "green");
				playButton.addListener(new ClickListener(){
					@Override
					public void clicked(InputEvent event, float x, float y)
					{
						super.clicked(event, x, y);
						GameStateFactory.getInstance().setCurrentLevel(level);
						game.transitionTo(CalculateGame.STAGE_INTRO_SCREEN, TransitionFixtures.OverlapLeft());
					}
				});
				playButton.getLabel().setFontScale(0.3f);

				TextButton cancelButton = new TextButton("Close", skin, "red");
				cancelButton.addListener(new ClickListener()
					{
					@Override
					public void clicked(InputEvent event, float x, float y) {
						super.clicked(event, x, y);
						dialog.remove();
					}
					});
				cancelButton.getLabel().setFontScale(0.3f);
				dialog.getContentTable().add(details);
				dialog.getButtonTable().defaults().pad(15f).width(stage.getWidth()/3.5f).padBottom(45f);
				if (!levelDetails.isLocked())
				{
					dialog.getButtonTable().add(playButton);
				}
				dialog.getButtonTable().add(cancelButton);
				
    			stage.addActor(dialog);
	        }

	    });
	    table.add(rowTable);
	    table.row().padTop(emptyRowHeight).expandX();
	}

	private Table createDetailsTable(int level)
	{
		LevelDetails levelDetails = GameStateFactory.getInstance().getLevelDetails(level);
		Table details = new Table(skin);
	    float fontScale = 0.2f;
	    
	    if ((levelDetails.isLocked()))
	    {
	    	Label locked = new Label("Locked", skin, "details");
	    	locked.setFontScale(0.3f);
	    	details.add(locked).align(Align.center);
	    }
	    else
	    {
	    	Label attempts = new Label("Attempts: ", skin, "details");
			Label attemptsVal = new Label(levelDetails.getAttempts() +"", skin, "details");
	    	attempts.setFontScale(fontScale);
	    	attemptsVal.setFontScale(fontScale);
	    	details.add(attempts).align(Align.left);
	    	details.add(attemptsVal).align(Align.right);
	    	details.row();
	    	Label completed = new Label("Completed: ", skin, "details");
	    	Label completedVal = new Label(levelDetails.getCompleted() + "", skin, "details");
	    	completed.setFontScale(fontScale);
	    	completedVal.setFontScale(fontScale);
	    	details.add(completed).align(Align.left);
	    	details.add(completedVal).align(Align.right);
	    }
	    details.setBackground(skin.getDrawable("stagedetails"));
	    details.pad(15f);	   
	    
		return details;
	}

	@Override
	public void render(float delta)
	{
		super.render(delta);
	}
}
