package utilities;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ChangeDate {
	private Date today;

	public ChangeDate() {
		this.today = new Date();
	}

	public static String toMyDateFormat() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return now.format(formatter);
	}

	public static String toMyDateFormat(java.sql.Date date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return date.toLocalDate().format(formatter);
	}
	
	public static String toMyDateFormat(java.util.Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
	}
	
	public static java.sql.Date toSqlDate(java.util.Date utilDate) {
        return new java.sql.Date(utilDate.getTime());
    }

	public static String toDateAfterDays(int days) {
		LocalDate afterDays = LocalDate.now().plusDays(days);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return afterDays.format(formatter);
	}
	
	public static String toDateAfterDays(String date, int days) {
		LocalDate parsedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	    LocalDate afterDays = parsedDate.plusDays(days);
	    return afterDays.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}

}