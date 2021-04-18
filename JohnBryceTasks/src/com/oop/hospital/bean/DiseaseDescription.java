package com.oop.hospital.bean;

public enum DiseaseDescription 
{
	DIARRHEA(1),
	HEADACHES(2),
	MONONUCLEOSIS(3),
	HEPATITIS(4),
	INFLUENZA(5);
	
	private int value;
	
	private DiseaseDescription(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}

	public int getIndex() {
		return this.ordinal();
	}
		
	public static DiseaseDescription getElement(int index) {
		return values()[index];
	}

	public String toString() {
		return getElement(getIndex()).name();
	}
	
	public static DiseaseDescription getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
