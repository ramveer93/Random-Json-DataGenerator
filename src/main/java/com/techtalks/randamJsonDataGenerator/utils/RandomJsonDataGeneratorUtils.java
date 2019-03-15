package com.techtalks.randamJsonDataGenerator.utils;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RandomJsonDataGeneratorUtils {
	// chose a Character random from this String
	private static final String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
	private static final int size = 10;
	Random rd = new Random();

	public String getRandomString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size; i++) {
			int index = rd.nextInt(size);
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	public Integer getRandomInteger(){
		return rd.nextInt(Integer.MAX_VALUE);

	}

	public Date getRandomDate() {
		return java.sql.Date.valueOf(LocalDate.now().minus(Period.ofDays((new Random().nextInt(365 * 70)))));
	}

	public boolean getRandomBool() {
		return rd.nextBoolean();

	}

}
