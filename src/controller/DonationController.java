package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.AuthorModel;
import model.BorrowModel;
import model.DonationModel;
import model.DonatorModel;
import model.StudentModel;

public class DonationController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(DonationModel donation) {
		String query = "INSERT INTO lib.donation (donation_id, donator_id, date) VALUES (?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, donation.getDonationId());
			ps.setString(2, donation.getDonatorId());
			ps.setString(3, donation.getDate());
			

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(DonationModel donation)  {
		String query = "UPDATE lib.donation SET donator_id=?,date=? WHERE donation_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, donation.getDonatorId());
			ps.setString(2, donation.getDate());
			ps.setString(5, donation.getDonatorId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(DonationModel donation) {
		String query = "DELETE FROM lib.donation WHERE donation_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, donation.getDonationId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<DonationModel> getAllDonation() {
		String query = "SELECT * FROM lib.donation ORDER BY donation_id DESC";
		List<DonationModel> donations = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DonationModel donation = new DonationModel();

				donation.setDonationId(rs.getString("donation_id"));
				donation.setDonationId(rs.getString("donator"));
				donation.setDate(rs.getString("date"));
				
				DonatorModel donator = new DonatorModel();
				donator.setDonatorId(donation.getDonatorId());

				donations.add(donation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return donations;
	}

	public DonationModel getOneDonationById(DonationModel data) {
		String query = "SELECT * FROM lib.donation WHERE donation_id=?";
		
		DonationModel donation = new DonationModel();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getDonationId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				donation.setDonationId(rs.getString("donation_id"));
				donation.setDonationId(rs.getString("donator"));
				donation.setDate(rs.getString("date"));
				
				DonatorModel donator = new DonatorModel();
				donator.setDonatorId(donation.getDonatorId());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return donation;
	}
}
