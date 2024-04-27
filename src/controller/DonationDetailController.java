package controller;

import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.BookModel;
import model.DonationDetailModel;
import model.DonationModel;

public class DonationDetailController {
	private static Connection con;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(DonationDetailModel detail) {
		String query = "INSERT INTO lib.donation_details (donation_id, book_id, donated_qty) VALUES (?,?,?)";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

			ps.setString(1, detail.getDonationId());
			ps.setString(2, detail.getBookId());
			ps.setInt(3, detail.getQty());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(DonationDetailModel detail) {
		String query = "UPDATE lib.donation_details SET donated_qty=? WHERE donation_id=? AND book_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

			ps.setInt(1, detail.getQty());
			ps.setString(2, detail.getDonationId());
			ps.setString(3, detail.getBookId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(DonationDetailModel detail) {
		String query = "DELETE FROM lib.donation_details WHERE donation_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

			ps.setString(1, detail.getDonationId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int deleteByBookIdAndDonationId(DonationDetailModel detail) {
		String query = "DELETE FROM lib.donation_details WHERE book_id=? AND donation_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

			ps.setString(1, detail.getBookId());
			ps.setString(2, detail.getDonationId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public boolean hasDuplicate(DonationDetailModel detail) {
		String query = "SELECT * FROM lib.donation_details WHERE donation_id=? AND book_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, detail.getDonationId());
			ps.setString(2, detail.getBookId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<DonationDetailModel> getAllDonation() {
		String query = "SELECT donation_details.*, donation.*, book.title FROM lib.donation_details\r\n"
				+ "FULL JOIN lib.donation ON donation_details.donation_id = donation.donation_id\r\n"
				+ "INNER JOIN lib.book ON donation_details.book_id = book.book_id\r\n"
				+ "ORDER BY donation_details.donation_id ASC;";
		List<DonationDetailModel> detail = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DonationDetailModel dd = new DonationDetailModel();
				dd.setDonationId(rs.getString("donation_id"));
				dd.setDonatorId(rs.getString("donator_id"));
				dd.setDate(rs.getString("date"));
				dd.setBookId(rs.getString("book_id"));
				dd.setTitle(rs.getString("title"));
				dd.setQty(rs.getInt("donated_qty"));

				detail.add(dd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return detail;
	}

	public int getDonatedQtyByBookIdAndDonationId(DonationDetailModel detail) {
		String query = "SELECT * FROM lib.donation_details WHERE donation_id=? AND book_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

			ps.setString(1, detail.getDonationId());
			ps.setString(2, detail.getBookId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getInt("donated_qty");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public DonationDetailModel getOneDonationById(DonationDetailModel detail) {
		String query = "SELECT donation_details.*, donation.*, book.title FROM lib.donation_details\r\n"
				+ "FULL JOIN lib.donation ON donation_details.donation_id = donation.donation_id\r\n"
				+ "INNER JOIN lib.book ON donation_details.book_id = book.book_id\r\n"
				+ "ORDER BY donation.donation_id ASC;";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, detail.getDonationId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				DonationDetailModel dd = new DonationDetailModel();
				dd.setDonationId(rs.getString("donation_id"));
				dd.setDonatorId(rs.getString("donator_id"));
				dd.setDate(rs.getString("date"));
				dd.setBookId(rs.getString("book_id"));
				dd.setTitle(rs.getString("title"));
				dd.setQty(rs.getInt("donated_qty"));

				return dd;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public DonationDetailModel getOneDonationById(String id) {
//		String query = "SELECT donation_details.*, donation.*, book.title FROM lib.donation_details "
//				+ "FULL JOIN lib.donation ON donation_details.donation_id = donation.donation_id "
//				+ "INNER JOIN lib.book ON donation_details.book_id = book.book_id WHERE donation_id=?"
//				+ "ORDER BY donation.donation_id ASC;";

		String query = "SELECT * FROM lib.donation JOIN lib.donator ON donation.donator_id=donator.donator_id WHERE donation_id=? ORDER BY donation_id ASC;";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				DonationDetailModel dd = new DonationDetailModel();
				dd.setDonationId(rs.getString("donation_id"));
				dd.setDonatorId(rs.getString("donator_id"));
//				dd.setDonatorName(rs.getString("donator_name"));
				dd.setDate(rs.getString("date"));
//				dd.setBookId(rs.getString("book_id"));
//				dd.setTitle(rs.getString("title"));
				dd.setQty(rs.getInt("total_qty"));

				return dd;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getIdByName(String name) {
		String query = "SELECT * FROM lib.book WHERE title=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("book_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<DonationDetailModel> getAllDonationsById(String id) {
		String query = "SELECT * FROM donation_details JOIN lib.book ON donation_details.book_id=book.book_id JOIN author on book.author_id=author.author_id JOIN donation ON donation_details.donation_id=donation.donation_id WHERE donation_details.donation_id=? ORDER BY book.title ASC;";
		List<DonationDetailModel> detail = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DonationDetailModel dd = new DonationDetailModel();
				dd.setDonationId(rs.getString("donation_id"));
				dd.setDonatorId(rs.getString("donator_id"));
				dd.setDate(rs.getString("date"));
				dd.setBookId(rs.getString("book_id"));
				dd.setTitle(rs.getString("title"));
				dd.setQty(rs.getInt("donated_qty"));
				dd.setAuthorName(rs.getString("author_name"));

				detail.add(dd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return detail;
	}
	
	public List<DonationDetailModel> getAllDonationsByDonationIdAndBookId(DonationDetailModel data) {
		String query = "SELECT * FROM lib.donation_details WHERE donation_details.donation_id =? AND donation_details.book_id=? ORDER BY donation_details.donation_id ASC;";
		List<DonationDetailModel> detail = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getDonationId());
			ps.setString(2, data.getBookId());
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				DonationDetailModel dd = new DonationDetailModel();
				dd.setDonationId(rs.getString("donation_id"));
				dd.setDonatorId(rs.getString("donator_id"));
				dd.setDate(rs.getString("date"));
				dd.setBookId(rs.getString("book_id"));
				dd.setTitle(rs.getString("title"));
				dd.setQty(rs.getInt("donated_qty"));

				detail.add(dd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return detail;
	}

}
