package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.MyBtn;
import components.TxtFieldFocusListener;
import controller.DonatorController;
import controller.DonationController;

import model.DonationModel;
import utilities.AutoID;
import utilities.InputValidator;
import utilities.MyComboBox;
import utilities.MyTblFunctions;
import utilities.MyDate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DonationDialogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JButton btnAdd;
	private static JComboBox<String> cboDonator;
	private static DonationDialogView dialog;
	private static boolean update = false;
	private static JLabel lblDonateDate;
	private static JLabel lbldate;
	
	static MyDate da = new MyDate();	
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new DonationDialogView();
			dialog.setTitle(AutoID.getPK("donation_id", "donation", "DON-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static void showDialog() {
//		try {
//			dialog = new DonationDialogView();
//			dialog.setTitle(AutoID.getPK("donation_id", "donation", "DON-"));
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public static void showDialog(String id) {
//		try {
//			update = true;
//
//			dialog = new DonationDialogView();
//			dialog.setTitle(id);
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//
//			DonationController ctl = new DonationController();
//			DonationModel d = new DonationModel();
//			d.setDonationId(id);
//
//			d=ctl.getOneDonationById(d);
//
//			cboDonator.setSelectedItem(d.getDonatorName());
//			lblDonateDate.setText(da.getMySQLDateFormat());
//			
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public DonationDialogView() {
		setBounds(100, 100, 450, 400);
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblDonator = new JLabel("Donator:");
		lblDonator.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDonator.setBounds(55, 95, 100, 20);
		contentPanel.add(lblDonator);

		cboDonator = new JComboBox<String>();
		cboDonator.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboDonator.setBounds(179, 96, 200, 20);
		contentPanel.add(cboDonator);

		lbldate = new JLabel("Donated Date:");
		lbldate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lbldate.setBounds(55, 175, 100, 20);
		contentPanel.add(lbldate);
		
		lblDonateDate = new JLabel("");
		lblDonateDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDonateDate.setBounds(181, 175, 198, 22);
		lblDonateDate.setText(da.getMySQLDateFormat());
		contentPanel.add(lblDonateDate);
			
		
		btnAdd = new JButton(update ? "Update" : "Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!hasValidInputs()) {
					return;
				}

				DonationController ctl = new DonationController();
				DonationModel donation = new DonationModel();
				
				donation.setDonationId(dialog.getTitle());
				donation.setDonatorId(DonatorController.getIdByName(cboDonator.getSelectedItem().toString()));
				donation.setDate(lblDonateDate.getText().toString());
				
				System.out.println(donation.getDonationId());
				System.out.println(donation.getDonatorId());

				
//				int ok = update ? ctl.update(donation) : ctl.insert(donation);
				int ok = ctl.insert(donation);
				if (ok == 1) {
					JOptionPane.showMessageDialog(null, "Success!");
					dispose();
					return;
				}

				JOptionPane.showMessageDialog(null, "Something Went Wrong");

			}
		});
		MyBtn.changeMyBtnStyle(btnAdd);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAdd.setBounds(175, 309, 89, 25);
		contentPanel.add(btnAdd);

		JLabel lbl = new JLabel("Donation Entry");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl.setBounds(10, 10, 416, 25);
		contentPanel.add(lbl);
		
		
		
		fillComboBoxes();
		

	}

	private boolean hasValidInputs() {
		if (cboDonator.getSelectedIndex() < 1) {
			JOptionPane.showMessageDialog(null, "Please select the required fields first");
			cboDonator.requestFocus();
			return false;
		} 
		return true;
	}

	private void fillComboBoxes() {
		MyComboBox.fillComboItems("donator", "donator_name", cboDonator);
		
	}
}
