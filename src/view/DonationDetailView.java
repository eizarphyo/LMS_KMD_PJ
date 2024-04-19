package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.MyBtn;
import components.TxtFieldFocusListener;
import controller.DonationController;
import controller.BookController;
import controller.DonatorController;
import controller.DonationDetailController;
import model.DonationDetailModel;
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

public class DonationDetailView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JTextField txtQty;
	private static JButton btnAdd;
	private static JComboBox<String> cboDonator;
	private static JComboBox<String> cboBook;
	private static DonationDetailView dialog;
	private static boolean update = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new DonationDetailView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static void showDialog() {
//		try {
//			dialog = new DonationDetailView();
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static void showDialog(String id) {
		try {
			update = true;

			dialog = new DonationDetailView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			DonationDetailController ctl = new DonationDetailController();
			DonationDetailModel dd = new DonationDetailModel();

			dd = ctl.getOneDonationById(dd);

			cboDonator.setSelectedItem(dd.getDonatorId());
			cboBook.setSelectedItem(dd.getBookName());
			txtQty.setText(dd.getQty() + "");


			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DonationDetailView() {
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

		JLabel lblBook = new JLabel("Book:");
		lblBook.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBook.setBounds(55, 159, 100, 20);
		contentPanel.add(lblBook);

		cboBook = new JComboBox<String>();
		cboBook.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboBook.setBounds(179, 160, 200, 20);
		contentPanel.add(cboBook);

		JLabel lblQty = new JLabel("Qty:");
		lblQty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQty.setBounds(55, 221, 100, 20);
		contentPanel.add(lblQty);

		txtQty = new JTextField();
		txtQty.addFocusListener(TxtFieldFocusListener.getFocusListener(txtQty));
		txtQty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtQty.setBounds(179, 221, 200, 20);
		contentPanel.add(txtQty);
		txtQty.setColumns(10);

		btnAdd = new JButton(update ? "Update" : "Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!hasValidInputs()) {
					return;
				}

				DonationDetailController ctl = new DonationDetailController();
				DonationDetailModel detail = new DonationDetailModel();
				
				detail.setDonationId(ctl.getIdById(cboDonator.getSelectedItem().toString()));
				detail.setBookId(ctl.getIdByName(cboBook.getSelectedItem().toString()));
				detail.setQty(Integer.parseInt(txtQty.getText().toString()));

				if (!update && ctl.hasDuplicate(detail)) {
					JOptionPane.showMessageDialog(null, "Donation Already Exists!");
					return;
				}
				
				int ok = update ? ctl.update(detail) : ctl.insert(detail);
				if (ok == 1) {
					//MyTblFunctions.updateBooksTable();
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

		JLabel lbl = new JLabel("Donation Detail Entry");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl.setBounds(10, 10, 416, 25);
		contentPanel.add(lbl);

		fillComboBoxes();

	}

	private boolean hasValidInputs() {
		if (cboDonator.getSelectedIndex() < 1 || cboBook.getSelectedIndex() < 1
				|| txtQty.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Please fill the required fields first");
			txtQty.requestFocus();
			return false;
		} else if (!InputValidator.isAllDigits(txtQty.getText())) {
			JOptionPane.showMessageDialog(null, "Quantity must be number only");
			txtQty.requestFocus();
			txtQty.selectAll();
			return false;
		} 
		return true;
	}

	private void fillComboBoxes() {
		MyComboBox.fillComboItems("donation", "donator_id", cboDonator);
		MyComboBox.fillComboItems("book", "title", cboBook);
	}
}
