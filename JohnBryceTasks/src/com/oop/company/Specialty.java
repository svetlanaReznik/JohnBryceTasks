package com.oop.company;

public enum Specialty {
	INDUSTRY, CHEMISTRY, ELECTRONICS, MECHANICS, COMPUTER;
	
	public int getValue() {
		return this.ordinal();
	}

	public static Specialty forValue(int value) {
		return values()[value];
	}

	public String toString() {
		return forValue(getValue()).name();
	}
	
	public static Specialty getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
