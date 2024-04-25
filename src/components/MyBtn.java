package components;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import utilities.LibColors;

public class MyBtn {
	
	public static void changeMyBtnStyle(JButton btn) {
		btn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		
		btn.setBackground(btn.isEnabled() ? LibColors.PRIMARY_BG : Color.LIGHT_GRAY);
		btn.setContentAreaFilled(false);
		btn.setOpaque(true);
		
		btn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, false));
//		btn.setBorder(BorderFactory.createBevelBorder(0));
		btn.setFocusPainted(false); // Remove focus indication

		btn.addMouseListener(BtnMouseListener.getListener(btn));
	}
	
	public static void changeMySideNaveStyle(JButton btn) {
//		btn.setOpaque(false); // Make background transparent
        btn.setContentAreaFilled(false); // Make content area transparent
//        btn.setBorderPainted(false); // Hide the border
        btn.setFocusPainted(false); // Remove focus indication
        btn.setBorder(BorderFactory.createBevelBorder(0));
//        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); // Set max size equal to max size of the vertical box
        btn.setFont(new Font("Tahoma", Font.PLAIN, 14));

        btn.addMouseListener(BtnMouseListener.getSideNavListener(btn));
	}
}
