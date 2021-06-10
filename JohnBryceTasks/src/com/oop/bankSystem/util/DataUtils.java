package com.oop.bankSystem.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DataUtils 
{
	public static double getRandomValue(double min, double max) {
		return (double)(Math.random() * (max - min + 1) + min);
	}
	
	private static final String DATE_FORMAT = "MM-DD-YYYY";
	public static final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
}
