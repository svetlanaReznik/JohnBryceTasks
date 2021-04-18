package com.creationalDP.factoryMethod;

import static com.creationalDP.factoryMethod.MobileFactory.*;

public class Test 
{
	public static void main(String[] args) 
	{
		//innerInput();
		outerInput(args);
	}

	private static void innerInput() 
	{
		System.out.println(createMobile("Android"));
		System.out.println(createMobile("Android"));
		System.out.println(createMobile("Iphone"));
		System.out.println(createMobile("Iphone"));
	}
	
	private static void outerInput(String[] args) 
	{
	    //Run -> Run Configuration -> Arguments -> Program arguments: Android Iphone 
		System.out.println(createMobile(args[0]));
		System.out.println(createMobile(args[1]));
	}
}
