package com.oop.hospital.bean;

public class Doctor extends Person 
{
	private DiseaseDescription diseaseSpecialty;
	private int roomNumber; 
	public static int doctorCount = 0;
	
	public Doctor() {
		super("Doctor");
	}
	
	public static Doctor build() {
		++doctorCount;
		return new Doctor();
	}
	
	public Doctor diseaseSpecialty() 
	{
		setDiseaseSpecialty();
		return this;
	}
	
	public Doctor roomNumber(int roomNumber) 
	{
		setRoomNumber(roomNumber);
		return this;
	}

	public DiseaseDescription getDiseaseSpecialty() {
		return diseaseSpecialty;
	}

	public void setDiseaseSpecialty() {
		diseaseSpecialty = DiseaseDescription.getRandom();
	}

	public int getRoomNumber() {
		return roomNumber;
	}
	
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public String print() {
		return roomNumber==0 ? " and not assigned to room yet " : " and belong to room number " + roomNumber;
	}

	@Override
	public String toString() {
		return  super.toString() + ", specialist to cure " + getDiseaseSpecialty() + print();
	}
}
