package com.simplyapped.calculate.screen.game.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.simplyapped.calculate.numbers.Operator;
import com.simplyapped.libgdx.ext.scene2d.flat.FlatUIButton;

public class EquationElementFlatUIButton extends FlatUIButton
{
	private Operator data;
	public EquationElementFlatUIButton(String text, FlatUIButtonStyle textButtonStyle)
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
