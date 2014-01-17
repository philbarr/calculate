package com.simplyapped.calculate.screen.game.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.simplyapped.calculate.numbers.Operator;

public class EquationElementFlatUIButton extends TextButton
{
	private Operator data;
	public EquationElementFlatUIButton(String text, TextButtonStyle textButtonStyle)
	{
		super(text, textButtonStyle);
	}
	public EquationElementFlatUIButton(String text, Skin skin, String style)
	{
		super(text, skin, style);
	}
	public Operator getData()
	{
		return data;
	}
	public void setData(Operator data)
	{
		this.data = data;
	}

}
