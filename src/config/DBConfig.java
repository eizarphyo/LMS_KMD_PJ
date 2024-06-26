package config;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class DBConfig {
	private final String CONNECTION = "jdbc:mysql://localhost:3306/lib?useConfigs=maxPerformance";
	private final String PASSWROD = "root";
	
	private static Connection con = null;

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		if (con == null) {
			con = (Connection) DriverManager.getConnection(this.CONNECTION, "root", this.PASSWROD);
		}
		return con;
	}
	
	
}
