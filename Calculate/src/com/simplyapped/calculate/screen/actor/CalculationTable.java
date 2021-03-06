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
import com.badlogic.gdx.utils.Disposable;
import com.esotericsoftware.tablelayout.Cell;
import com.simplyapped.calculate.CalculateGame;
import com.simplyapped.calculate.numbers.Equation;
import com.simplyapped.calculate.numbers.EquationElement;
import com.simplyapped.calculate.numbers.NonIntegerDivisionException;
import com.simplyapped.calculate.numbers.Operator;

public class CalculationTable extends Table implements Disposable
{
	private final int MAX_LINE_LENGTH = 8;
	private List<List<EquationElement>> calculationElements = new ArrayList<List<EquationElement>>();
	private Skin skin;
	private ScrollPane calculationPane;
	private float screenWidth;
	private float screenHeight;
	
	public CalculationTable(float screenWidth, float screenHeight)
	{
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		skin = new Skin(Gdx.files.internal("data/gamescreen.json"));
	    this.setBackground(skin.getTiledDrawable("graphtile"));
	    
	    calculationPane = new ScrollPane(this);
	    calculationPane.getStyle().background = new NinePatchDrawable( skin.getPatch("calcbackground"));
	    
	    calculationPane.setScrollingDisabled(true, false);
	}
	
	public void update()
	{
		calculationPane.setPosition(screenWidth/2 - calculationPane.getWidth()/2, screenHeight -  calculationPane.getHeight() - (screenHeight/10f));
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
		} 
		catch (NonIntegerDivisionException e)
		{
			Gdx.app.error(Equation.class.toString(), e.getMessage());
		}
		
	    Actor totalActor = createTotalActor(row, tempEq);
		Cell<?> cell = calculation.add(totalActor);
		padTotalCell(cell, row);
	}

	protected Actor createTotalActor(int row, Equation tempEq) 
	{
		return new Label(tempEq.getTotal() + "", skin, "calculation");
	}

	protected void padTotalCell(Cell<?> cell, int row) 
	{
		cell.minWidth(100).padRight(40).padTop(20).top().fillX();
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
	}
	
	@Override
	public void reset()
	{
		calculationElements.clear();
		update();
	}
	
	public boolean addElement(EquationElement element)
	{
		boolean lineAdded = false;
		if (calculationElements.size() == 0) //blank calculation table
		{
			lineAdded = true;
			addElementLine(element);
		}
		else
		{
			if (this.size()>0 && element instanceof Operator && lastLine().size() > 2)
			{
				EquationElement twoElementsBack = lastLine().get(lastLine().size()-2);
				boolean equivalent =  twoElementsBack instanceof Operator && ((Operator)twoElementsBack).isEquivalent((Operator)element); // is the last inputted operator compatible with this one
				if	(lastLineLength() > MAX_LINE_LENGTH || !equivalent)
				{
					lineAdded = true;
					carryLine();
				}
			}
			List<EquationElement> line = calculationElements.get(calculationElements.size()-1);
			line.add(element);
		}
		return lineAdded;
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

	public void newLine() {
		List<EquationElement> line = new ArrayList<EquationElement>();
		calculationElements.add(line);
	}

	public void removeLine() {
		calculationElements.remove(calculationElements.size()-1);
	}
	
	public void dispose() {
		skin.dispose();
	}
}
