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
		String query = "INSERT INTO lib.donation (donation_id, donator_id, date, total_qty) VALUES (?, ?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, donation.getDonationId());
			ps.setString(2, donation.getDonatorId());
			ps.setString(3, donation.getDate());
			ps.setInt(4, donation.getTotalQty());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(DonationModel donation) {
		String query = "UPDATE lib.donation SET donator_id=?,date=?, total_qty=? WHERE donation_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, donation.getDonatorId());
			ps.setString(2, donation.getDate());
			ps.setInt(3, donation.getTotalQty());
			ps.setString(4, donation.getDonationId());

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
		String query = "SELECT * FROM lib.donation JOIN lib.donator ON donation.donator_id=donator.donator_id ORDER BY donation_id DESC";
		List<DonationModel> donations = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DonationModel donation = new DonationModel();

				donation.setDonationId(rs.getString("donation_id"));
				donation.setDonatorId(rs.getString("donator_id"));
				donation.setDate(rs.getString("date"));
				donation.setTotalQty(rs.getInt("total_qty"));
				donation.setDonatorName(rs.getString("donator_name"));

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
		String query = "SELECT * FROM lib.donation JOIN lib.donator ON donation.donator_id=donator.donator_id WHERE donation_id=?";

		DonationModel donation = new DonationModel();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getDonationId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				donation.setDonationId(rs.getString("donation_id"));
				donation.setDonatorName(rs.getString("donator_name"));
				donation.setDate(rs.getString("date"));
				donation.setTotalQty(rs.getInt("total_qty"));

				DonatorModel donator = new DonatorModel();
				donator.setDonatorId(donation.getDonatorId());

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return donation;
	}

	public static List<DonationModel> searchByDonatorName(String name) {
		String query = "SELECT * FROM lib.donation JOIN lib.donator ON donation.donator_id=donator.donator_id WHERE donator_name LIKE ? ORDER BY donator_name ASC";
		List<DonationModel> donations = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DonationModel donation = new DonationModel();

				donation.setDonationId(rs.getString("donation_id"));
				donation.setDonatorId(rs.getString("donator_id"));
				donation.setDate(rs.getString("date"));
				donation.setTotalQty(rs.getInt("total_qty"));
				donation.setDonatorName(rs.getString("donator_name"));

				DonatorModel donator = new DonatorModel();
				donator.setDonatorId(donation.getDonatorId());

				donations.add(donation);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return donations;
	}
}
