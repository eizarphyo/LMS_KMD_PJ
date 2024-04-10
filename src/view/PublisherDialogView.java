package view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.MyBtn;
import controller.PublisherController;
import model.PublisherModel;
import utilities.AutoID;
import utilities.MyTblFunctions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PublisherDialogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private static PublisherDialogView dialog;
	private static JTextField txtName;
	private static boolean update = false;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			dialog = new PublisherDialogView();
//			dialog.setTitle(AutoID.getPK("publisher_id", "publisher", "PUB-"));
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public static void showDialog() {
		try {
			dialog = new PublisherDialogView();
			dialog.setTitle(AutoID.getPK("publisher_id", "publisher", "PUB-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showDialog(String id) {
		try {
			update = true;
			
			PublisherController ctl = new PublisherController();
			PublisherModel publisher = new PublisherModel();

			publisher.setId(id);
			publisher = ctl.getOneByName(publisher);
			
			dialog = new PublisherDialogView();
			
			dialog.setTitle(id);
			txtName.setText(publisher.getName());
			txtName.requestFocus();
			txtName.selectAll();
			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public PublisherDialogView() {
		setBounds(100, 100, 300, 174);
		getContentPane().setLayout(null);

		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setBounds(0, 0, 286, 129);
		getContentPane().add(contentPanel);

		JLabel lblPublisherName = new JLabel("Publisher Name:");
		lblPublisherName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPublisherName.setBounds(10, 11, 266, 25);
		contentPanel.add(lblPublisherName);

		txtName = new JTextField();
//		txtName.addFocusListener(TxtFieldFocusListener.getListener(txtName));
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtName.setColumns(10);
		txtName.setBounds(10, 47, 266, 25);
		contentPanel.add(txtName);

		JButton btnAdd = new JButton(update ? "Update" : "Add");
		MyBtn.changeMyBtnStyle(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtName.getText().isBlank()) {
					txtName.requestFocus();
					return;
				}

				PublisherController ctl = new PublisherController();
				PublisherModel publisher = new PublisherModel();

				publisher.setId(dialog.getTitle());
				publisher.setName(txtName.getText());

				if (ctl.hasDuplicate(publisher)) {
					JOptionPane.showMessageDialog(null, "Publisher Already Exists!");
					txtName.requestFocus();
					txtName.selectAll();
					return;
				} 
				
				int ok = update ? ctl.update(publisher) : ctl.insert(publisher);
				
				if (ok == 1) {
					MyTblFunctions.updatePublishersTable();
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

	
}
