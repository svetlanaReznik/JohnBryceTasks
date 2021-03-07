package com.oop.animalkingdom;

import java.awt.Color;
import java.util.Random;

public class Tiger extends Critter 
{
	public Tiger() {};
	
	public static enum Colors {
	   RED, GREEN, BLUE;
   	};
   	
   	private Color color;
   	
   	@Override
	public Action getMove(CritterInfo info) 
	{
		countSteps++;
				
		if(info.frontThreat()) 
			return Action.INFECT;
		else if(Neighbor.WALL.equals(info.getFront()) || Neighbor.WALL.equals(info.getRight()))
			return Action.LEFT;
		else if(Neighbor.SAME.equals(info.getFront()))
			return Action.RIGHT;	
		return Action.HOP;
	}

	@Override
	public Color getColor() 
	{
		return color = (countSteps%3 == 0) ? randomColor() : color;
	}
	
	public Color randomColor() 
   	{
   		Colors newColor =  Colors.values()[new Random().nextInt(Colors.values().length)];
		
		switch(newColor) 
		{
			case RED: color = Color.RED; break;
			case GREEN: color = Color.GREEN; break;
			case BLUE: color = Color.BLUE; break;
		}
		return color;
	}
	
	@Override
	public String toString() 
	{
		return "TGR";
	}
}
