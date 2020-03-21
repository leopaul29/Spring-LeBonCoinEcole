package com.leopaulmartin.spring.leboncoinecole.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

	public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public static String getFormattedDate(Date date) {
		return SIMPLE_DATE_FORMAT.format(date);
	}
}
