package com.oop.animalkingdom;

import java.awt.Color;

import com.oop.animalkingdom.Critter.Action;

public class WhiteTiger extends Tiger 
{
	private boolean infected;
	
	public WhiteTiger() {};
	
	@Override
	public Action getMove(CritterInfo info) 
	{
	    Action action = super.getMove(info);
	    infected = (Action.INFECT.equals(action)) ? true : false;
		return action;
	}

	@Override
	public Color getColor() {
		return Color.WHITE;
	}
	
	@Override
	public String toString() 
	{
		return infected ? "TGR" : "tgr";
	}
}
