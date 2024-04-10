package view;

import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import components.MyBtn;
import controller.AuthorController;
import controller.BookController;
import controller.GenreController;
import utilities.LibColors;
import utilities.MyTblFunctions;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;

public class Test extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tbl;
	private DefaultTableModel dtm = new DefaultTableModel();
	private JLabel lblTitle;

	public static JButton obj;
	private static JButton btnPublishers;
	private static JButton btnBooks;
	private static JButton btnAuthors;
	private static JButton btnGenres;
	private static JButton btnDonators;
	private static JButton btnStudents;
	private JLabel lbl;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void showFrame() {
		try {
			Test frame = new Test();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public Test() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		java.net.URL imgURL = getClass().getResource("../images/shiba.png");
		System.out.println(imgURL);
		Image img = new ImageIcon(imgURL).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);
		
		ImageIcon icon = new ImageIcon(img);
		
		lbl = new JLabel("Fantastic Library");
		lbl.setIcon(icon);
		lbl.setOpaque(true);
        lbl.setBackground(Color.ORANGE);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl.setBounds(0, 0, 1270, 39);
		contentPane.add(lbl);

		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBounds(0, 50, 126, 263);
//		verticalBox.setBorder(BorderFactory.createLineBorder(getForeground()));
		contentPane.add(verticalBox);

		// BOOKS BTN
		btnBooks = new JButton("Books");
		btnBooks.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Set max size equal to max size of the vertical
																		// box

		MyBtn.changeMySideNaveStyle(btnBooks);
		
		// Make the btn selected design
		btnBooks.setBackground(LibColors.PRIMARY_BG);
		btnBooks.setBorder(BorderFactory.createBevelBorder(0, Color.LIGHT_GRAY, Color.GRAY));
		obj = btnBooks;
		btnBooks.setOpaque(true);
		
		btnBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTitle.setText("Book List");

				String[] cols = { "ID", "Title", "Author", "Publisher", "Genre", "Published at", "Price", "Qty" };
				int[] w = { 5, 50, 20, 30, 10, 20, 1, 5 };
				createTable(cols, w);
				obj = btnBooks;

				updateTable(obj.getText());
			}
		});

		verticalBox.add(btnBooks);

		// AUTHORS BTN
		btnAuthors = new JButton("Authors");
		btnAuthors.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Set max size equal to max size of the
																			// vertical box
		MyBtn.changeMySideNaveStyle(btnAuthors);
		btnAuthors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				lblTitle.setText("Author List");

				String[] cols = { "ID", "Name" };
				int[] w = { 50, 80 };
				createTable(cols, w);

				obj = btnAuthors;
				updateTable(obj.getText());
			}
		});

		verticalBox.add(btnAuthors);

		// GENRES BTN
		btnGenres = new JButton("Genres");
		btnGenres.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Set max size equal to max size of the
																		// vertical box
		MyBtn.changeMySideNaveStyle(btnGenres);
		btnGenres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTitle.setText("Genre List");

				String[] cols = { "ID", "Name" };
				int[] w = { 50, 80 };
				createTable(cols, w);

				obj = btnGenres;
				updateTable(obj.getText()); // update table data - for row

			}
		});
		verticalBox.add(btnGenres);

		// PUPLISHERS BTN
		btnPublishers = new JButton("Publishers");
		MyBtn.changeMySideNaveStyle(btnPublishers);
		btnPublishers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obj = btnPublishers;

			}
		});
		btnPublishers.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Set max size equal to max size of the
																			// vertical box
		verticalBox.add(btnPublishers);

		// DONATORS BTN
		btnDonators = new JButton("Donators");
		MyBtn.changeMySideNaveStyle(btnDonators);
		btnDonators.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obj = btnDonators;
			}
		});
		btnDonators.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Set max size equal to max size of the
																			// vertical box
		verticalBox.add(btnDonators);

		// STUDENTS BTN
		btnStudents = new JButton("Students");
		MyBtn.changeMySideNaveStyle(btnStudents);
		btnStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obj = btnStudents;
			}
		});
		btnStudents.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Set max size equal to max size of the
																			// vertical box
		verticalBox.add(btnStudents);

		// TABLE TITLE
		lblTitle = new JLabel("");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setBounds(136, 50, 740, 25);
		contentPane.add(lblTitle);

		// TABLE PANEL
		JPanel panel = new JPanel();
		panel.setBounds(136, 80, 747, 500);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 747, 500);
		panel.add(scrollPane);

		tbl = new JTable();
		scrollPane.setViewportView(tbl);

		// ADD BTN
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				switch (obj.getText()) {
				case "Books":
					BookDialogView.showDialog();
					break;
				case "Authors":
					AuthorDiglogView.showDialog();
					break;
				case "Genres":
					GenreDialogView.showDialog();
					break;
				case "Publishers":
					PublisherDialogView.showDialog();
					break;
				case "Donators":
//					DonatorDialogView.showDialog();
					break;
				case "Students":
//					StudentDialogView.showDialog();
					break;
				}
			}
		});
		btnNewButton.setBounds(912, 50, 89, 23);
		contentPane.add(btnNewButton);
		
		lblTitle.setText("Book List");
		
		String[] cols = { "ID", "Title", "Author", "Publisher", "Genre", "Published at", "Price", "Qty" };
		int[] w = { 5, 50, 20, 30, 10, 20, 1, 5 };
		createTable(cols, w);
	} // END of CONSTRUCTOR

	public void setColumnWidth(int i, int width) {
		DefaultTableColumnModel tcm = (DefaultTableColumnModel) tbl.getColumnModel();
		TableColumn tc = tcm.getColumn(i);
		tc.setPreferredWidth(width);
	}

	public void createTable(String[] cols, int[] w) {
		dtm.setColumnCount(0);
		for (String col : cols) {
			dtm.addColumn(col);
		}
		tbl.setModel(dtm);

		for (int i = 0; i < w.length; i++) {
			setColumnWidth(i, w[i]);
		}
	}

	private void updateTable(String tbl) {
		switch (tbl) {
		case "Books":
			BookController bctl = new BookController();
			MyTblFunctions.updateBooks(bctl.getAllBooks(), dtm);
			break;
		case "Authors":
			AuthorController actl = new AuthorController();
			MyTblFunctions.updateAuthors(actl.getAllAuthors(), dtm);
			break;
		case "Genres":
			GenreController gctl = new GenreController();
			MyTblFunctions.updateGenres(gctl.getAllGenres(), dtm);
			break;
		case "Publishers":

			break;
		case "Donators":

			break;
		case "Students":

			break;
		}
	}

	public static JButton[] getBtns() {
		JButton[] btns = { btnBooks, btnAuthors, btnGenres, btnPublishers, btnDonators, btnStudents };
		return btns;
	}
}
