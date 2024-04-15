package controller;

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
		String query = "INSERT INTO lib.borrow (borrow_id,stu_id,borrowed_at,borrowed_qty, qty_to_be_returned, is_all_returned) VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, borrow.getBorrowId());
			ps.setString(2, borrow.getStuId());
			ps.setDate(3, new java.sql.Date(borrow.getBorrowAt().getTime()));
			ps.setInt(4, borrow.getBorrowQty());
			ps.setInt(5, borrow.getQtyToBeReturned());
			ps.setBoolean(6, borrow.isAllReturned());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(BorrowModel borrow) {
		String query = "UPDATE lib.borrow SET stu_id=?, borrowed_at=?, borrowed_qty=?, qty_to_be_returned=?, is_all_returned=? WHERE borrow_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, borrow.getStuId());
			ps.setDate(2, new java.sql.Date(borrow.getBorrowAt().getTime()));
			ps.setInt(3, borrow.getBorrowQty());
			ps.setInt(4, borrow.getQtyToBeReturned());
			ps.setBoolean(5, borrow.isAllReturned());
			ps.setString(6, borrow.getBorrowId());

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
		String query = "SELECT * FROM lib.return ORDER BY return_id DESC";
		List<BorrowModel> borrowlist = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BorrowModel borrow = new BorrowModel();

				borrow.setBorrowId(rs.getString("borrow_id"));
				borrow.setStuId(rs.getString("stu_id"));
//				borrow.setBorrowAt(rs.getString("borrowed_at"));
				borrow.setBorrowAt(rs.getDate("borrowed_at"));
				borrow.setBorrowQty(rs.getInt("borrowed_qty"));
				borrow.setQtyToBeReturned(rs.getInt("qty_to_be_returned"));
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
				borrow.setBorrowAt(rs.getDate("borrowed_at"));
				borrow.setBorrowQty(rs.getInt("borrowed_qty"));
				borrow.setQtyToBeReturned(rs.getInt("qty_to_be_returned"));
				borrow.setAllReturned(rs.getBoolean("is_all_returned"));

				StudentModel student = new StudentModel();
				student.setStuId(borrow.getStuId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return borrow;
	}

}
