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
import model.PublisherModel;

public class PublisherController {
	private static Connection con;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(PublisherModel publisher) {
		String query = "INSERT INTO lib.publisher (publisher_id, publisher_name) VALUES (?, ?)";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, publisher.getId());
			ps.setString(2, publisher.getName());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(PublisherModel publisher) {
		String query = "UPDATE lib.publisher SET publisher_name=? WHERE publisher_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, publisher.getName());
			ps.setString(2, publisher.getId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(PublisherModel publisher) {
		String query = "DELETE FROM lib.publisher WHERE publisher_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, publisher.getId());

			return ps.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			JOptionPane.showMessageDialog(null, "Delete Fails!\nBooks exist with this Publisher.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public List<PublisherModel> getAllPublishers() {
		String query = "SELECT * FROM lib.publisher ORDER BY publisher_id DESC";
		List<PublisherModel> publishers = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				PublisherModel p = new PublisherModel();

				p.setId(rs.getString("publisher_id"));
				p.setName(rs.getString("publisher_name"));

				publishers.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
	}

	public PublisherModel getOneByName(PublisherModel publisher) {
		String query = "SELECT * FROM lib.publisher WHERE publisher_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, publisher.getId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				PublisherModel p = new PublisherModel();

				p.setId(rs.getString("publisher_id"));
				p.setName(rs.getString("publisher_name"));

				return p;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean hasDuplicate(PublisherModel publisher) {
		String query = "SELECT * FROM lib.publisher WHERE publisher_name=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, publisher.getName());

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
		String query = "SELECT * FROM lib.publisher WHERE publisher_name=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("publisher_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<PublisherModel> searchByName(String name) {
		String query = "SELECT * FROM lib.publisher WHERE publisher_name LIKE ? ORDER BY publisher_name ASC";
		List<PublisherModel> publishers = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name + "%");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				PublisherModel p = new PublisherModel();

				p.setId(rs.getString("publisher_id"));
				p.setName(rs.getString("publisher_name"));

				publishers.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
	}
}
