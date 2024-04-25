package components;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import utilities.LibColors;
import view.AdminMain;

public class BtnMouseListener extends MouseAdapter {
	public static MouseAdapter getListener(JButton btn) {
		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (btn.isEnabled()) {
					btn.setBackground(LibColors.PRIMARY_ACCENT);
					btn.setBorder(BorderFactory.createLineBorder(Color.GRAY));
//					btn.setBorder(BorderFactory.createBevelBorder(1));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (btn.isEnabled()) {
					btn.setBackground(LibColors.PRIMARY_BG);
					btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
//					btn.setBorder(BorderFactory.createBevelBorder(0));
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (btn.isEnabled()) {
					btn.setBackground(LibColors.PRIMARY_BG);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (btn.isEnabled()) {
					btn.setBackground(LibColors.PRIMARY_ACCENT);
				}
			}
		};
		return adapter;
	}

	public static MouseAdapter getSideNavListener(JButton btn) {
		MouseAdapter adapter = new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btn.setOpaque(true);
				btn.setBackground(LibColors.PRIMARY_BG);
				btn.setBorder(BorderFactory.createEtchedBorder());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btn.setBorder(BorderFactory.createBevelBorder(0, Color.LIGHT_GRAY, Color.GRAY));

				if (btn.equals(AdminMain.obj)) {
					btn.setOpaque(true);
					return;
				}
				btn.setOpaque(false);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				for (JButton b : AdminMain.getBtns()) {
					if (!b.equals(btn)) {
						b.setOpaque(false); // false -> transparent

					} else {
						b.setOpaque(true);
						b.setBackground(LibColors.PRIMARY_ACCENT);

					}
//					System.out.println("Tran> " + b.isOpaque());
				}

//				btn.setOpaque(true);
//				btn.setBackground(LibColors.PRIMARY_ACCENT);
				
			}

			public void mouseReleased(MouseEvent e) {
				btn.setOpaque(true);
				btn.setBackground(LibColors.PRIMARY_BG);
			}
		};
		return adapter;
	}
}
