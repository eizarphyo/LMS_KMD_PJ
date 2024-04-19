package utilities;


import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JDialog;

import view.DonationDialogView;

public class MyDate {
	
//	public static void main(String[] args) {
//		MyDate da = new MyDate();
//		System.out.println(da.getMySQLDateFormat());
//	}
	
	private Date today;
	public MyDate() {
		this.today = new Date();
	}
	
	public String getMySQLDateFormat() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(this.today);
		
	}
	
}


