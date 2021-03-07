package com.oop.animalkingdom;

import java.awt.Color;

public class Bear extends Critter 
{
	private boolean polar;
	private boolean rightStep=true;
	
	public Bear() {};
		
	public Bear(boolean polar) {
		this.polar = polar;
	}

	@Override
	public Action getMove(CritterInfo info) 
	{
		return info.frontThreat() ? Action.INFECT : (Neighbor.EMPTY.equals(info.getFront())) ? Action.HOP : Action.LEFT;
	}

	public boolean isPolar() {
		return polar;
	}


	public void setPolar(boolean polar) {
		this.polar = polar;
	}


	@Override
	public Color getColor() {
		return polar ? Color.WHITE : Color.BLACK;
	}

	@Override
	public String toString() 
	{
		if(rightStep) {
			rightStep = false; 	return "/";
		}
		rightStep = true; return "\\";
	}
}
