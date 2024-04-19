package utilities;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

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

			while (rs.next()) {
				combo.addItem(rs.getString(field));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void changeMyCboStyle(JComboBox<String> comboBox) {
		System.out.println(comboBox.isEnabled());
		if(!comboBox.isEnabled()) {
			return;
		}
		// Set the background color of the combo box
		comboBox.setBackground(LibColors.COMBOBOX_BG);

		// Set the foreground (text) color of the combo box
		comboBox.setForeground(new Color(50, 50, 50));

//				// Set the color of the dropdown list background
//				UIManager.put("ComboBox.background", Color.GREEN);
		//
//				// Set the color of the dropdown list foreground (text)
//				UIManager.put("ComboBox.foreground", Color.GRAY);

		// Set the border color of the combo box
		UIManager.put("ComboBox.border", BorderFactory.createLineBorder(LibColors.BORDER));

		// Override the UI defaults for the selected item background
		UIManager.put("ComboBox.selectionBackground", LibColors.PRIMARY_SELECT_BG);

		// Override the UI defaults for the selected item foreground (text) color
		UIManager.put("ComboBox.selectionForeground", Color.BLACK);

		SwingUtilities.updateComponentTreeUI(comboBox);
	}
}
