package utilities;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
	public static String formatCurrency(int num) {
        // Create a NumberFormat instance for the default locale
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.getDefault());
        // Set grouping used to true
        numberFormat.setGroupingUsed(true);
        // Format the number
        String formattedNumber = numberFormat.format(num);
        return formattedNumber;
    }
	
	public static int extractNumber(String formattedNumber) {
        // Remove commas from the formatted number
        String cleanNumber = formattedNumber.replaceAll(",", "");
        // Parse the clean number string into an integer
        int extractedNumber = Integer.parseInt(cleanNumber);
        return extractedNumber;
    }
}
