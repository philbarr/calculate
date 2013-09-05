package com.simplyapped.libgdx.ext.scene2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Disposable;

/**
 * Since this class creates it's own texture to draw it's background, it must be disposed 
 * when it is no longer needed.
 * @author pbarr
 *
 */
public class FlatUIButton extends TextButton implements Disposable
{
	private FlatUIButtonStyle style;
	private Pixmap pix;
	private Texture texture;
	private boolean isTextureDisposed;
	
	public FlatUIButton (String text, Skin skin) {
		this(text, skin.get(FlatUIButtonStyle.class));
		setSkin(skin);
	}

	public FlatUIButton (String text, Skin skin, String styleName) {
		this(text, skin.get(styleName, FlatUIButtonStyle.class));
		setSkin(skin);
	}
	
	public FlatUIButton(String text, FlatUIButtonStyle textButtonStyle)
	{
		super(text, textButtonStyle);
		this.style = textButtonStyle;
		pix = new Pixmap(1, 1, Format.RGBA4444);
	}

	@Override
	protected void drawBackground(SpriteBatch batch, float parentAlpha)
	{
		if (isPressed() && style.backgroundPressedColor != null)
		{
			pix.setColor(style.backgroundPressedColor);
		}
		else
		{
			pix.setColor(style.backgroundColor);
		}
		pix.fill();
		texture = new Texture(pix);
		
		Sprite s = new Sprite(new TextureRegion(texture));
		s.setSize(this.getWidth(), this.getHeight());
		s.setX(this.getX());
		s.setY(this.getY());
		s.draw(batch);
	}
	
	static public class FlatUIButtonStyle extends TextButtonStyle
	{
		public Color backgroundColor, backgroundPressedColor;
	}

	@Override
	public void dispose()
	{
		try
		{
			if (texture != null && !isTextureDisposed)
			{
				System.out.println("disposing FlatUIButton Texture");
				texture.dispose();
				isTextureDisposed = true;
			}
		} catch (Exception e)
		{
			System.out.println(e.toString());
			// it's already disposed, so that's fine
		}
	}
}
