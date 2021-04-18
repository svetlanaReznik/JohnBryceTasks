package com.oop.hospital.utils;

import java.util.HashMap;

import com.oop.hospital.bean.DiseaseDescription;
import com.oop.hospital.bean.Patient;

public class DiseaseUtils 
{
	public static HashMap<String, Integer> diseaseCount = new HashMap<>();
	public static HashMap<String, Integer> diseaseTimeToCure = new HashMap<>();
	
	public static void initMapCount(HashMap<String, Integer> diseaseCount) {
		for (DiseaseDescription d : DiseaseDescription.values()) {
			diseaseCount.put(d.toString(), 0);
		}
	}
	
	public static void printDiseaseCount() {
		System.out.println("List of patients' diseases:");
		diseaseCount.entrySet().forEach(entry -> {
		    System.out.println(entry.getKey() + " " + entry.getValue());
		});
	}
	
	public static void updateDiseaseTimeToCure(Patient p) {
		diseaseTimeToCure.computeIfPresent(p.getDisease().getDiseaseDescription().toString(), (k, v) -> v + p.getDisease().getTimeToCure());
	}
	
	public static void updateDiseaseCount(Patient p) {
		diseaseCount.computeIfPresent(p.getDisease().getDiseaseDescription().toString(), (k, v) -> v + 1);
	}

}
