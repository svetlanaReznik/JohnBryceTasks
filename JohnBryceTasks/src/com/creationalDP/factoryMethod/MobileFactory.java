package com.creationalDP.factoryMethod;

public class MobileFactory 
{
	public static Mobile createMobile(String input) 
	{
		if(input.isEmpty())
			return null;
		if("Android".equals(input))
			return new Android();
		return new Iphone();
	}
}
