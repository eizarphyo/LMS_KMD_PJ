package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.GenreModel;

public class GenreController {
	private static Connection con;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(GenreModel genre) {
		String query = "INSERT INTO lib.genre (genre_id, genre_name) VALUES (?, ?)";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, genre.getId());
			ps.setString(2, genre.getName());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(GenreModel genre) {
		String query = "INSERT INTO lib.genre genre_name=? WHERE genre_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, genre.getName());
			ps.setString(2, genre.getId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<GenreModel> getAllGenres() {
		String query = "SELECT * FROM lib.genre ORDER BY genre_id DESC";
		List<GenreModel> genres = new ArrayList<>();
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				GenreModel g = new GenreModel();
				g.setId(rs.getString("genre_id"));
				g.setName(rs.getString("genre_name"));
				
				genres.add(g);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return genres;
	}
	
	public boolean hasDuplicate(GenreModel genre) {
		String query = "SELECT * FROM lib.genre WHERE genre_name=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, genre.getName());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static String getIdByName(String name) {
		String query = "SELECT * FROM lib.genre WHERE genre_name=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, name);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return rs.getString("genre_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
