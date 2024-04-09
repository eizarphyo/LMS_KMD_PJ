package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.BtnMouseListener;
import components.MyBtn;
import controller.AuthorController;
import model.AuthorModel;
import utilities.AutoID;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AuthorDiglogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtName;
	private static AuthorDiglogView dialog;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			dialog = new AuthorDiglogView();
//			dialog.setTitle(AutoID.getPK("author_id", "author", "AUT-"));
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public AuthorDiglogView() {
		setFont(new Font("Dialog", Font.PLAIN, 11));
		setBounds(100, 100, 300, 166);
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);

		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblname = new JLabel("Author Name:");
		lblname.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblname.setBounds(10, 11, 266, 25);
		contentPanel.add(lblname);

		txtName = new JTextField();
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtName.setBounds(10, 47, 266, 25);
		contentPanel.add(txtName);
		txtName.setColumns(10);

		JButton btnAdd = new JButton("Add");
		MyBtn.changeMyBtnStyle(btnAdd);

		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtName.getText().isBlank()) {
					txtName.requestFocus();
					return;
				}

				AuthorController ctl = new AuthorController();
				AuthorModel author = new AuthorModel();
				author.setId(dialog.getTitle());
				author.setName(txtName.getText());

				if (ctl.hasDuplicateName(author)) {
					JOptionPane.showMessageDialog(null, "Author Already Exists!");
					txtName.requestFocus();
					txtName.selectAll();
					return;
				} else if (ctl.insert(author) == 1) {
					JOptionPane.showMessageDialog(null, "Success!");
					dispose();
					return;
				}
				JOptionPane.showMessageDialog(null, "Something Went Wrong");
				txtName.requestFocus();
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAdd.setBounds(109, 92, 75, 25);
		contentPanel.add(btnAdd);
	}

	public static void showDialog() {
		try {
			dialog = new AuthorDiglogView();
			dialog.setTitle(AutoID.getPK("author_id", "author", "AUT-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
