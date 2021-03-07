package com.oop.animalkingdom;

import java.awt.Color;

public class NinjaCat extends Critter 
{
	public NinjaCat() {};
	
	@Override
	public Action getMove(CritterInfo info) 
	{
	    if(Neighbor.EMPTY.equals(info.getFront()))
	    	return Action.HOP;
	    else if(Neighbor.EMPTY.equals(info.getRight()))
	    		return Action.RIGHT;
	    else if (Neighbor.EMPTY.equals(info.getLeft()))
	    		return Action.LEFT;
	    return Action.INFECT;
	}

	@Override
	public Color getColor() {
		return new Color((int)(Math.random()*256), (int)(Math.random()*256), (int)(Math.random()*256));
	}
	
	@Override
	public String toString() 
	{
		return "KIA";
	}
}
