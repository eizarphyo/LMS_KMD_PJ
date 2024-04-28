package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import components.MyBtn;
import components.MyImageLabel;
import controller.BookController;
import controller.GenreController;
import model.BookModel;
import utilities.MyComboBox;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lbl;
	private List<BookModel> books = new ArrayList<>();
	private JComboBox<String> cboFilter;
	private JPanel panelBooks;

	private int row = 1, col=1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // set the frame to be maximized
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void showFrame() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // set the frame to be maximized
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setTitle("Library Management System");
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);

		BookController ctl = new BookController();
		books = ctl.getAllBooks();

		JPanel contentPanel = new JPanel();
		contentPanel.setBounds(0, 0, 1300, 685);
		contentPane.add(contentPanel);
		contentPanel.setLayout(null);

		java.net.URL imgURL = getClass().getResource("../images/shiba.png");
		Image img = new ImageIcon(imgURL).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);

		ImageIcon icon = new ImageIcon(img);

		lbl = new JLabel("Shiba Shelves");

		// Set padding by adjusting the insets of the label's border
		lbl.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); // top, left, bottom, right
		lbl.setIconTextGap(20); // Adjust the gap value between img and txt
		lbl.setBounds(0, 0, 1300, 50);
		lbl.setIcon(icon);
		lbl.setOpaque(true);
		lbl.setBackground(Color.ORANGE);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.setLayout(null);
		contentPanel.add(lbl);

		// Search field
//		JPanel searchPanel = new JPanel();
//		searchPanel.setBounds(244, 61, 215, 30);
//		contentPanel.add(searchPanel);
//
//		// Create the icon
//		java.net.URL iconUrl = getClass().getResource("../images/search.png");
//		Image image = new ImageIcon(iconUrl).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); // Replace
//																										// "icon.png"
//		ImageIcon ic = new ImageIcon(image);
//		JLabel iconLabel = new JLabel(ic);
//
//		// Create the text field
//		JTextField txtSearch = new JTextField(15);
//		txtSearch.setBounds(800, 69, 150, 25);
//
//		// Add the icon and text field to the panel
//		searchPanel.add(iconLabel, BorderLayout.WEST);
//		searchPanel.add(txtSearch, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBounds(0, 107, 1280, 580);
		panel.setLayout(null);
		contentPanel.add(panel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1280, 580);
		panel.add(scrollPane);

		panelBooks = new JPanel();
		scrollPane.setViewportView(panelBooks);
		row = books.size() / 6 + 1; col = 6;
		panelBooks.setLayout(new GridLayout(row, col, 10, 20));

		JLabel title = new JLabel("Books In The Library");
		title.setFont(new Font("Tahoma", Font.BOLD, 15));
		title.setBounds(10, 66, 500, 30);
		contentPanel.add(title);

		JButton btnBorrow = new JButton("Borrow");
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BorrowDialogView.showDialog();

			}
		});
		MyBtn.changeMyBtnStyle(btnBorrow);
		btnBorrow.setBounds(1074, 66, 80, 27);
		contentPanel.add(btnBorrow);

		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReturnDialogView.showDialog();
			}
		});
		MyBtn.changeMyBtnStyle(btnReturn);
		btnReturn.setBounds(1173, 66, 75, 27);
		contentPanel.add(btnReturn);
		
		cboFilter = new JComboBox();
		MyComboBox.changeMyCboStyle(cboFilter);
		cboFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cboFilter.getSelectedIndex() > 0) {
					String genre = cboFilter.getSelectedItem().toString();
					
					String genreId = GenreController.getIdByName(genre);
					
					books = BookController.searchBooksByGenreId(genreId);
				} else {
					BookController ctl = new BookController();
					books = ctl.getAllBooks();
				}
				panelBooks.removeAll();
				renderBooks();
			}
		});
		cboFilter.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboFilter.setBounds(600, 67, 200, 25);
		contentPanel.add(cboFilter);

		for (BookModel book : books) {
			ImageIcon bookCover = new ImageIcon(
					convertBlobToImage(book.getImage()).getScaledInstance(150, 200, Image.SCALE_SMOOTH));

			JCheckBox checkBox = new JCheckBox();
			checkBox.setOpaque(false); // Set opaque to false
			checkBox.setBorderPainted(false); // Remove border

			MyImageLabel label = new MyImageLabel(book.getTitle(), bookCover, checkBox);
			label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Selected item ID: " + book.getId());
                    BookDetailsView.showDialog(book.getId());
                }
            });
//			label.setBorder(BorderFactory.createLineBorder(Color.orange));
			panelBooks.add(label);
		}

		fillCombo();
	}

	private static Image convertBlobToImage(byte[] blobData) {
		// Convert the blob data to an Image
		Image image = null;
		if (blobData != null && blobData.length > 0) {
			try {
				InputStream inputStream = new ByteArrayInputStream(blobData);
				image = ImageIO.read(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return image;
	}
	
	void renderBooks() {
		
		for (BookModel book : books) {
			
			ImageIcon bookCover = new ImageIcon(
					convertBlobToImage(book.getImage()).getScaledInstance(150, 200, Image.SCALE_SMOOTH));

			JCheckBox checkBox = new JCheckBox();
			checkBox.setOpaque(false); // Set opaque to false
			checkBox.setBorderPainted(false); // Remove border

			MyImageLabel label = new MyImageLabel(book.getTitle(), bookCover, checkBox);
			label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println("Selected item ID: " + book.getId());
                    BookDetailsView.showDialog(book.getId());
                }
            });
//			label.setBorder(BorderFactory.createLineBorder(Color.orange));
			panelBooks.add(label);
		}
	}
	
	void fillCombo() {
		MyComboBox.fillComboItems("genre", "genre_name", cboFilter);
	}
}
