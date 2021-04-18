package com.oop.hospital.bean;

import java.util.HashMap;

import com.oop.hospital.utils.NurseUtils;
import static com.oop.hospital.utils.DiseaseUtils.*;

public class Room 
{
	private static final int PATIENT_NUMBER = 5;
	private static int R_NUMBER = 0;
		
	public int roomNumber = 0;
	
	private Doctor doctor;
	private Nurse nurse;
	private Patient[] patients = new Patient[PATIENT_NUMBER];
	
	public Room() {
		setRoomNumber();
		setDoctor(roomNumber);
		setNurse(roomNumber);
		setPatients();
	}
	
	public void setRoomNumber() {
		this.roomNumber = ++R_NUMBER;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public Doctor getDoctor() {
		return doctor;
	}
	
	public void setDoctor(int roomNumber) {
		this.doctor = Doctor.build().diseaseSpecialty().roomNumber(roomNumber);
		
	}

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(int currentRoomNumber) 
	{
		HashMap<Integer, Nurse> result = NurseUtils.isRoomAlreadyAssignedToNurse(roomNumber);
		if(!result.isEmpty()) {
			this.nurse = result.get(roomNumber);
			return;
		}
		this.nurse = Nurse.build().roomNumber(currentRoomNumber);
	}

	public Patient[] getPatients() {
		return patients;
	}

	public void setPatients() {
		for (int i = 0; i < patients.length; i++) {
			patients[i] = new Patient();
		}
	}
	
	public StringBuilder printPatients(Patient[] patients) {
		StringBuilder print = new StringBuilder("");
		for (Patient p : patients) {
			print.append("\n" + p);
		}
		return print;
	}
	
	public double averageTimeToCure() {
		int sum = 0;
		for (Patient p : patients) {
			sum += p.getDisease().getTimeToCure();
		}
		return (double)sum/PATIENT_NUMBER;
	}
	
	public void updateTimeToCureDisease() 
	{
		for (Patient p : patients) {
			updateDiseaseCount(p);
			updateDiseaseTimeToCure(p);
		}
	}
	
	public double calcPatientsAgeAverage() 
	{
		int sum = 0;
		
		for (Patient p : patients) {
			sum += p.getAge();
		}
		return (double)sum/PATIENT_NUMBER;
	}
	
	@Override
	public String toString() {
		return "**********************************************************************************************\n\n"
				+ "ROOM: roomNumber=" + roomNumber 
				+ ", \nDoctor: " + doctor.toString() 
		        + ", \nNurse: " + nurse.toString() 
				+ ", \nPatients: "
				+ printPatients(patients) + "\n";
	}
}
