package components;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import utilities.LibColors;

public class BtnMouseListener extends MouseAdapter {

	public static MouseAdapter getListener(JButton btn) {
		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setBackground(LibColors.PRIMARY_ACCENT);
				btn.setBorder(BorderFactory.createEtchedBorder());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn.setBackground(LibColors.PRIMARY_BG);
				btn.setBorder(BorderFactory.createEmptyBorder());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btn.setBackground(LibColors.PRIMARY_BG);
			}

			public void mouseReleased(MouseEvent e) {
				btn.setBackground(LibColors.PRIMARY_ACCENT);
			}
		};
		return adapter;
	}
}
