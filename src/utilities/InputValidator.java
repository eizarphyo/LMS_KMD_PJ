package utilities;

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
}
