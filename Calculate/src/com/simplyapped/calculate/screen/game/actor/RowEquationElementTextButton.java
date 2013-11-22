package com.simplyapped.calculate.screen.game.actor;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class RowEquationElementTextButton extends	EquationElementTextButton 
{
	private int row;
	
	public RowEquationElementTextButton(String text, Skin skin)
	{
		super(text, skin);
	}

	public RowEquationElementTextButton(String text, Skin skin, String style)
	{
		super(text, skin, style);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	
}
