package com.leopaulmartin.spring.leboncoinecole.utils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Utils {

	private static final DateTimeFormatter formatter =
			DateTimeFormatter
					.ofLocalizedDateTime(FormatStyle.SHORT)
					.withZone(ZoneId.systemDefault());

	public static String getFormattedDate(Instant date) {
		return formatter.format(date);
	}
}
