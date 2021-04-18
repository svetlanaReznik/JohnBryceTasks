package com.creationalDP.singleton.lazyloading;

public class MySingleton 
{
	private static final int MAX=100;
	private static final int MIN=1;
	
	private int random;
	private static MySingleton instance = null;

	private MySingleton() {
		 setRandom();
	}

	public int getRandom() {
		return random;
	}

	public void setRandom() {
		this.random = (int)(Math.random() * ((MAX - MIN) + 1)) + MIN;
	}
	
    public static MySingleton getInstance() 
    {
        if(instance == null)
        	instance = new MySingleton();
    	return instance;
    }
}
