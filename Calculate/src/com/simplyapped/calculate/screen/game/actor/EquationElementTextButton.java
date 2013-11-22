package com.simplyapped.calculate.screen.game.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class EquationElementTextButton extends TextButton
{
	private Integer data;
	public EquationElementTextButton(String text, Skin skin)
	{
		super(text, skin);
	}

	public EquationElementTextButton(String text, Skin skin, String style)
	{
		super(text, skin, style);
	}

	public Integer getData()
	{
		return data;
	}

	public void setData(Integer data)
	{
		this.data = data;
	}

}
