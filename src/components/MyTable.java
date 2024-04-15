package components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import utilities.LibColors;

public class MyTable {
	public static void changeMyTableStyle(JTable tbl) {
		tbl = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());
				return comp;
			}
		};

		tbl.setRowHeight(30);
		tbl.setSelectionBackground(LibColors.PRIMARY_SELECT_BG);
		tbl.setIntercellSpacing(new Dimension(7, 0));

		// Change header height
		JTableHeader header = tbl.getTableHeader();
		Dimension headerPreferredSize = header.getPreferredSize();
		headerPreferredSize.height = 35;
		header.setPreferredSize(headerPreferredSize);

		Font headerFont = header.getFont();
		header.setFont(headerFont.deriveFont(Font.BOLD, 14));
//    header.setBackground(new Color(255,238,178));

		tbl.setFont(new Font("Tahoma", Font.PLAIN, 14));

	}
}
