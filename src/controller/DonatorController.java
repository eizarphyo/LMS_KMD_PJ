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
import model.DonatorModel;

public class DonatorController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(DonatorModel donator) {
		String query = "INSERT INTO lib.donator (donator_id, donator_name, phone, email, address) VALUES (?, ?, ?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, donator.getDonatorId());
			ps.setString(2, donator.getDonatorName());
			ps.setString(3, donator.getPhone());
			ps.setString(4, donator.getEmail());
			ps.setString(5, donator.getAddress());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(DonatorModel donator) {
		String query = "UPDATE lib.donator SET donator_name=?,phone=?,email=?,address=? WHERE donator_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, donator.getDonatorName());
			ps.setString(2, donator.getPhone());
			ps.setString(3, donator.getEmail());
			ps.setString(4, donator.getAddress());
			ps.setString(5, donator.getDonatorId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(DonatorModel donator) {
		String query = "DELETE FROM lib.donator WHERE donator_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, donator.getDonatorId());

			return ps.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Delete Fails!\nThis Donator has donated books.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<DonatorModel> getAllDonators() {
		String query = "SELECT * FROM lib.donator ORDER BY donator_id DESC";
		List<DonatorModel> donators = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DonatorModel donator = new DonatorModel();

				donator.setDonatorId(rs.getString("donator_id"));
				donator.setDonatorName(rs.getString("donator_name"));
				donator.setPhone(rs.getString("phone"));
				donator.setEmail(rs.getString("email"));
				donator.setAddress(rs.getString("address"));

				donators.add(donator);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return donators;
	}

	public DonatorModel getOneDonatorById(DonatorModel data) {
		String query = "SELECT * FROM lib.donator WHERE donator_id=?";

		DonatorModel donator = new DonatorModel();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getDonatorId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				donator.setDonatorId(rs.getString("donator_id"));
				donator.setDonatorName(rs.getString("donator_name"));
				donator.setPhone(rs.getString("phone"));
				donator.setEmail(rs.getString("email"));
				donator.setAddress(rs.getString("address"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return donator;
	}

	public List<DonatorModel> searchByName(DonatorModel data) {
		String query = "SELECT * FROM lib.donator WHERE donator_name LIKE ? ORDER BY author_id DESC";
		List<DonatorModel> donators = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getDonatorName());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DonatorModel donator = new DonatorModel();

				donator.setDonatorId(rs.getString("donator_id"));
				donator.setDonatorName(rs.getString("donator_name"));
				donator.setPhone(rs.getString("phone"));
				donator.setEmail(rs.getString("email"));
				donator.setAddress(rs.getString("address"));

				donators.add(donator);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return donators;
	}

	public boolean hasDuplicateName(DonatorModel author) {
		String query = "SELECT * FROM lib.donator WHERE donator_name=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, author.getDonatorName());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getIdByName(String name) {
		String query = "SELECT * FROM lib.donator WHERE donator_name= ?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("donator_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
