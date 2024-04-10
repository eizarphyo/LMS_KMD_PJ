package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JComboBox;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;

public class MyComboBox {
	private static Connection con;
	
	static {
		DBConfig config = new DBConfig();
		
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void fillComboItems(String table, String field, JComboBox<String> combo) {
		String query = "SELECT " + field + " FROM " + table;
		
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			combo.removeAllItems();
			combo.addItem("--Select--");
			
			while(rs.next()) {
				combo.addItem(rs.getString(field));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
