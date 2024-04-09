package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.BtnMouseListener;
import components.MyBtn;
import components.TxtFieldFocusListener;
import utilities.LibColors;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class AdminLogin extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnLogin;
	private JPasswordField txtPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			AdminLogin dialog = new AdminLogin();

			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public AdminLogin() {
		setResizable(false);
		setTitle("Login");
//		put("JFrame.activeTitleBackground", primaryBtnBg);
		
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);
		
		setBackground(Color.BLACK);
		setBounds(100, 100, 357, 250);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setForeground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBackground(Color.WHITE);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblWelcome = new JLabel("Welcome!");
			lblWelcome.setBackground(Color.BLACK);
			lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
			lblWelcome.setFont(new Font("Tahoma", Font.BOLD, 16));
			lblWelcome.setBounds(10, 11, 323, 25);
			contentPanel.add(lblWelcome);
		}

		JLabel lblName = new JLabel("Username:");
		lblName.setForeground(Color.BLACK);
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblName.setBounds(53, 63, 75, 20);
		contentPanel.add(lblName);

		JTextField txtName = new JTextField();
		txtName.addFocusListener(TxtFieldFocusListener.getListener(txtName));
//		txtName.addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusGained(FocusEvent e) {
//				txtName.setBorder(BorderFactory.createLineBorder(LibColors.BORDER, 2));
//			}
//
//			@Override
//			public void focusLost(FocusEvent e) {
//				txtName.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
//			}
//		});
		txtName.setBounds(149, 64, 150, 20);
		contentPanel.add(txtName);
		txtName.setColumns(10);

		JLabel lblPass = new JLabel("Password:");
		lblPass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblPass.setBounds(53, 106, 75, 20);
		contentPanel.add(lblPass);
		

		btnLogin = new JButton("Login");
		btnLogin.setBounds(134, 157, 75, 25);
		
		MyBtn.changeMyBtnStyle(btnLogin);

//		btnLogin.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				btnLogin.setBackground(LibColors.PRIMARY_ACCENT);
//				btnLogin.setBorder(BorderFactory.createEtchedBorder());
//			}
//
//			@Override
//			public void mouseExited(MouseEvent e) {
//				btnLogin.setBackground(LibColors.PRIMARY_BG);
//				btnLogin.setBorder(BorderFactory.createEmptyBorder());
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				btnLogin.setBackground(LibColors.PRIMARY_BG);
//			}
//
//			public void mouseReleased(MouseEvent e) {
//				btnLogin.setBackground(LibColors.PRIMARY_ACCENT);
//			}
//		});
		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!txtName.getText().isBlank() && txtPass.getPassword().length > 0) {
					System.out.println(txtPass.getPassword());

				}
			}
		});

		contentPanel.add(btnLogin);

		txtPass = new JPasswordField();
		txtPass.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				txtPass.setBorder(BorderFactory.createLineBorder(LibColors.BORDER, 2));
			}

			@Override
			public void focusLost(FocusEvent e) {
				txtPass.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			}
		});
		txtPass.setBounds(149, 107, 150, 20);
		contentPanel.add(txtPass);
	}
}
