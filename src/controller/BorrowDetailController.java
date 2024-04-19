package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.BorrowDetailModel;

public class BorrowDetailController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(BorrowDetailModel borrowdetail) {
		String query = "INSERT INTO lib.borrow_details (borrow_id,book_id, is_returned) VALUES (?, ?, ?)";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, borrowdetail.getBorrowId());
			ps.setString(2, borrowdetail.getBookId());
			ps.setBoolean(3, borrowdetail.isReturned());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	
	public List<BorrowDetailModel> getAllDetailsByBorrowId(String id) {
		String query = "SELECT lib.borrow_details.*, book.*, author.author_name FROM lib.borrow_details\r\n"
				+ "INNER JOIN lib.book ON borrow_details.book_id = book.book_id\r\n"
				+ "INNER JOIN lib.author ON book.author_id = author.author_id WHERE borrow_id=?;";
		
		List<BorrowDetailModel> details = new ArrayList<>();
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, id);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				BorrowDetailModel d = new BorrowDetailModel();
				d.setBorrowId(rs.getString("borrow_id"));
				d.setBookId(rs.getString("book_id"));
				d.setReturned(rs.getBoolean("is_returned"));
				d.setBookTitle(rs.getString("title"));
				d.setAuthorName(rs.getString("author_name"));
				
				details.add(d);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return details;
	}
	
	public static int updateIsReturnedByBorrowIdAndBookId(BorrowDetailModel details) {
		String query = "UPDATE lib.borrow_details SET is_returned=? WHERE borrow_id=?  AND book_id=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ps.setBoolean(1, true);
			ps.setString(2, details.getBorrowId());
			ps.setString(3, details.getBookId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
}
