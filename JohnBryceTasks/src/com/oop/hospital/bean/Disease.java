package com.oop.hospital.bean;

import static com.oop.hospital.utils.DataUtils.*;

public class Disease 
{
	private DiseaseDescription diseaseDescription;
	private int timeToCure;
	
	public Disease() {
		setDiseaseDescription();
		setTimeToCure();
	}
	
	public DiseaseDescription getDiseaseDescription() {
		return diseaseDescription;
	}

	public void setDiseaseDescription() {
		diseaseDescription = DiseaseDescription.getRandom();
	}

	public int getTimeToCure() {
		return timeToCure;
	}

	public void setTimeToCure() {
		this.timeToCure = getRandomValue(3, 21);
	}
	
	@Override
	public String toString() {
		return "disease:" + diseaseDescription + ", Time to cure:" + timeToCure;
	}
}
