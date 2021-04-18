package com.creationalDP.singleton.enumsingleton;

public enum EnumSingleton //enum its public final static, that loaded first to Class Loader
{						 //it has a default value 0 -> same as perform new() -> Eager Loading
	INSTANCE;     		//private final static INSTANCE = 0; -> Int
	
	int random;

    public int getRandom() {
        return random;
    }

    public void setRandom() {
        this.random = (int)(Math.random() *100 + 1);
    }
}
