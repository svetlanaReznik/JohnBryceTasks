package com.creationalDP.singleton.threadsafe;

public class Test 
{
	public static MySingleton single1 = MySingleton.getInstance();
	public static MySingleton single2 = MySingleton.getInstance();
	
	public static void main(String[] args) 
	{
		while(true) 
		{
			new Thread(() -> {System.out.println(Thread.currentThread().getName());
							  changeFirstReference();}).start();
			
			new Thread(() -> {System.out.println(Thread.currentThread().getName());
							  changeSecondReference();}).start();
		}
		
	}
	
	public static void changeFirstReference() 
	{
		System.out.println("Random of first reference:" + single1.getRandom());
		single1.setRandom();
		System.out.println("Random of first reference:" + single1.getRandom() + "\n");
	}
	
	public static void changeSecondReference() 
	{
		System.out.println("Random of second reference:" + single2.getRandom());
		single2.setRandom();
		System.out.println("Random of second reference:" + single2.getRandom() + "\n");
	}
}
