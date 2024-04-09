package view;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.MyBtn;
import controller.GenreController;
import model.GenreModel;
import utilities.AutoID;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GenreDialogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtName;
	private static GenreDialogView dialog;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			dialog = new GenreDialogView();
//			dialog.setTitle(AutoID.getPK("genre_id", "genre", "GEN-"));
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Create the dialog.
	 */
	public GenreDialogView() {
		setBounds(100, 100, 300, 170);
		getContentPane().setLayout(null);
		  
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);
		
		{
			JPanel contentPanel = new JPanel();
			contentPanel.setLayout(null);
			contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
			contentPanel.setBounds(0, 0, 286, 129);
			getContentPane().add(contentPanel);
			{
				JLabel lblGenreName = new JLabel("Genre Name:");
				lblGenreName.setFont(new Font("Tahoma", Font.PLAIN, 14));
				lblGenreName.setBounds(10, 11, 266, 25);
				contentPanel.add(lblGenreName);
			}
			{
				txtName = new JTextField();
				txtName.setFont(new Font("Tahoma", Font.PLAIN, 14));
				txtName.setColumns(10);
				txtName.setBounds(10, 47, 266, 25);
				contentPanel.add(txtName);
			}
			{
				JButton btnAdd = new JButton("Add");
				MyBtn.changeMyBtnStyle(btnAdd);
				btnAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(txtName.getText().isBlank()) {
							txtName.requestFocus();
							return;
						}
						
						GenreController ctl = new GenreController();
						GenreModel genre = new GenreModel();
						
						genre.setId(dialog.getTitle());
						genre.setName(txtName.getText());
						
						if(ctl.hasDuplicate(genre)) {
							JOptionPane.showMessageDialog(null, "Genre Already Exists!");
							txtName.requestFocus();
							txtName.selectAll();
							return;
						} else if (ctl.insert(genre) == 1) {
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
	}

	public static void showDialog() {
		try {
			dialog = new GenreDialogView();
			dialog.setTitle(AutoID.getPK("genre_id", "genre", "GEN-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
