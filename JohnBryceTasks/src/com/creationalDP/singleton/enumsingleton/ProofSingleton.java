package com.creationalDP.singleton.enumsingleton;

public class ProofSingleton {

	public static void main(String[] args) 
	{
		EnumSingleton single1 = EnumSingleton.INSTANCE;
		System.out.println("Random at creation moment: " + single1.getRandom() + "\n");
		EnumSingleton single2 = EnumSingleton.INSTANCE;
        
		single1.setRandom();
        System.out.println("Changed random via first reference:" + single1.getRandom());
		System.out.println("Changed random via first reference:" + single2.getRandom() + "\n");
		
		single2.setRandom();
		System.out.println("Changed random via second reference:" + single1.getRandom());
		System.out.println("Changed random via second reference:" + single2.getRandom());
	}
}
