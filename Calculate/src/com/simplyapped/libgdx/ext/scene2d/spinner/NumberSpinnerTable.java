package com.simplyapped.libgdx.ext.scene2d.spinner;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class NumberSpinnerTable extends Table
{
	private List<NumberSpinner> spinners = new ArrayList<NumberSpinner>();
	
	public NumberSpinnerTable(int targetNumber, Interpolation interpolation, float duration, float hangingdelay)
	{
		row();
		char[] digits = ("" + targetNumber).toCharArray();
		setWidth(digits.length * NumberSpinner.DEFAULT_WIDTH_SPINNER);
	
		for (int i = 0; i < digits.length; i++)
		{
			int num = Integer.parseInt(digits[i]+"");
			int from = i % 2 == 0 ? num : num+20;
			int to =   i % 2 == 0 ? num+20 : num;
			NumberSpinner spinner = new NumberSpinner(from, to, interpolation, duration + (i*hangingdelay));
			add(spinner).expand().fill();
			spinners.add(spinner);
		}
	}
	
	public void finishSpinning()
	{
		for(NumberSpinner spinner: spinners)
		{
			for (Action action : spinner.getActions())
			{
				if (action instanceof TemporalAction)
				{
					((TemporalAction)action).finish();
				}
			}
		}
	}

	public boolean isSpinning()
	{
		for (NumberSpinner spinner : spinners)
		{
			if (spinner.getActions() != null && spinner.getActions().size>0)
			{
				return true;
			}
		}
		return false;
	}
}
