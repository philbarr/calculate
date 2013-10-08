package com.simplyapped.libgdx.ext.scene2d.flat;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Disposable;

/**
 * Since this class creates it's own texture to draw it's background, it must be disposed 
 * when it is no longer needed.
 * @author pbarr
 *
 */
public class FlatUI extends Table implements Disposable
{
	private List<Disposable> disposables = new ArrayList<Disposable>();
	private FlatUIStyle style;
	private Pixmap pix;
	
	public static TextureRegionDrawable CreateBackgroundDrawable(float r, float g, float b, float a, float width, float height)
	{
		Pixmap pix = new Pixmap(1,1,Format.RGBA4444);
		pix.setColor(new Color(r,g,b,a));
		pix.fill();
		Texture texture = new Texture(pix);
		TextureRegion region = new TextureRegion(texture);
		region.setRegionWidth((int) width);
		region.setRegionHeight((int) height);
		return new TextureRegionDrawable(region);
	}
	
	public FlatUI (Table table, Skin skin, FlatUIStyle style) {
		setSkin(skin);
		this.style = style;
		pix = new Pixmap(1, 1, Format.RGBA4444);
		disposables.add(pix);
	}
	
	public FlatUI(Table table, Skin skin, String styleName)
	{
		this(table, skin, skin.get(styleName, FlatUIStyle.class));
	}

	@Override
	protected void drawBackground(SpriteBatch batch, float parentAlpha)
	{
		if (style != null && style.backgroundColor != null)
		{
			pix.setColor(style.backgroundColor);
			pix.fill();
			Texture texture = new Texture(pix);
			disposables.add(texture);
			Sprite s = new Sprite(new TextureRegion(texture));
			s.setSize(this.getWidth(), this.getHeight());
			s.setX(this.getX());
			s.setY(this.getY());
			s.draw(batch);
		}
	}
	
	static public class FlatUIStyle
	{
		public Color backgroundColor;
	}

	@Override
	public void dispose()
	{
		for (Disposable dis : disposables)
		{
			try
			{
				dis.dispose();
			} catch (Exception e)
			{
				// it's already disposed, so that's fine
			}
		}
	}
}
