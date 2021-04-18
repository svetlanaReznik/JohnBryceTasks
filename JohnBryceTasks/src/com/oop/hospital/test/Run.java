package com.oop.hospital.test;

import com.oop.hospital.bean.Hospital;
import static com.oop.hospital.test.Statistics.*;

public class Run 
{
	public static void main(String[] args) throws CloneNotSupportedException 
	{
		Hospital hospital = new Hospital();
		System.out.println(hospital);
		
		runStatistics(hospital);
	}
}
