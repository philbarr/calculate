package com.simplyapped.libgdx.ext.scene2d.spinner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class NumberSpinner extends Widget
{
	public final static int DEFAULT_WIDTH_SPINNER = 97;
	
	
	
	private IntAction action;
	private TextureRegion numberstrip;
	private int viewportHeight;
	private int height;

	public NumberSpinner(int from, int to, Interpolation interpolation, float duration)
	{
		this(new TextureAtlas(Gdx.files.classpath("com/simplyapped/libgdx/ext/scene2d/spinner/numberspinner.atlas")).findRegion("numberstrip"), 90, 90, from, to, interpolation, duration);
	}
	
	public NumberSpinner(TextureRegion numberstrip, int viewportHeight, int numberHeight, int from, int to, Interpolation interpolation, float duration)
	{
		this.height = numberstrip.getRegionHeight();
		this.numberstrip = numberstrip;
		this.viewportHeight = viewportHeight;
		int offset = numberHeight/4;
		action = new IntAction((from * numberHeight) - offset, (to * numberHeight) - offset);
		action.setInterpolation(interpolation);
		action.setDuration(duration);
		getActions().add(action);
	}	
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		int position = action.getValue();
		
		position =  position % height;
		while (position<=0)
		{
			position+=height; // allows for spinning off the bottom of the region (just start again at the top, which is taken care of)
		}
		
		TextureRegion regionMain = new TextureRegion(
				numberstrip,
				0,
				position,
				numberstrip.getRegionWidth(),
				viewportHeight);
		
		batch.draw(
				regionMain,
				getX(),
				getY(), 
				numberstrip.getRegionWidth(),
				viewportHeight);
		
		// deal with the case where the end of the strip joins the start of a new strip at the top
		if (position > height - viewportHeight)
		{
			TextureRegion extraRegion = new TextureRegion(
					numberstrip,
					0,
					0,
					numberstrip.getRegionWidth(),
					position - (height - viewportHeight));
			batch.draw(
					extraRegion,
					getX(),
					getY(), 
					numberstrip.getRegionWidth(),
					position - (height - viewportHeight));
		}
	}
}