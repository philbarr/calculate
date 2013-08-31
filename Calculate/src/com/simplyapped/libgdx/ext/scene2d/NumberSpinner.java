package com.simplyapped.libgdx.ext.scene2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.FloatAction;
import com.badlogic.gdx.scenes.scene2d.actions.IntAction;

public class NumberSpinner extends Actor
{
	private IntAction action;
	private int a;
	private TextureRegion numberstrip;

	public NumberSpinner(TextureRegion region)
	{
		this.numberstrip = region;
		action = new IntAction(0, 1000);
		action.setDuration(5);
		getActions().add(action);
	}
	
	@Override
	public void act(float delta)
	{
		super.act(delta);
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		int heightOfANumber = 100;
		
		TextureRegion region = new TextureRegion(
				numberstrip,
				0,
				action.getValue(),
				numberstrip.getRegionWidth(),
				heightOfANumber);
		batch.draw(
				region,
				getX(),
				getY(), 
				numberstrip.getRegionWidth(),
				heightOfANumber);
	}
}