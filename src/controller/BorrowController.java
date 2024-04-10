package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.AuthorModel;
import model.BookModel;
import model.BorrowModel;
import model.GenreModel;
import model.PublisherModel;
import model.ReturnModel;
import model.StudentModel;

public class BorrowController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(BorrowModel borrow) {
		String query = "INSERT INTO lib.borrow (borrow_id,stu_id,borrowed_at,borrowed_qty) VALUES (?, ?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, borrow.getBorrowId());
			ps.setString(2, borrow.getStuId());
			ps.setString(3, borrow.getBorrowAt());
			ps.setInt(4, borrow.getBorrowQty());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(BorrowModel borrow)  {
		String query = "UPDATE lib.borrow SET stu_id=?, borrowed_at=?, borrowed_qty=? WHERE borrow_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, borrow.getStuId());
			ps.setString(2, borrow.getBorrowAt());
			ps.setInt(3, borrow.getBorrowQty());
			ps.setString(4, borrow.getBorrowId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(BorrowModel borrow) {
		String query = "DELETE FROM lib.borrow WHERE borrow_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, borrow.getBorrowId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<BorrowModel> getAllBorrow() {
		String query = "SELECT * FROM lib.return ORDER BY return_id DESC";
		List<BorrowModel> borrowlist = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BorrowModel borrowed = new BorrowModel();

				borrowed.setBorrowId(rs.getString("borrow_id"));
				borrowed.setStuId(rs.getString("stu_id"));
				borrowed.setBorrowAt(rs.getString("borrowed_at"));
				borrowed.setBorrowQty(rs.getInt("borrowed_qty"));
				
				StudentModel student = new StudentModel();
				student.setStuId(borrowed.getStuId());

				borrowlist.add(borrowed);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowlist;
	}
	
	public BorrowModel getOneBorrowById(BorrowModel data) {
		String query = "SELECT * FROM lib.borrow WHERE borrow_id=?";
		
		BorrowModel borrow = new BorrowModel();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getBorrowId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				borrow.setBorrowId(rs.getString("borrow_id"));
				borrow.setStuId(rs.getString("stu_id"));
				borrow.setBorrowAt(rs.getString("borrowed_at"));
				borrow.setBorrowQty(rs.getInt("borrowed_qty"));
				
				StudentModel student = new StudentModel();
				student.setStuId(borrow.getStuId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrow;
	}


}
