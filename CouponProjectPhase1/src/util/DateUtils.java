package util;

import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateUtils {
	public static Date convert(java.util.Date date) {
		return new Date(date.getTime());
	}
	
	public static java.util.Date asDate(LocalDate localDate) {
	    return (java.util.Date) Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	 }
}
