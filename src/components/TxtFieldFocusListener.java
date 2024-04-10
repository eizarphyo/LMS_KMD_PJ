package components;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.BorderFactory;
import javax.swing.JTextField;

import utilities.LibColors;

public class TxtFieldFocusListener {
	public static FocusAdapter getFocusListener(JTextField txt) {
		FocusAdapter adapter = new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txt.setBorder(BorderFactory.createLineBorder(LibColors.BORDER, 2));
			}

			@Override
			public void focusLost(FocusEvent e) {
				txt.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
			}
		};

		return adapter;
	}
}
