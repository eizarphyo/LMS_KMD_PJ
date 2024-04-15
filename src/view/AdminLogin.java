package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.MyBtn;
import components.TxtFieldFocusListener;
import controller.UserController;
import model.UserModel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminLogin extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JButton btnLogin;
	private JPasswordField txtPass;
	private JTextField txtName;

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

		txtName = new JTextField();
		txtName.addFocusListener(TxtFieldFocusListener.getFocusListener(txtName));
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
		btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				login();
			}
		});

		contentPanel.add(btnLogin);

		txtPass = new JPasswordField();
		txtPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
                }
			}
		});
		txtPass.addFocusListener(TxtFieldFocusListener.getFocusListener(txtPass));
		txtPass.setBounds(149, 107, 150, 20);
		contentPanel.add(txtPass);
	}
	
	private void login() {
		if (!txtName.getText().isBlank() && txtPass.getPassword().length > 0) {
			UserController ctl = new UserController();
			UserModel user = new UserModel();
			user.setUsername(txtName.getText());
			user.setPassword(new String(txtPass.getPassword()));
			
			if(ctl.login(user)) {
				Test.showFrame();
				dispose();
			} else {
				JOptionPane.showMessageDialog(null, "Username and Passord combination is incorrect");
			}
		}
	}
}
