package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.ReturnDetailModel;

public class ReturnDetailController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public  int insert(ReturnDetailModel details) {
		String query = "INSERT INTO lib.return_details (return_id, book_id, is_damaged, damage_fees) VALUES (?, ?, ?, ?)";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, details.getReturnId());
			System.out.println(".. " + details.getBookId());
			ps.setString(2, details.getBookId());
			ps.setBoolean(3, details.isDamaged());
			ps.setInt(4, details.getDamageFees());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static boolean isDamaged(String bookId, String borrowId) {
		String query = "SELECT * FROM lib.return_details WHERE book_id=? AND return_id in (SELECT return_id FROM lib.return WHERE borrow_id=?)";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, bookId);
			ps.setString(2, borrowId);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return rs.getBoolean("is_damaged");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
//	public  int update(ReturnDetailModel details) {
//		String query = "UPDATE lib.return SET return_id, book_id, is_damaged, damage_fees) VALUES (?, ?, ?, ?)";
//		
//		try {
//			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
//			ps.setString(1, details.getReturnId());
//			ps.setString(2, details.getBookId());
//			ps.setBoolean(3, details.isDamaged());
//			ps.setInt(4, details.getDamageFees());
//			
//			return ps.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return 0;
//	}
	
}
