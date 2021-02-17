package com.oop.company;

public enum Group 
{
	A, B, C, D, E, F;
	
	public int getValue() {
		return this.ordinal();
	}

	public static Group forValue(int value) {
		return values()[value];
	}

	public String toString() {
		return forValue(getValue()).name();
	}
	
	public static Group getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
