package com.oop.hospital.test;

import com.oop.hospital.bean.Hospital;
import com.oop.hospital.statistics.HospitalStatistics;

public class Statistics 
{
	public static void runStatistics(Hospital hospital) 
	{
		System.out.println("***************************************** STATISTICS *****************************************");
		HospitalStatistics.getHumanCount();
		HospitalStatistics.averageTimeToCure(hospital);
		HospitalStatistics.averageTimeToCurePerDisease(hospital);
		HospitalStatistics.calcPatientsAgeAverage(hospital);
		HospitalStatistics.mostCommonDisease();
		HospitalStatistics.rarestDisease();
		HospitalStatistics.averageAgeDoctorsAndNurses(hospital);
	}
}
