package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.DonationDetailModel;
import model.DonatorModel;

public class DonationDetailController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(DonationDetailModel donationdetail) {
		String query = "INSERT INTO lib.donation_details (donation_id, book_id, donated_qty) VALUES (?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, donationdetail.getDonationId());
			ps.setString(2, donationdetail.getBookId());
			ps.setString(3, donationdetail.getDonatedQty());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	
}
