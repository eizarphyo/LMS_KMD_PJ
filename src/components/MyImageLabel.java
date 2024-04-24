package components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyImageLabel extends JPanel {
	private JLabel textLabel;
	private JLabel imageLabel1;

	public MyImageLabel(String text, ImageIcon icon1, JCheckBox checkBox) {
		setLayout(new GridBagLayout());

		textLabel = new JLabel(text);
		textLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		textLabel.setPreferredSize(new Dimension(150, 35));

		imageLabel1 = new JLabel(icon1);
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.NORTHWEST; // Align top-left
		add(checkBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 0;
		add(imageLabel1, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER; // Align center
		add(textLabel, gbc);

	}
}