package utilities;

import javax.swing.JOptionPane;

public class InputValidator {
	
	public static boolean isAllDigits(String str) {
		for(int i=0; i<str.length(); i++) {
			if(!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isAllLetters(String str) {
		for(int i=0; i<str.length(); i++) {
			if(!Character.isLetter(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean containsDigits(String str) {
		for(int i=0; i<str.length(); i++) {
			if(Character.isDigit(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean containsSpecialCharacters(String str) {
		for(int i=0; i<str.length(); i++) {
			if(!Character.isAlphabetic(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasValidEmailFormat(String str) {
		int dot = str.indexOf(".");
		int at = str.indexOf("@");

		if (dot < 0 || at < 0 || str.indexOf(" ") > 0) {
			return false;
		}

		String name = str.substring(0, at);
		String domain = str.substring(at + 1, dot);
		String com = str.substring(dot + 1);

		if (!name.trim().equals("") && com.equals("com") && !containsSpecialCharacters(name) && !containsSpecialCharacters(domain)) {
			return true;
		}

		return false;
	}
}
