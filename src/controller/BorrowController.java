package controller;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

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
		String query = "INSERT INTO lib.borrow (borrow_id,stu_id,borrowed_at,borrowed_qty, qty_to_be_returned, returned_qty, is_all_returned) VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, borrow.getBorrowId());
			ps.setString(2, borrow.getStuId());
			ps.setDate(3, new java.sql.Date(borrow.getBorrowAt().getTime()));
			ps.setInt(4, borrow.getBorrowQty());
			ps.setInt(5, borrow.getQtyToBeReturned());
			ps.setInt(6, borrow.getReturnedQty());
			ps.setBoolean(7, borrow.isAllReturned());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(BorrowModel borrow) {
		String query = "UPDATE lib.borrow SET stu_id=?, borrowed_at=?, borrowed_qty=?, qty_to_be_returned=?, returned_qty=? is_all_returned=? WHERE borrow_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, borrow.getStuId());
			ps.setDate(2, new java.sql.Date(borrow.getBorrowAt().getTime()));
			ps.setInt(3, borrow.getBorrowQty());
			ps.setInt(4, borrow.getQtyToBeReturned());
			ps.setBoolean(5, borrow.isAllReturned());
			ps.setInt(6, borrow.getReturnedQty());
			ps.setString(7, borrow.getBorrowId());

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
		} catch (MySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Delete Fails!\nThis Book includes in borrow history.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<BorrowModel> getAllBorrow() {
		String query = "SELECT * FROM lib.borrow JOIN lib.student ON borrow.stu_id=student.stu_id ORDER BY borrow_id DESC";
		List<BorrowModel> borrowlist = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BorrowModel borrow = new BorrowModel();

				borrow.setBorrowId(rs.getString("borrow_id"));
				borrow.setStuId(rs.getString("stu_id"));
//				borrow.setBorrowAt(rs.getString("borrowed_at"));
				borrow.setBorrowAt(rs.getDate("borrowed_at"));
				borrow.setBorrowQty(rs.getInt("borrowed_qty"));
				borrow.setQtyToBeReturned(rs.getInt("qty_to_be_returned"));
				borrow.setReturnedQty(rs.getInt("returned_qty"));
				borrow.setAllReturned(rs.getBoolean("is_all_returned"));
				
				borrow.setStuName(rs.getString("stu_name"));

				StudentModel student = new StudentModel();
				student.setStuId(borrow.getStuId());

				borrowlist.add(borrow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowlist;
	}

//	public BorrowModel getOneBorrowById(BorrowModel data) {
//		String query = "SELECT * FROM lib.borrow WHERE borrow_id=?";
//
//		BorrowModel borrow = new BorrowModel();
//
//		PreparedStatement ps;
//		try {
//			ps = (PreparedStatement) con.prepareStatement(query);
//			ps.setString(1, data.getBorrowId());
//
//			ResultSet rs = ps.executeQuery();
//
//			if (rs.next()) {
//
//				borrow.setBorrowId(rs.getString("borrow_id"));
//				borrow.setStuId(rs.getString("stu_id"));
//				borrow.setBorrowAt(rs.getDate("borrowed_at"));
//				borrow.setBorrowQty(rs.getInt("borrowed_qty"));
//				borrow.setQtyToBeReturned(rs.getInt("qty_to_be_returned"));
//	borrow.setReturnedQty(rs.getInt("returned_qty"));
//				borrow.setAllReturned(rs.getBoolean("is_all_returned"));
//
//				StudentModel student = new StudentModel();
//				student.setStuId(borrow.getStuId());
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return borrow;
//	}
//

	// static method
	public static BorrowModel getOneBorrowById(String id) {
		String query = "SELECT * FROM lib.borrow WHERE borrow_id=?";

		BorrowModel borrow = new BorrowModel();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				borrow.setBorrowId(rs.getString("borrow_id"));
				borrow.setStuId(rs.getString("stu_id"));
				borrow.setBorrowAt(rs.getDate("borrowed_at"));
				borrow.setBorrowQty(rs.getInt("borrowed_qty"));
				borrow.setQtyToBeReturned(rs.getInt("qty_to_be_returned"));
				borrow.setReturnedQty(rs.getInt("returned_qty"));
				borrow.setAllReturned(rs.getBoolean("is_all_returned"));

				StudentModel student = new StudentModel();
				student.setStuId(borrow.getStuId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrow;
	}

	public List<BorrowModel> getAllUnfinishedBorrowsByStudentId(String stuId) {
		String query = "SELECT * FROM lib.borrow WHERE stu_id=? AND is_all_returned=? ORDER BY borrow_id DESC";
		List<BorrowModel> borrowlist = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, stuId);
			ps.setBoolean(2, false);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BorrowModel borrow = new BorrowModel();

				borrow.setBorrowId(rs.getString("borrow_id"));
				borrow.setStuId(rs.getString("stu_id"));
//				borrow.setBorrowAt(rs.getString("borrowed_at"));
				borrow.setBorrowAt(rs.getDate("borrowed_at"));
				borrow.setBorrowQty(rs.getInt("borrowed_qty"));
				borrow.setQtyToBeReturned(rs.getInt("qty_to_be_returned"));
				borrow.setReturnedQty(rs.getInt("returned_qty"));
				borrow.setAllReturned(rs.getBoolean("is_all_returned"));

				StudentModel student = new StudentModel();
				student.setStuId(borrow.getStuId());

				borrowlist.add(borrow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowlist;
	}
	
	public static int updateReturnQty(BorrowModel borrow) {
		String query = "UPDATE lib.borrow SET qty_to_be_returned=?, returned_qty=?, is_all_returned=? WHERE borrow_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setInt(1, borrow.getQtyToBeReturned());
			ps.setInt(2, borrow.getReturnedQty());
			ps.setBoolean(3, borrow.isAllReturned());
			ps.setString(4, borrow.getBorrowId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static Date getBorrowDate(String id) {
		String query = "SELECT * FROM lib.borrow WHERE borrow_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getDate("borrowed_at");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<BorrowModel> getAllBorrowsByDate(java.sql.Date date) {
		String query = "SELECT * FROM lib.borrow JOIN lib.student ON borrow.stu_id=student.stu_id WHERE borrow.borrowed_at=? ORDER BY borrow_id DESC";
		List<BorrowModel> borrowlist = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setDate(1, date);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BorrowModel borrow = new BorrowModel();

				borrow.setBorrowId(rs.getString("borrow_id"));
				borrow.setStuId(rs.getString("stu_id"));
//				borrow.setBorrowAt(rs.getString("borrowed_at"));
				borrow.setBorrowAt(rs.getDate("borrowed_at"));
				borrow.setBorrowQty(rs.getInt("borrowed_qty"));
				borrow.setQtyToBeReturned(rs.getInt("qty_to_be_returned"));
				borrow.setReturnedQty(rs.getInt("returned_qty"));
				borrow.setAllReturned(rs.getBoolean("is_all_returned"));
				
				borrow.setStuName(rs.getString("stu_name"));

				StudentModel student = new StudentModel();
				student.setStuId(borrow.getStuId());

				borrowlist.add(borrow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowlist;
	}
	
	public static List<BorrowModel> searchByStudentName(String name) {
		String query = "SELECT * FROM lib.borrow JOIN lib.student ON borrow.stu_id=student.stu_id WHERE stu_name LIKE ? ORDER BY stu_name ASC";
		List<BorrowModel> borrowlist = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BorrowModel borrow = new BorrowModel();

				borrow.setBorrowId(rs.getString("borrow_id"));
				borrow.setStuId(rs.getString("stu_id"));
//				borrow.setBorrowAt(rs.getString("borrowed_at"));
				borrow.setBorrowAt(rs.getDate("borrowed_at"));
				borrow.setBorrowQty(rs.getInt("borrowed_qty"));
				borrow.setQtyToBeReturned(rs.getInt("qty_to_be_returned"));
				borrow.setReturnedQty(rs.getInt("returned_qty"));
				borrow.setAllReturned(rs.getBoolean("is_all_returned"));
				
				borrow.setStuName(rs.getString("stu_name"));

				StudentModel student = new StudentModel();
				student.setStuId(borrow.getStuId());

				borrowlist.add(borrow);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrowlist;
	}
}