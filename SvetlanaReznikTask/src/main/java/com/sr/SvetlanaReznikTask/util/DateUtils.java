package com.sr.SvetlanaReznikTask.util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

public class DateUtils {
	public static Date convert(java.util.Date date) {
		return new Date(date.getTime());
	}
	
	public static java.util.Date asDate(LocalDate localDate) {
	    return (java.util.Date) Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	 }
	
	public static Date setDate(long numOfDays) {
		return convert(asDate(LocalDate.now().plusDays(numOfDays)));
	}
}