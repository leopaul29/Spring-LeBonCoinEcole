package com.leopaulmartin.spring.leboncoinecole.utils;

import de.svenjacobs.loremipsum.LoremIpsum;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Random;

public class Utils {

	private static final DateTimeFormatter DATE_TIME_FORMATTER =
			DateTimeFormatter
					.ofLocalizedDateTime(FormatStyle.SHORT)
					.withZone(ZoneId.systemDefault());
	private static final LoremIpsum LOREM_IPSUM = new LoremIpsum();
	private static final Random RANDOM = new Random();

	public static String getFormattedDate(Instant date) {
		return DATE_TIME_FORMATTER.format(date);
	}

	public static String generateAnnounceTitle() {
		return LOREM_IPSUM.getWords(4);
	}

	public static String generateAnnounceDescription() {
		return LOREM_IPSUM.getParagraphs(6);
	}

	public static float getRandomPrice10() {
		return getFloatRandom(10);
	}

	public static float getRandomPrice100() {
		return getFloatRandom(100);
	}

	public static float getRandomPrice1000() {
		return getFloatRandom(1000);
	}

	public static Long getLongRandom(int max) {
		return Long.valueOf(RANDOM.nextInt(max));
	}

	public static float getFloatRandom(int max) {
		return RANDOM.nextInt(max);
	}

	public static int getRandom(int max) {
		return RANDOM.nextInt(max);
	}
}
