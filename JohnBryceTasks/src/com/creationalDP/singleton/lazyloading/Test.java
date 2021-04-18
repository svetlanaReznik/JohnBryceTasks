package com.creationalDP.singleton.lazyloading;

public class Test {

	public static void main(String[] args) 
	{
		MySingleton single1 = MySingleton.getInstance();
		System.out.println("Random at creation moment: " + single1.getRandom() + "\n");
		MySingleton single2 = MySingleton.getInstance();
		
		single1.setRandom();
		System.out.println("Changed random via first reference:" + single1.getRandom());
		System.out.println("Changed random via first reference:" + single2.getRandom() + "\n");
		
		single2.setRandom();
		System.out.println("Changed random via second reference:" + single1.getRandom());
		System.out.println("Changed random via second reference:" + single2.getRandom());
	}
}
