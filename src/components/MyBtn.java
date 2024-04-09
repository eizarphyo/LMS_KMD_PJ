package components;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import utilities.LibColors;

public class MyBtn {
	
	public static void changeMyBtnStyle(JButton btn) {
		btn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btn.setBackground(LibColors.PRIMARY_BG);
		btn.setBorder(BorderFactory.createLineBorder(LibColors.PRIMARY_ACCENT));
		btn.setContentAreaFilled(false);
		btn.setOpaque(true);

		btn.addMouseListener(BtnMouseListener.getListener(btn));
	}
}
