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
import controller.AuthorController;
import controller.BookController;
import controller.GenreController;
import controller.PublisherController;
import model.BookModel;
import utilities.AutoID;
import utilities.InputValidator;
import utilities.LibColors;
import utilities.MyComboBox;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;

public class BookDialogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtTitle;
	private JTextField txtPrice;
	private JButton btnAdd;
	private JComboBox<String> cboGenre;
	private JComboBox<String> cboPublisher;
	private JTextField txtPubYr;
	private JComboBox<String> cboAuthor;
	private static BookDialogView dialog;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new BookDialogView();
			dialog.setTitle(AutoID.getPK("book_id", "book", "BOK-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BookDialogView() {
		setBounds(100, 100, 450, 400);
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);
		
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitle.setBounds(55, 50, 100, 20);
		lblTitle.requestFocus();
		contentPanel.add(lblTitle);
		
		txtTitle = new JTextField();
		txtTitle.addFocusListener(TxtFieldFocusListener.getFocusListener(txtTitle));
		
		txtTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTitle.setBounds(179, 50, 200, 20);
		contentPanel.add(txtTitle);
		txtTitle.setColumns(10);
		
		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAuthor.setBounds(55, 95, 100, 20);
		contentPanel.add(lblAuthor);
		
		cboAuthor = new JComboBox<String>();
		cboAuthor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboAuthor.setBounds(179, 96, 200, 20);
		contentPanel.add(cboAuthor);
		
		JLabel lblGenre = new JLabel("Genre:");
		lblGenre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblGenre.setBounds(55, 130, 100, 20);
		contentPanel.add(lblGenre);
		
		cboGenre = new JComboBox<String>();
		cboGenre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboGenre.setBounds(179, 131, 200, 20);
		contentPanel.add(cboGenre);
		
		JLabel lblPublisher = new JLabel("Publisher:");
		lblPublisher.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPublisher.setBounds(55, 170, 100, 20);
		contentPanel.add(lblPublisher);
		
		cboPublisher = new JComboBox<String>();
		cboPublisher.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboPublisher.setBounds(179, 171, 200, 20);
//		cboPublisher.setBackground(LibColors.PRIMARY_ACCENT);
		contentPanel.add(cboPublisher);
		
		JLabel lblPublishedYr = new JLabel("Published Year:");
		lblPublishedYr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPublishedYr.setBounds(55, 210, 100, 20);
		contentPanel.add(lblPublishedYr);
		
		txtPubYr = new JTextField();
		txtPubYr.addFocusListener(TxtFieldFocusListener.getFocusListener(txtPubYr));
		txtPubYr.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPubYr.setBounds(179, 210, 200, 20);
		contentPanel.add(txtPubYr);
		txtPubYr.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Price:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(55, 255, 100, 20);
		contentPanel.add(lblNewLabel);
		
		txtPrice = new JTextField();
		txtPrice.addFocusListener(TxtFieldFocusListener.getFocusListener(txtPrice));
		txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtPrice.setBounds(179, 255, 200, 20);
		contentPanel.add(txtPrice);
		txtPrice.setColumns(10);
		
		btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!hasValidInputs()) {
					return;
				}
				
				BookController ctl = new BookController();
				BookModel book = new BookModel();
				book.setId(dialog.getTitle());
				book.setTitle(txtTitle.getText());
				book.setAuthorId(AuthorController.getIdByName(cboAuthor.getSelectedItem().toString()));
//				book.setAuthorName(cboAuthor.getSelectedItem().toString());
				book.setGenreId(GenreController.getIdByName(cboGenre.getSelectedItem().toString()));
//				book.setGenreName(cboGenre.getSelectedItem().toString());
				book.setPublisherId(PublisherController.getIdByName(cboPublisher.getSelectedItem().toString()));
//				book.setPublisherName(cboPublisher.getSelectedItem().toString());
				book.setPrice(Integer.parseInt(txtPrice.getText()));
				book.setPuplishedYr(Integer.parseInt(txtPubYr.getText()));
				book.setQty(0);
				
				if(ctl.hasDuplicate(book)) {
					JOptionPane.showMessageDialog(null, "Book Already Exists!");
					return;
				}
			
				if(ctl.insert(book) == 1) {
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
		
		JLabel lbl = new JLabel("Book Entry");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl.setBounds(10, 10, 416, 25);
		contentPanel.add(lbl);
		
		fillComboBoxes();
		
	}
	
	private boolean hasValidInputs() {
		if(txtTitle.getText().isBlank() || txtPrice.getText().isBlank() || txtPubYr.getText().isBlank() || cboAuthor.getSelectedIndex() < 1 || cboGenre.getSelectedIndex() < 1 || cboPublisher.getSelectedIndex() < 1) {
			JOptionPane.showMessageDialog(null, "Please fill the required fields first");
			txtTitle.requestFocus();
			return false;
		} else if (!InputValidator.isAllDigits(txtPrice.getText())) {
			JOptionPane.showMessageDialog(null, "Book Price must be number only");
			txtPrice.requestFocus();
			txtPrice.selectAll();
			return false;
		} else if (!InputValidator.isAllDigits(txtPubYr.getText())) {
			JOptionPane.showMessageDialog(null, "Published Year must be number only");
			txtPubYr.requestFocus();
			txtPubYr.selectAll();
			return false;
		} else if (txtPubYr.getText().length() != 4) {
			JOptionPane.showMessageDialog(null, "Invalid Year\nRequired Format: XXXX");
			txtPubYr.requestFocus();
			txtPubYr.selectAll();
			return false;
		} else if (txtPrice.getText().length() < 3) {
			JOptionPane.showMessageDialog(null, "Price must be greater than 100");
			txtPrice.requestFocus();
			txtPrice.selectAll();
			return false;
		}
		return true;
	}
	
	public static void showDialog() {
		try {
			BookDialogView dialog = new BookDialogView();
			dialog.setTitle(AutoID.getPK("book_id", "book", "BOK-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void fillComboBoxes() {
		MyComboBox.fillComboItems("author", "author_name", cboAuthor);
		MyComboBox.fillComboItems("genre", "genre_name", cboGenre);
		MyComboBox.fillComboItems("publisher", "publisher_name", cboPublisher);
	}
}
