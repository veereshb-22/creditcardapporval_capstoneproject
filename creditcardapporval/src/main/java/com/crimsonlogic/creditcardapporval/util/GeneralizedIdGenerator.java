package com.crimsonlogic.creditcardapporval.util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Random;

public class GeneralizedIdGenerator {
	
	private static final int LENGTH = 7;
	    
	private static final DecimalFormat FORMAT = new DecimalFormat("0000000");
	public static  String idGenerartor(String prefix) {
		
		Random random = new Random();
        int number = random.nextInt((int) Math.pow(10, LENGTH));

        // Format the number with leading zeros
        String formattedNumber = FORMAT.format(number);

        // Return the ID in the desired format
        return prefix + formattedNumber;
	}
	public static String cardNumberGenerate() {
		  Random random = new Random();
	        StringBuilder sb = new StringBuilder();

	        // Generate 16 random digits
	        for (int i = 0; i < 16; i++) {
	            sb.append(random.nextInt(10)); // Generates a digit between 0-9
	        }

	        return sb.toString(); 
	}
	 
		public static LocalDate cardStartTime() {
	        return LocalDate.now(); // Returns today's date
	    }
	
	    // Method to get the end date, which is 5 years from today
	    public static LocalDate cardEndTime() {
	        return LocalDate.now().plusYears(5); // Returns today's date + 5 years
	    }
	
	    // Method to format the date as a String
	    public static String formatDate(LocalDate date) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	        return date.format(formatter);
	    }
	    
	    
	    public static String maskNumber(String number) {
	        if (number == null || number.length() < 4) {
	            return number; // Return the original if it's null or less than 4 characters
	        }
	        // Create a string of 'X's of appropriate length and concatenate the last 4 digits
	        return "X".repeat(number.length() - 4) + number.substring(number.length() - 4);
	    }
	    
	    
	    public String securePassword(String password) {
			// TODO Auto-generated method stub
			String encodedPassword=Base64.getEncoder().encodeToString(password.getBytes());	
			return encodedPassword;
		}
}

		