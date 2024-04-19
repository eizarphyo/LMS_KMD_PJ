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
import model.DonatorModel;
import model.GenreModel;
import model.PublisherModel;
import model.ReturnModel;
import model.StudentModel;
import model.UserModel;

public class ReturnController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(ReturnModel returned) {
		String query = "INSERT INTO lib.return (return_id,borrow_id,stu_id,returned_at,returned_qty,late_fine,total_fine) VALUES (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, returned.getReturnId());
			ps.setString(2, returned.getBorrowId());
			ps.setString(3, returned.getStuId());
			ps.setDate(4, returned.getReturnedAt());
			ps.setInt(5, returned.getReturnedQty());
			ps.setInt(6, returned.getLateFine());
			ps.setInt(7, returned.getTotalFine());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(ReturnModel returned)  {
		String query = "UPDATE lib.return SET borrow_id=?, stu_id=?, returned_at=?, returned_qty=?, late_fine=?, total_fine=? WHERE return_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, returned.getBorrowId());
			ps.setString(2, returned.getStuId());
			ps.setDate(3, returned.getReturnedAt());
			ps.setInt(4, returned.getReturnedQty());
			ps.setInt(5, returned.getLateFine());
			ps.setInt(6, returned.getTotalFine());
			ps.setString(7, returned.getReturnId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(ReturnModel returned) {
		String query = "DELETE FROM lib.return WHERE return_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, returned.getReturnId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<ReturnModel> getAllReturn() {
		String query = "SELECT * FROM lib.return ORDER BY return_id DESC";
		List<ReturnModel> returnlist = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ReturnModel returned = new ReturnModel();

				returned.setReturnId(rs.getString("return_id"));
				returned.setBorrowId(rs.getString("borrow_id"));
				returned.setStuId(rs.getString("stu_id"));
				returned.setReturnedAt(rs.getDate("returned_at"));
				returned.setReturnedQty(rs.getInt("returned_qty"));
				returned.setLateFine(rs.getInt("late_fine"));
				returned.setTotalFine(rs.getInt("total_fine"));
				
				BorrowModel borrow = new BorrowModel();
				borrow.setBorrowId(returned.getBorrowId());
				
				StudentModel student = new StudentModel();
				student.setStuId(returned.getStuId());

				returnlist.add(returned);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnlist;
	}
	
	public ReturnModel getOneReturnById(ReturnModel data) {
		String query = "SELECT * FROM lib.return WHERE return_id=?";
		
		ReturnModel returnlist = new ReturnModel();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getReturnId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				returnlist.setReturnId(rs.getString("return_id"));
				returnlist.setBorrowId(rs.getString("borrow_id"));
				returnlist.setStuId(rs.getString("stu_id"));
				returnlist.setReturnedAt(rs.getDate("returned_at"));
				returnlist.setReturnedQty(rs.getInt("returned_qty"));
				returnlist.setLateFine(rs.getInt("late_fine"));
				returnlist.setTotalFine(rs.getInt("total_fine"));
				
				BorrowModel borrow = new BorrowModel();
				borrow.setBorrowId(returnlist.getBorrowId());
				
				StudentModel student = new StudentModel();
				student.setStuId(returnlist.getStuId());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnlist;
	}


}
