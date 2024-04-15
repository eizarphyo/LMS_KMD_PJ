package components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyImageLabel extends JPanel {
	private JLabel textLabel;
	private JLabel imageLabel1;
	private JLabel imageLabel2;
	private JCheckBox checkBox;

	public MyImageLabel(String text, ImageIcon icon1, ImageIcon icon2, JCheckBox checkBox) {
		setLayout(new GridBagLayout());

		textLabel = new JLabel(text);

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