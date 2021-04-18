package com.oop.hospital.utils;

public class DataUtils 
{
	public static final int MAX_ROOMS = 10;
	
	public static int getRandomValue(int minDays, int maxDays) {
		return (int)(Math.random() * (maxDays - minDays + 1) + minDays);
	}
	

}
