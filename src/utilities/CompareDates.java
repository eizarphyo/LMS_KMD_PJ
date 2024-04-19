package utilities;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CompareDates {
	public static boolean isDue(Date startDate) {
		// Get the current date
        LocalDate currentDate = LocalDate.now();
        
        // Assuming you have the first date stored as a java.sql.Date object
//        Date firstDateSQL = Date.valueOf("2024-04-10"); // Example: "2024-04-10"

        // Convert java.sql.Date to LocalDate
        LocalDate firstDate = startDate.toLocalDate();

        // Calculate the difference in days
        long daysDifference = ChronoUnit.DAYS.between(firstDate, currentDate);

        // Check if the difference is greater than 7 days
        if (daysDifference > 1) {
//            System.out.println("Current date is over 7 days after the first date.");
            return true;
        } else {
//            System.out.println("Current date is within 7 days of the first date.");
            return false;
        }
    }

	
}
