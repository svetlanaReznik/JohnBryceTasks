package com.oop.hospital.bean;

public class Patient extends Person 
{
	protected Disease disease;
	public static int patientCount = 0;

	public Patient() {
		super("Patient");
		++patientCount;
		setDisease();
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease() {
		this.disease = new Disease();
	}

	@Override
	public String toString() {
		return super.toString() + ", has " + getDisease().getDiseaseDescription() 
		       + " and will be cured in approximately in " + getDisease().getTimeToCure() + " days";
	}
}
