package com.simplyapped.libgdx.ext.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class CroppableImage extends Image
{
	private int cropX;
	private int cropY;
	private int cropWidth;
	private int cropHeight;
	
	public CroppableImage(int cropx, int cropy, int cropwidth, int cropheight)
	{
		super();
		this.cropX = cropx;
		this.cropY = cropy;
		this.cropWidth = cropwidth;
		this.cropHeight = cropheight;
	}

	@Override
	public void draw(SpriteBatch batch, float parentAlpha)
	{
		validate();

		Color color = getColor();
		batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);

		float x = getX();
		float y = getY();
		float scaleX = getScaleX();
		float scaleY = getScaleY();
		Drawable drawable = getDrawable();
		if (drawable != null) {
			if (drawable.getClass() == TextureRegionDrawable.class) {
				TextureRegion region = ((TextureRegionDrawable)drawable).getRegion();
				float rotation = getRotation();
				if (scaleX == 1 && scaleY == 1 && rotation == 0)
					batch.draw(region, x + getImageX(), y + getImageY(), getImageWidth(), getImageHeight());
				else {
					batch.draw(region, x + getImageX(), y + getImageY(), getOriginX() - getImageX(), getOriginY() - getImageY(), getImageWidth(), getImageHeight(),
						scaleX, scaleY, rotation);
				}
			} else
				{
				
				drawable.draw(batch, x + getImageX(), y + getImageY(), getImageWidth() * scaleX, getImageHeight() * scaleY);
				}
		}
	}
}
