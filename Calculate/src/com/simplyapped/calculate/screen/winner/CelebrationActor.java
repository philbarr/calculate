package com.simplyapped.calculate.screen.winner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class CelebrationActor extends Actor
{
	private ParticleEffect effect;

	public CelebrationActor(TextureAtlas atlas, float x, float y, String pFile, String imageDir)
	{
		effect = new ParticleEffect();
		effect.load(Gdx.files.internal(pFile), Gdx.files.internal(imageDir));
		effect.setPosition(x, y);
		effect.start();

	}

	@Override
	public void act(float delta)
	{
		effect.update(delta);
		if (effect.isComplete())
		{
			effect.start();
		}
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		super.draw(batch, parentAlpha);
		effect.draw(batch, parentAlpha);
	}
}
