package com.oop.animalkingdom;

import java.awt.Color;

import com.oop.animalkingdom.Critter.Action;
import com.oop.animalkingdom.Critter.Neighbor;

public class Giant extends Critter 
{
	private int countNameTimes;
	private String nickname;
	
	public Giant() {};
		
	@Override
	public Action getMove(CritterInfo info) 
	{
		countSteps++;
		return info.frontThreat() ? Action.INFECT : (Neighbor.EMPTY.equals(info.getFront())) ? Action.HOP : Action.RIGHT;
	}

	@Override
	public Color getColor() {
		return Color.GRAY;
	}
	
	public String pickNickname() 
   	{
		switch(++countNameTimes)
		{
			case 1: nickname = "fee"; break;
			
			case 2: nickname = "fie"; break;
			
			case 3: nickname = "foe"; break;
			
			case 4: countNameTimes = 0;
				    nickname = "fum"; 
				    break;
		}
   		return nickname;
	}
	

	@Override
	public String toString() 
	{
		return nickname = (countSteps%6 == 0) ? pickNickname() : nickname;
	}
}
