package com.oop.company;

public enum Office 
{
	HAIFA, JERUSALIM, ASHDOD, EILAT;

	public int getValue() {
		return this.ordinal();
	}

	public static Office forValue(int value) {
		return values()[value];
	}

	public String toString() {
		return forValue(getValue()).name();
	}
	
	public static Office getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
