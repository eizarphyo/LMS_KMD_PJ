package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.AuthorModel;

public class AuthorController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(AuthorModel author) {
		String query = "INSERT INTO lib.author (author_id, author_name) VALUES (?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, author.getId());
			ps.setString(2, author.getName());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(AuthorModel author) {
		String query = "UPDATE lib.author SET author_name=? WHERE author_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, author.getName());
			ps.setString(2, author.getId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(AuthorModel author) {
		String query = "DELETE FROM lib.author WHERE author_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, author.getId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<AuthorModel> getAllAuthors() {
		String query = "SELECT * FROM lib.author ORDER BY author_id DESC";
		List<AuthorModel> authors = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				AuthorModel author = new AuthorModel();

				author.setId(rs.getString("author_id"));
				author.setName(rs.getString("author_name"));

				authors.add(author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}

	public AuthorModel getOneAuthorById(AuthorModel data) {
		String query = "SELECT * FROM lib.author WHERE author_id=?";

		AuthorModel author = new AuthorModel();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				author.setId(rs.getString("author_id"));
				author.setName(rs.getString("author_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return author;
	}

	public List<AuthorModel> searchByName(AuthorModel data) {
		String query = "SELECT * FROM lib.author WHERE author_name LIKE ? ORDER BY author_id DESC";
		List<AuthorModel> authors = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getName());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				AuthorModel author = new AuthorModel();

				author.setId(rs.getString("author_id"));
				author.setName(rs.getString("author_name"));

				authors.add(author);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return authors;
	}

	public boolean hasDuplicateName(AuthorModel author) {
		String query = "SELECT * FROM lib.author WHERE author_name=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, author.getName());

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
		String query = "SELECT * FROM lib.author WHERE author_name=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getString("author_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
