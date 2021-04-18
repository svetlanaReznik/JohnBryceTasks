package com.oop.hospital.statistics;

import static com.oop.hospital.bean.Doctor.*;

import com.oop.hospital.bean.Doctor;
import com.oop.hospital.bean.Hospital;
import com.oop.hospital.bean.Nurse;

import static com.oop.hospital.bean.Nurse.*;
import com.oop.hospital.bean.Patient;
import com.oop.hospital.bean.Room;

import static com.oop.hospital.utils.DiseaseUtils.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import static com.oop.hospital.bean.Hospital.*;

public class HospitalStatistics 
{
	public static void getHumanCount() {
		System.out.println("Total count of human in hospital:");
		System.out.println("The number of Doctors in hospital is: " + Doctor.doctorCount);
		System.out.println("The number of Nurses in hospital is: " + Nurse.nurseCount);
		System.out.println("The number of Patients in hospital is: " + Patient.patientCount);
	}
	
	public static void averageTimeToCure(Hospital hospital) {
		System.out.println("\nAverage time to cure from disease is: " + hospital.averageTimeToCure() + "\n");
	}
	
	public static void averageTimeToCurePerDisease(Hospital hospital) 
	{
		initMapCount(diseaseCount);
		initMapCount(diseaseTimeToCure);
		
		for (Room r : hospital.getRooms()) {
			r.updateTimeToCureDisease();
		}
		
		diseaseTimeToCure.forEach((k,v) -> System.out.println("Average to cure from " + k + " will take in average: " + (double)v/diseaseCount.get(k) + " days."));
	}
	
	public static void calcPatientsAgeAverage(Hospital hospital) 
	{
		double sum = 0;
		
		for (Room r : hospital.getRooms()) {
			sum += r.calcPatientsAgeAverage();
		}
		System.out.println(String.format("\nThe patients' age per room is: %.2f", (double)sum/NUM_OF_ROOMS) + "\n"); 
	}
	
	public static void mostCommonDisease() {
		printDiseaseCount();
		String key = Collections.max(diseaseCount.entrySet(), Map.Entry.comparingByValue()).getKey();
		System.out.println("The most common disease is: " + key);
	}
	
	public static void rarestDisease() {
		String key = Collections.min(diseaseCount.entrySet(), Map.Entry.comparingByValue()).getKey();
		System.out.println("The most rare disease is: " + key);
	}
	
	public static void averageAgeDoctorsAndNurses(Hospital hospital) {
		HashSet<Nurse> nurses = new HashSet<Nurse>();
		int sum=0;
		
		for (Room r : hospital.getRooms()) {
			sum += r.getDoctor().getAge();
			nurses.add(r.getNurse());
		}
		
		for (Nurse n : nurses) {
			sum += n.getAge();
		}
		
		System.out.println(String.format("\nThe age average of doctors and nurses is: %.2f", (double)sum/(doctorCount+nurseCount)));
	}
}
