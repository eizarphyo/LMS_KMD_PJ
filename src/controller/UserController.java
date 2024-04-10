package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.UserModel;

public class UserController {
	private static Connection con = null;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(UserModel user) {
		String query = "INSERT INTO lib.users (user_id, username, password, role) VALUES (?, ?, ?, ?)";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getUsername());
			ps.setString(3, user.getPassword());
			ps.setString(4, user.getRole());
			

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(UserModel user)  {
		String query = "UPDATE lib.users SET username=?,password=?,role=? WHERE user_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getRole());
			ps.setString(4, user.getUserId());
			
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(UserModel user) {
		String query = "DELETE FROM lib.users WHERE user_id=?";

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, user.getUserId());
			
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public List<UserModel> getAllUsers() {
		String query = "SELECT * FROM lib.users ORDER BY user_id DESC";
		List<UserModel> users = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				UserModel user = new UserModel();

				user.setUserId(rs.getString("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				

				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}

	public UserModel getOneUserById(UserModel data) {
		String query = "SELECT * FROM lib.users WHERE user_id=?";

		UserModel user = new UserModel();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getUserId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				user.setUserId(rs.getString("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<UserModel> searchByName(UserModel data) {
		String query = "SELECT * FROM lib.users WHERE username LIKE ? ORDER BY user_id DESC";
		List<UserModel> users = new ArrayList<>();

		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, data.getUsername());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				UserModel user = new UserModel();

				user.setUserId(rs.getString("user_id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));

				users.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return users;
	}
	
	public boolean hasDuplicateName(UserModel user) {
		String query = "SELECT * FROM lib.users WHERE username=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, user.getUsername());
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean login(UserModel user) {
		String query = "SELECT * FROM lib.users WHERE username=? AND password=?";
		
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getPassword());

			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
