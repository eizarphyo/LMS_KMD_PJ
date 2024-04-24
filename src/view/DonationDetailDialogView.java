package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;


import components.MyBtn;
import components.TxtFieldFocusListener;
import controller.DonationController;
import controller.DonationDetailController;
import model.DonationDetailModel;
import model.DonationModel;
import utilities.AutoID;
import utilities.InputValidator;
import utilities.MyComboBox;
import utilities.MyTblFunctions;
import utilities.MyDate;


public class DonationDetailDialogView extends JDialog {
//	private JPanel contentPanel;
	private static final long serialVersionUID = 1L;
	
	private static JButton btnAdd;
	private static JComboBox<String> cboDonationId;
	private static JComboBox<String> cboBookId;
	private static JTextField txtQty;
	private static DonationDetailDialogView dialog;
	private static boolean update = false;
	private static Border b = BorderFactory.createLineBorder(Color.black);
	private final JPanel contentPanel = new JPanel();
	
	private JLabel lblDonation;
	private JLabel lblDonator;
	private JLabel lblDonateDate;
	private JLabel lblDonatorId;
	private JLabel lblDate;
	private JLabel lblBook;
	private JLabel lblQty;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new DonationDetailDialogView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showDialog() {
		try {
			update = true;

			dialog = new DonationDetailDialogView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			DonationDetailController ctl = new DonationDetailController();
			DonationDetailModel dd = new DonationDetailModel();

			dd = ctl.getOneDonationById(dd);

			cboDonationId.setSelectedItem(dd.getDonationId());
			cboBookId.setSelectedItem(dd.getTitle());
			txtQty.setText(dd.getQty() + "");


			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Create the panel.
	 */
	public DonationDetailDialogView() {
		setTitle("Donation Detail Dialog View");
		
		setBounds(120, 100, 549, 576);
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbl = new JLabel("Donation Detail Entry");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl.setBounds(10, 10, 416, 25);
		contentPanel.add(lbl);
		
		lblDonation = new JLabel("Donation ID:");
		lblDonation.setBounds(85, 69, 112, 25);
		lblDonation.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPanel.add(lblDonation);

		cboDonationId = new JComboBox<String>();
		cboDonationId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboDonationId.getSelectedIndex()>0) {
					DonationDetailController ctl = new DonationDetailController();
					DonationDetailModel dd = new DonationDetailModel();
					
					ArrayList<DonationDetailModel> list = new ArrayList<DonationDetailModel> ();
					
					dd.setDonationId(cboDonationId.getSelectedItem().toString());
					
					try {
						list =(ArrayList<DonationDetailModel>) ctl.searchDonation(dd);
						for(DonationDetailModel detail:list) {
							lblDonatorId.setText(detail.getDonatorId());
							lblDate.setText(detail.getDate());
		
						}
					}catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		cboDonationId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboDonationId.setBounds(260, 66, 200, 26);
		cboDonationId.setBorder(b);
		contentPanel.add(cboDonationId);
		
		lblDonator = new JLabel("Donator ID:");
		lblDonator.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDonator.setBounds(85, 131, 112, 25);
		contentPanel.add(lblDonator);
		
		lblDonateDate = new JLabel("Date:");
		lblDonateDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDonateDate.setBounds(85, 193, 112, 25);
		contentPanel.add(lblDonateDate);
		
		lblDonatorId = new JLabel("");
		lblDonatorId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDonatorId.setBounds(260, 131, 200, 25);
		lblDonatorId.setBorder(b);
		contentPanel.add(lblDonatorId);
		
		lblDate = new JLabel("");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDate.setBounds(260, 193, 200, 25);
		lblDate.setBorder(b);
		contentPanel.add(lblDate);
		
		lblBook = new JLabel("Book ID:");
		lblBook.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBook.setBounds(85, 268, 112, 25);
		contentPanel.add(lblBook);
		
		cboBookId = new JComboBox<String>();
		cboBookId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboBookId.setBorder(b);
		cboBookId.setBounds(260, 267, 200, 26);
		contentPanel.add(cboBookId);
		
		lblQty = new JLabel("Book Qty:");
		lblQty.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblQty.setBounds(85, 337, 112, 25);
		contentPanel.add(lblQty);
		
		txtQty = new JTextField();
		txtQty.setBounds(260, 337, 200, 27);
		txtQty.setBorder(b);
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
				
				detail.setDonationId(cboDonationId.getSelectedItem().toString());
//				detail.setDonationId(ctl.searchDonation(cboDonationId.getSelectedItem().toString()));
				detail.setBookId(ctl.getIdByName(cboBookId.getSelectedItem().toString()));
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
		btnAdd.setBorder(b);
		btnAdd.setBounds(371, 428, 89, 25);
		contentPanel.add(btnAdd);
		
		fillComboBoxes();

	}

	private boolean hasValidInputs() {
		if (cboDonationId.getSelectedIndex() < 1 || cboBookId.getSelectedIndex() < 1
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
		MyComboBox.fillComboItems("donation", "donation_id", cboDonationId);
		MyComboBox.fillComboItems("book", "title", cboBookId);
	}
	
}
