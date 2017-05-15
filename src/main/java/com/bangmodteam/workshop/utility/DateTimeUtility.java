package com.bangmodteam.workshop.utility;

import java.util.Date;
import java.util.Locale;
import java.text.SimpleDateFormat;

public class DateTimeUtility {

	public static String DateToString(Date date) throws IllegalArgumentException {
		return DateToString(date, "yyyyMMdd");
	}

	public static String DateToString(Date date, String format) throws IllegalArgumentException {
		SimpleDateFormat sf = new SimpleDateFormat(format, Locale.US);
		return sf.format(date);
	}

}
