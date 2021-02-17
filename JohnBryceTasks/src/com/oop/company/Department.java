package com.oop.company;

public enum Department 
{
	LOGISTICS, PRODUCTION, FINANCIAL, HR, ENGENEERING;
	
	public int getValue() {
		return this.ordinal();
	}

	public static Department forValue(int value) {
		return values()[value];
	}

	public String toString() {
		return forValue(getValue()).name();
	}
	
	public static Department getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
