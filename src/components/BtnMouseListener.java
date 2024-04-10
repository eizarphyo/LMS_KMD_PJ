package components;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import utilities.LibColors;
import view.Test;

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
				
				if(btn.equals(Test.obj)) {
					btn.setOpaque(true);
					return;
				}
				btn.setOpaque(false);
//				btn.setBackground(LibColors.PRIMARY_BG);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				btn.setOpaque(true);
				btn.setBackground(LibColors.PRIMARY_ACCENT);
				for(JButton b: Test.getBtns()) {
					if(!btn.equals(b)) {
						b.setOpaque(false);
					}
				}
			}

			public void mouseReleased(MouseEvent e) {
				btn.setOpaque(true);
				btn.setBackground(LibColors.PRIMARY_BG);
			}

			public void mouseClicked(MouseEvent e) {
				btn.setOpaque(true);
			}
		};
		return adapter;
	}
}
