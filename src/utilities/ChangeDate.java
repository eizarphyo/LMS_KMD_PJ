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

	public static String toMySqlDateFormat() {
		LocalDate now = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return now.format(formatter);
	}

	public static String toDateAfterDays(int days) {
		LocalDate afterDays = LocalDate.now().plusDays(days);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return afterDays.format(formatter);
	}
}