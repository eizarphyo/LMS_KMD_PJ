package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
import utilities.MyComboBox;
import utilities.MyTblFunctions;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Year;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Cursor;

public class BookDialogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JTextField txtTitle;
	private static JTextField txtPrice;
	private static JButton btnAdd;
	private static JComboBox<String> cboGenre;
	private static JComboBox<String> cboPublisher;
	private static JTextField txtPubYr;
	private static JComboBox<String> cboAuthor;
	private static BookDialogView dialog;
	private static boolean update = false;
	private static JLabel lblImg;
	
	private static byte[] imgBytes;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			dialog = new BookDialogView();
//			dialog.setTitle(AutoID.getPK("book_id", "book", "BOK-"));
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static void showDialog() {
		try {
			update = false;

			dialog = new BookDialogView();
			dialog.setTitle(AutoID.getPK("book_id", "book", "BOK-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDialog(String id) {
		try {
			update = true;

			dialog = new BookDialogView();
			dialog.setTitle(id);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			BookController ctl = new BookController();
			BookModel b = new BookModel();
			b.setId(id);

			b = ctl.getOneBookById(b);

			txtTitle.setText(b.getTitle());
			cboAuthor.setSelectedItem(b.getAuthorName());
			cboGenre.setSelectedItem(b.getGenreName());
			cboPublisher.setSelectedItem(b.getPublisherName());
			txtPubYr.setText(b.getPuplishedYr() + "");
			txtPrice.setText(b.getPrice() + "");
			imgBytes = b.getImage();
			
			if(b.getImage() != null) {
				// Convert the byte array to an Image object
                Image image = Toolkit.getDefaultToolkit().createImage(b.getImage()).getScaledInstance(100, 110, Image.SCALE_SMOOTH);

                // Scale the image if necessary
//                Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

                // Set the image to the JLabel
                lblImg.setText("");
				lblImg.setOpaque(false); 
				lblImg.setIcon(new ImageIcon(image));	
			} 

			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BookDialogView() {
		setBounds(100, 100, 450, 524);
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTitle.setBounds(55, 180, 100, 20);
		lblTitle.requestFocus();
		contentPanel.add(lblTitle);

		txtTitle = new JTextField();
		txtTitle.addFocusListener(TxtFieldFocusListener.getFocusListener(txtTitle));

		txtTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtTitle.setBounds(179, 180, 200, 25);
		contentPanel.add(txtTitle);
		txtTitle.setColumns(10);

		JLabel lblAuthor = new JLabel("Author:");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAuthor.setBounds(55, 220, 100, 20);
		contentPanel.add(lblAuthor);

		cboAuthor = new JComboBox<String>();
		MyComboBox.changeMyCboStyle(cboAuthor);
		cboAuthor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboAuthor.setBounds(179, 220, 200, 25);
		contentPanel.add(cboAuthor);

		JLabel lblGenre = new JLabel("Genre:");
		lblGenre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblGenre.setBounds(55, 260, 100, 20);
		contentPanel.add(lblGenre);

		cboGenre = new JComboBox<String>();
		MyComboBox.changeMyCboStyle(cboGenre);
		cboGenre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboGenre.setBounds(179, 260, 200, 25);
		contentPanel.add(cboGenre);

		JLabel lblPublisher = new JLabel("Publisher:");
		lblPublisher.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPublisher.setBounds(55, 300, 100, 20);
		contentPanel.add(lblPublisher);

		cboPublisher = new JComboBox<String>();
		MyComboBox.changeMyCboStyle(cboPublisher);
		cboPublisher.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboPublisher.setBounds(179, 300, 200, 25);
//		cboPublisher.setBackground(LibColors.PRIMARY_ACCENT);
		contentPanel.add(cboPublisher);

		JLabel lblPublishedYr = new JLabel("Published Year:");
		lblPublishedYr.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPublishedYr.setBounds(55, 340, 100, 20);
		contentPanel.add(lblPublishedYr);

		txtPubYr = new JTextField();
		txtPubYr.addFocusListener(TxtFieldFocusListener.getFocusListener(txtPubYr));
		txtPubYr.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtPubYr.setBounds(179, 340, 200, 25);
		contentPanel.add(txtPubYr);
		txtPubYr.setColumns(10);

		JLabel lblNewLabel = new JLabel("Price:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel.setBounds(55, 380, 100, 20);
		contentPanel.add(lblNewLabel);

		txtPrice = new JTextField();
		txtPrice.setHorizontalAlignment(SwingConstants.TRAILING);
		txtPrice.addFocusListener(TxtFieldFocusListener.getFocusListener(txtPrice));
		txtPrice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtPrice.setBounds(179, 380, 70, 25);
		contentPanel.add(txtPrice);
		txtPrice.setColumns(10);

		System.out.println("-> " + update);

		btnAdd = new JButton(update ? "Update" : "Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!hasValidInputs()) {
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
				book.setImage(imgBytes);

				if (!update && ctl.hasDuplicate(book)) {
					JOptionPane.showMessageDialog(null, "Book Already Exists!");
					return;
				}

				int ok = update ? ctl.update(book) : ctl.insert(book);
				System.out.println(ok);
				if (ok == 1) {
					MyTblFunctions.updateBooksTable();
					JOptionPane.showMessageDialog(null, "Success!");
					dispose();
					return;
				}

				JOptionPane.showMessageDialog(null, "Something Went Wrong");

			}
		});
		MyBtn.changeMyBtnStyle(btnAdd);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnAdd.setBounds(179, 435, 89, 27);
		contentPanel.add(btnAdd);

		JLabel lbl = new JLabel("Book Entry");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl.setBounds(10, 10, 416, 25);
		contentPanel.add(lbl);

		lblImg = new JLabel("upload image");
		lblImg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblImg.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				chooseImage();
			}
		});
		lblImg.setOpaque(true);
		lblImg.setBackground(new Color(221,221,221)
);
		lblImg.setHorizontalAlignment(SwingConstants.CENTER);
		lblImg.setBounds(168, 46, 100, 110);
		contentPanel.add(lblImg);
		
		JLabel lblCurrency = new JLabel("MMK");
		lblCurrency.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCurrency.setBounds(255, 378, 30, 25);
		contentPanel.add(lblCurrency);

		fillComboBoxes();

	}

	private void chooseImage() {
		// Create file chooser
		JFileChooser fileChooser = new JFileChooser();
		// Only allow image files to be selected
		fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
			public boolean accept(File file) {
				String filename = file.getName().toLowerCase();
				return filename.endsWith(".jpg") || filename.endsWith(".png") || filename.endsWith(".gif")
						|| file.isDirectory();
			}

			public String getDescription() {
				return "Image Files";
			}
		});

		// Show file chooser dialog
		int result = fileChooser.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION) {
			try {
				// Get selected file
                File selectedFile = fileChooser.getSelectedFile();
                // Read the image from file
                imgBytes = readImageAsBytes(selectedFile);
                
				Image image = ImageIO.read(selectedFile).getScaledInstance(100, 110, Image.SCALE_SMOOTH);
				
				lblImg.setText("");
				lblImg.setOpaque(false);
				// Set the image to the JLabel
				lblImg.setIcon(new ImageIcon(image));
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error loading image", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private byte[] readImageAsBytes(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = fis.read(buffer)) != -1) {
            bos.write(buffer, 0, bytesRead);
        }
        fis.close();
        bos.close();
        return bos.toByteArray();
    }

	private boolean hasValidInputs() {
		if (txtTitle.getText().isBlank() || txtPrice.getText().isBlank() || txtPubYr.getText().isBlank()
				|| cboAuthor.getSelectedIndex() < 1 || cboGenre.getSelectedIndex() < 1
				|| cboPublisher.getSelectedIndex() < 1) {
			JOptionPane.showMessageDialog(null, "Please fill the required fields first");
			txtTitle.requestFocus();
			return false;
		} else if (lblImg.getIcon() == null) {
			JOptionPane.showMessageDialog(null, "Please upload the Book's cover");
			return false;
		}
		else if (!InputValidator.isAllDigits(txtPrice.getText())) {
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
		} else if(Integer.parseInt(txtPubYr.getText()) < 1800 || Integer.parseInt(txtPubYr.getText()) > Year.now().getValue()) {
			JOptionPane.showMessageDialog(null, "Invalid Year\nRequired Format: between 1500 and current year");
			txtPubYr.requestFocus();
			txtPubYr.selectAll();
			return false;
		}
		else if (txtPrice.getText().length() < 4) {
			JOptionPane.showMessageDialog(null, "Price must be greater than 1,000");
			txtPrice.requestFocus();
			txtPrice.selectAll();
			return false;
		} else if(Integer.parseInt(txtPrice.getText()) > 300000) {
			JOptionPane.showMessageDialog(null, "Price must be less than 300,000");
			txtPrice.requestFocus();
			txtPrice.selectAll();
			return false;
		} 
		return true;
	}

	private void fillComboBoxes() {
		MyComboBox.fillComboItems("author", "author_name", cboAuthor);
		MyComboBox.fillComboItems("genre", "genre_name", cboGenre);
		MyComboBox.fillComboItems("publisher", "publisher_name", cboPublisher);
	}
}
