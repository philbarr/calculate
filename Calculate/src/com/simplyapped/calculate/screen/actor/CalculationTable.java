package com.simplyapped.calculate.screen.actor;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.numbers.EquationElement;
import com.simplyapped.calculate.numbers.NonIntegerDivisionException;
import com.simplyapped.calculate.numbers.Operator;

public class CalculationTable extends Table
{
	private final int MAX_LINE_LENGTH = 8;
	private List<List<EquationElement>> calculationElements = new ArrayList<List<EquationElement>>();
	private Skin skin = new Skin(Gdx.files.internal("data/gamescreen.json"));
//	private Skin cards = new Skin(Gdx.files.internal("data/stageintroscreen.json"));
	private ScrollPane calculationPane;
	private List<Integer> textButtonTotalLines = new ArrayList<Integer>(); 
	private boolean isExpectingOperator = false;
	
	public CalculationTable()
	{
	    this.setBackground(skin.getTiledDrawable("graphtile"));
	    
	    calculationPane = new ScrollPane(this);
	    calculationPane.getStyle().background = new NinePatchDrawable( skin.getPatch("calcbackground"));
	    
	    calculationPane.setScrollingDisabled(true, false);
	}
	
	public void update()
	{
		calculationPane.setPosition(CalculateGame.SCREEN_WIDTH/2 - calculationPane.getWidth()/2, CalculateGame.SCREEN_HEIGHT -  calculationPane.getHeight() - (CalculateGame.SCREEN_HEIGHT/13f));
		this.clear();
		int row = 1;
		for(List<EquationElement> line : calculationElements)
		{
			createRow(this, line, row++);
		}

		calculationPane.setScrollY(this.getHeight());
	    this.row();
	}
	
	public void createRow(Table calculation, List<EquationElement> line, int row)
	{
		calculation.row();
	    calculation.add(getLabel(line)).expandX().top().left().padLeft(20).padTop(20).fillX();
	    Label actor = new Label("=", skin, "calculation");
	    actor.setAlignment(Align.center);
		calculation.add(actor).width(60).padTop(20).top();
		Equation tempEq = new Equation();
		try
		{
			tempEq.setElements(line);
		} catch (NonIntegerDivisionException e)
		{
			Gdx.app.error(Equation.class.toString(), e.getMessage());
		}
		
	    Actor totalActor;
	    if (getTextButtonTotalLines().contains(row))
	    {
	    	
	    }
	    totalActor = new Label(tempEq.getTotal() + "", skin, "calculation");
		calculation.add(totalActor).minWidth(100).padRight(40).padTop(20).top().fillX();
		
	}
	
	public void carryLine()
	{
		List<EquationElement> line = calculationElements.get(calculationElements.size()-1);
		Equation eq;
		try
		{
			eq = new Equation(line);
			addElementLine(new Equation(eq.getTotal()));
		} catch (NonIntegerDivisionException e)
		{
			Gdx.app.error(Equation.class.toString(), e.getMessage());
		}
	}

	public void addElementLine(EquationElement element)
	{
		List<EquationElement> line = new ArrayList<EquationElement>();
		line.add(element);
		calculationElements.add(line);
		isExpectingOperator = true;
	}
	
	@Override
	public void reset()
	{
		calculationElements.clear();
		update();
	}
	
	public void addElement(EquationElement element)
	{
		if (element instanceof Operator && !isExpectingOperator ||
			element instanceof Equation && isExpectingOperator)
		{
			return;//ignore when trying to add to equations next to each other or two operators.
		}
		
		if (calculationElements.size() == 0) //blank calculation table
		{
			addElementLine(element);
		}
		else
		{
			if (this.size()>0 &&
					(lastLineLength() > MAX_LINE_LENGTH && isExpectingOperator && element instanceof Operator) ||
					 (isExpectingOperator &&
					  element instanceof Operator && 
					  lastLine().size() > 2 && 
					  (lastLine().get(lastLine().size()-2)) instanceof Operator && 
					  !((Operator)lastLine().get(lastLine().size()-2)).isEquivalent((Operator)element)))
				{
					carryLine();
				}
			List<EquationElement> line = calculationElements.get(calculationElements.size()-1);
			line.add(element);
			isExpectingOperator = !isExpectingOperator;
		}
	}
	private int lastLineLength()
	{
		String str = "";
		for (EquationElement e : lastLine())
		{
			str+=e.toString();
		}
		str = str.replace("(", "").replace(")", "").replace(" ", "");
		return str.length();
	}
	private Label getLabel(List<EquationElement> line)
	{
		String lineString = "";
		for(EquationElement element : line)
		{
			lineString+=element.toString() + " ";
		}
		Label label = new Label(lineString, skin, "calculation");
		label.setAlignment(Align.left, Align.left);
		return label;
	}
	
	public List<EquationElement> lastLine()
	{
		return calculationElements.get(calculationElements.size()-1);
	}

	public int size()
	{
		return calculationElements.size();
	}

	public List<Integer> getTextButtonTotalLines()
	{
		return textButtonTotalLines;
	}

	public void setTextButtonTotalLines(List<Integer> textButtonTotalLines)
	{
		this.textButtonTotalLines = textButtonTotalLines;
	}

	public void setPanelWidth(float panelwidth)
	{
		calculationPane.setWidth(panelwidth);
	}

	public void setPanelHeight(float height)
	{
		calculationPane.setHeight(height);
	}

	public Actor getPanel()
	{
		return calculationPane;
	}
}
