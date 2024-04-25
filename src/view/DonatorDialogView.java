package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.MyBtn;
import components.TxtFieldFocusListener;
import controller.DonatorController;

import model.DonatorModel;
import utilities.AutoID;
import utilities.InputValidator;
import utilities.LibColors;
import utilities.MyTblFunctions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class DonatorDialogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private static DonatorDialogView dialog;
	private final JPanel contentPanel = new JPanel();
	private static JTextField txtName;
	private static JTextField txtPhone;
	private static JTextField txtEmail;
	private static JTextArea txtAddress;
	private JButton btnAdd;
	private static boolean update = false;


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			dialog = new DonatorDialogView();
//			dialog.setTitle(AutoID.getPK("donator_id", "donator", "DNT-"));
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static void showDialog() {
		try {
			update = false;

			dialog = new DonatorDialogView();
			dialog.setTitle(AutoID.getPK("donator_id", "donator", "DNT-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDialog(String id) {
		try {
			update = true;
		
			dialog = new DonatorDialogView();
			dialog.setTitle(id);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			DonatorController ctl = new DonatorController();
			DonatorModel donator = new DonatorModel();

			donator.setDonatorId(id);
			donator = ctl.getOneDonatorById(donator);

			txtName.setText(donator.getDonatorName());
			txtEmail.setText(donator.getEmail());
			txtPhone.setText(donator.getPhone());
			txtAddress.setText(donator.getAddress());

			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DonatorDialogView() {
		setBounds(100, 100, 450, 370);
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblName.setBounds(55, 63, 100, 20);
		lblName.requestFocus();
		contentPanel.add(lblName);

		txtName = new JTextField();
		txtName.addFocusListener(TxtFieldFocusListener.getFocusListener(txtName));

		txtName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtName.setBounds(179, 63, 200, 25);
		contentPanel.add(txtName);
		txtName.setColumns(10);

		JLabel lblPhone = new JLabel("Phone No:");
		lblPhone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPhone.setBounds(55, 109, 100, 20);
		contentPanel.add(lblPhone);

		txtPhone = new JTextField();
		 txtPhone.addFocusListener(TxtFieldFocusListener.getFocusListener(txtPhone));

		txtPhone.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtPhone.setBounds(179, 109, 200, 25);
		contentPanel.add(txtPhone);
		txtPhone.setColumns(10);

		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblEmail.setBounds(55, 160, 100, 20);
		contentPanel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.addFocusListener(TxtFieldFocusListener.getFocusListener(txtEmail));

		txtEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtEmail.setBounds(179, 160, 200, 25);
		contentPanel.add(txtEmail);
		txtEmail.setColumns(10);

		JLabel lblAddress = new JLabel("Address:");
		lblAddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAddress.setBounds(55, 211, 100, 20);
		contentPanel.add(lblAddress);
		
		txtAddress = new JTextArea();
		txtAddress.addFocusListener(TxtFieldFocusListener.getFocusListener(txtAddress));
		txtAddress.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
		txtAddress.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtAddress.setBounds(179, 211, 200, 50);
		contentPanel.add(txtAddress);

		btnAdd = new JButton(update ? "Update" : "Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!hasValidInputs()) {
					return;
				}

				DonatorController ctl = new DonatorController();
				DonatorModel donator = new DonatorModel();
				donator.setDonatorId(dialog.getTitle());
				donator.setDonatorName(txtName.getText());

				donator.setPhone(txtPhone.getText());
				donator.setEmail(txtEmail.getText());
				donator.setAddress(txtAddress.getText());

				if (ctl.hasDuplicateName(donator) && !update) {
					JOptionPane.showMessageDialog(null, "Donator Name Already Exists!");
					return;
				}

				int ok = update ? ctl.update(donator) : ctl.insert(donator);

				if (ok == 1) {
					MyTblFunctions.updateDonatorsTable();
					JOptionPane.showMessageDialog(null, "Success!");
					dispose();
					return;
				}

				JOptionPane.showMessageDialog(null, "Something Went Wrong");

			}
		});
		MyBtn.changeMyBtnStyle(btnAdd);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAdd.setBounds(175, 292, 89, 27);
		contentPanel.add(btnAdd);

		JLabel lbl = new JLabel("Donator Entry");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl.setBounds(10, 10, 416, 25);
		contentPanel.add(lbl);

		

	}

	private boolean hasValidInputs() {
		if (txtName.getText().isBlank() || txtPhone.getText().isBlank() || txtEmail.getText().isBlank()
				|| txtAddress.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Please fill the required fields first");
			txtName.requestFocus();
			return false;
		} else if (!InputValidator.hasValidEmailFormat(txtEmail.getText())) {
			JOptionPane.showMessageDialog(null, "Invalid Email Format.\nRequired Format: xxxx@xx.com");
			return false;
		}
		else if (!InputValidator.isAllDigits(txtPhone.getText())) {
			JOptionPane.showMessageDialog(null, "Phone must be number only");
			txtPhone.requestFocus();
			txtPhone.selectAll();
			return false;
		} else if (txtPhone.getText().length() != 11) {
			JOptionPane.showMessageDialog(null, "Invalid Phone No\nRequired Format: 09XXXXXXXXX");
			txtPhone.requestFocus();
			txtPhone.selectAll();
			return false;
		}
		return true;
	}

}
