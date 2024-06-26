package utilities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.jdbc.Statement;

import config.DBConfig;

public class AutoID {

	public static ResultSet getOneColDataFromTable(String field, String table) { // same as sqlSelect()
		DBConfig config = new DBConfig();
		try {
			Statement stm = (Statement) config.getConnection().createStatement();
			ResultSet rs = stm.executeQuery("SELECT " + field + " FROM " + table);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getPK(String field, String table, String prefix) {
		
		ResultSet rs = getOneColDataFromTable(field , table);
		ArrayList<String> results = new ArrayList<>();
		
		try {
			while(rs.next()) {
				results.add(rs.getString(field));
			}
			
			if(results.size() > 0) {
				String[] num = results.get(results.size()-1).split("-");
				String incrementedNum = Integer.parseInt(num[1]) + 1 + "";

				switch (incrementedNum.length()) {
				case 1:
					return (num[0] + "-000" + incrementedNum);
				case 2:
					return (num[0] + "-00" + incrementedNum);
				case 3:
					return (num[0] + "-0" + incrementedNum);
				case 4:
					return num[0] + "-" + incrementedNum;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prefix + "0001";
	}
}
