package view;

import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import components.DateLabelFormatter;
import components.MyBtn;
import components.TxtFieldFocusListener;
import controller.AuthorController;
import controller.BookController;
import controller.BorrowController;
import controller.DonationController;
import controller.DonationDetailController;
import controller.DonatorController;
import controller.GenreController;
import controller.PublisherController;
import controller.ReturnController;
import javafx.scene.control.DatePicker;
import model.AuthorModel;
import model.BookModel;
import model.BorrowModel;
import model.DonationDetailModel;
import model.DonationModel;
import model.DonatorModel;
import model.GenreModel;
import model.PublisherModel;
import model.ReturnModel;
import utilities.ChangeDate;
import utilities.CurrencyFormatter;
import utilities.LibColors;
import utilities.MyTblFunctions;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AdminMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tbl;
	public static DefaultTableModel dtm = new DefaultTableModel();
	private JLabel lblTitle;

	public static JButton obj;
	private static JButton btnPublishers;
	private static JButton btnBooks;
	private static JButton btnAuthors;
	private static JButton btnGenres;
	private static JButton btnDonators;
	private static JButton btnDonation;
	private static JButton btnBorrow;
	private static JButton btnReturn;
	private JButton btnUpdate;
	private JButton btnDelete;
	private JLabel lbl;

	private String selectedId;
	private JLabel lblInfo;
	private JButton btnAdd;
	private JDatePickerImpl datePicker;
	private JLabel lblFilterBy;
	private JTextField txtSearch;
	private JLabel lblSearch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMain frame = new AdminMain();
					frame.setVisible(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // set the frame to be maximized
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void showFrame() {
		try {
			AdminMain frame = new AdminMain();
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // set the frame to be maximized
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public AdminMain() {
		setTitle("Library Management System");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		java.net.URL imgURL = getClass().getResource("../images/shiba.png");
		Image img = new ImageIcon(imgURL).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);

		ImageIcon icon = new ImageIcon(img);
		setIconImage(img);

		lbl = new JLabel("Shiba Shelves");
		// Set padding by adjusting the insets of the label's border
		lbl.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0)); // top, left, bottom, right
		lbl.setIconTextGap(20); // Adjust the gap value between img and txt
		lbl.setIcon(icon);
		lbl.setOpaque(true);
		lbl.setBackground(Color.ORANGE);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl.setBounds(0, 0, 1300, 50);
		contentPane.add(lbl);

		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBounds(0, 70, 126, 720);
//		verticalBox.setBorder(BorderFactory.createLineBorder(getForeground()));
		contentPane.add(verticalBox);

		// BOOKS BTN
		btnBooks = new JButton("Books");
		btnBooks.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		MyBtn.changeMySideNaveStyle(btnBooks);

		// Make the btn selected design
		btnBooks.setBackground(LibColors.PRIMARY_BG);
		btnBooks.setBorder(BorderFactory.createBevelBorder(0, Color.LIGHT_GRAY, Color.GRAY));
		obj = btnBooks;
		btnBooks.setOpaque(true);

		btnBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBtns(true);

				lblTitle.setText("Book List");

				String[] cols = { "No.", "ID", "Title", "Author", "Publisher", "Genre", "Published at", "Price",
						"Qty" };
				int[] w = { 40, 76, 343, 171, 143, 101, 97, 71, 40 };
				createTable(cols, w);
				obj = btnBooks;

				updateTable(obj.getText());
				enableBtns(false);
			}
		});

		verticalBox.add(btnBooks);

		// AUTHORS BTN
		btnAuthors = new JButton("Authors");
		btnAuthors.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

		MyBtn.changeMySideNaveStyle(btnAuthors);
		btnAuthors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBtns(true);

				lblTitle.setText("Author List");

				String[] cols = { "No.", "ID", "Name" };
				int[] w = { 44, 220, 833 };
				createTable(cols, w);

				obj = btnAuthors;
				updateTable(obj.getText());
				enableBtns(false);
			}
		});

		verticalBox.add(btnAuthors);

		// GENRES BTN
		btnGenres = new JButton("Genres");
		btnGenres.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));

		MyBtn.changeMySideNaveStyle(btnGenres);
		btnGenres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBtns(true);
				lblTitle.setText("Genre List");

				String[] cols = { "No.", "ID", "Name" };
				int[] w = { 44, 220, 833 };
				createTable(cols, w);

				obj = btnGenres;
				updateTable(obj.getText()); // update table data - for row
				enableBtns(false);
			}
		});
		verticalBox.add(btnGenres);

		// PUPLISHERS BTN
		btnPublishers = new JButton("Publishers");
		MyBtn.changeMySideNaveStyle(btnPublishers);
		btnPublishers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBtns(true);
				lblTitle.setText("Publisher List");

				String[] cols = { "No.", "ID", "Name" };
				int[] w = { 44, 220, 833 };
				createTable(cols, w);

				obj = btnPublishers;
				updateTable(obj.getText());
				enableBtns(false);

			}
		});
		btnPublishers.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		verticalBox.add(btnPublishers);

		// DONATORS BTN
		btnDonators = new JButton("Donators");
		MyBtn.changeMySideNaveStyle(btnDonators);
		btnDonators.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBtns(true);
				lblTitle.setText("Donator List");

				String[] cols = { "No.", "ID", "Name", "Email", "Phone", "Address" };
				int[] w = { 40, 82, 220, 216, 136, 403 };
				createTable(cols, w);

				obj = btnDonators;
				updateTable(obj.getText());
				enableBtns(false);
			}
		});
		btnDonators.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		verticalBox.add(btnDonators);

		// DONTATION BTN
		btnDonation = new JButton("Donations");
		MyBtn.changeMySideNaveStyle(btnDonation);
		btnDonation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBtns(true);
				lblTitle.setText("Dontaion List");

				String[] cols = { "No.", "ID", "Dontaor ID", "Donator Name", "Date", "Book Qty" };
				int[] w = { 47, 141, 158, 430, 197, 134 };
				createTable(cols, w);

				obj = btnDonation;
				updateTable(obj.getText());
				enableBtns(false);
			}
		});
		btnDonation.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		verticalBox.add(btnDonation);

		// BORROW BTN
		btnBorrow = new JButton("Borrows");
		MyBtn.changeMySideNaveStyle(btnBorrow);
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBtns(false);

				lblTitle.setText("Borrow List");

				String[] cols = { "No.", "ID", "Student ID", "Student Name", "Date", "Total Borrowed Qty",
						"Total Returned Qty" };
				int[] w = { 44, 115, 131, 350, 141, 150, 150 };
				createTable(cols, w);

				obj = btnBorrow;
				updateTable(obj.getText());
				enableBtns(false);
			}
		});
		btnBorrow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		verticalBox.add(btnBorrow);

		// RETURN BTN
		btnReturn = new JButton("Returns");
		MyBtn.changeMySideNaveStyle(btnReturn);
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				showBtns(false);

				lblTitle.setText("Borrow List");

				String[] cols = { "No.", "Return ID", "Borrow ID", "Student ID", "Student Name", "Date",
						"Total Returned Qty", "Total Fine" };
				int[] w = { 44, 115, 116, 122, 250, 137, 148, 150 };
				createTable(cols, w);

				obj = btnReturn;
				updateTable(obj.getText());
				enableBtns(false);
			}
		});
		btnReturn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
		verticalBox.add(btnReturn);

		// TABLE TITLE
		lblTitle = new JLabel("");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setBounds(136, 105, 200, 25);
		contentPane.add(lblTitle);

		// TABLE PANEL
		JPanel panel = new JPanel();
		panel.setBounds(136, 140, 1100, 495);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1100, 495);
		panel.add(scrollPane);

		tbl = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());

				if (column == 7) {
					((JLabel) comp).setHorizontalAlignment(SwingConstants.RIGHT);
				} else {
					// For other columns, reset horizontal alignment to default (left)
					((JLabel) comp).setHorizontalAlignment(SwingConstants.LEFT);
				}

				return comp;
			}
		};

		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				enableBtns(true);

				selectedId = tbl.getValueAt(row, 1).toString();
			}
		});

		tbl.setRowHeight(30);
		tbl.setSelectionBackground(LibColors.PRIMARY_SELECT_BG);
		tbl.setIntercellSpacing(new Dimension(7, 0));

		// Change header height
		JTableHeader header = tbl.getTableHeader();
		Dimension headerPreferredSize = header.getPreferredSize();
		headerPreferredSize.height = 35;
		header.setPreferredSize(headerPreferredSize);

		Font headerFont = header.getFont();
		header.setFont(headerFont.deriveFont(Font.BOLD, 14));
//        header.setBackground(new Color(255,238,178));

		tbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(tbl);

		// ADD BTN
		btnAdd = new JButton("Add");
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 13));
		MyBtn.changeMyBtnStyle(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				enableBtns(false);

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
					DonatorDialogView.showDialog();
					break;
				case "Donations":
					DonationView.showDialog();
					break;
				}
			}
		});
		btnAdd.setBounds(955, 97, 80, 30);
		contentPane.add(btnAdd);

		// UPDATE BTN
		btnUpdate = new JButton("Update");
		btnUpdate.setEnabled(false);
		MyBtn.changeMyBtnStyle(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedId.startsWith("BOK-")) {
					BookDialogView.showDialog(selectedId);
				} else if (selectedId.startsWith("AUT-")) {
					AuthorDiglogView.showDialog(selectedId);
				} else if (selectedId.startsWith("GEN-")) {
					GenreDialogView.showDialog(selectedId);
				} else if (selectedId.startsWith("PUB-")) {
					PublisherDialogView.showDialog(selectedId);
				} else if (selectedId.startsWith("DNT-")) {
					DonatorDialogView.showDialog(selectedId);
				} else if (selectedId.startsWith("DON-")) {
					DonationView.showDialog(selectedId);
				}

				enableBtns(false);
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUpdate.setBounds(1050, 97, 85, 30);
		contentPane.add(btnUpdate);

		// DELETE BTN
		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		MyBtn.changeMyBtnStyle(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(JOptionPane.showConfirmDialog(null, "Are you sure you want to delete?", "Confirm", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
					return;
				}
				
				if (selectedId.startsWith("BOK-")) {
					BookController ctl = new BookController();
					BookModel b = new BookModel();
					b.setId(selectedId);

					if (ctl.delete(b) == 1) {
						MyTblFunctions.updateBooksTable();
						enableBtns(false);
					}
				} else if (selectedId.startsWith("AUT-")) {
					AuthorController ctl = new AuthorController();
					AuthorModel a = new AuthorModel();
					a.setId(selectedId);

					if (ctl.delete(a) == 1) {
						MyTblFunctions.updateAuthorsTable();
						enableBtns(false);
					}
				} else if (selectedId.startsWith("GEN-")) {
					GenreController ctl = new GenreController();
					GenreModel g = new GenreModel();
					g.setId(selectedId);

					if (ctl.delete(g) == 1) {
						MyTblFunctions.updateGenresTable();
						enableBtns(false);
					}
				} else if (selectedId.startsWith("PUB-")) {
					PublisherController ctl = new PublisherController();
					PublisherModel p = new PublisherModel();
					p.setId(selectedId);

					if (ctl.delete(p) == 1) {
						MyTblFunctions.updatePublishersTable();
						enableBtns(false);
					}
				} else if (selectedId.startsWith("DNT-")) {
					DonatorController ctl = new DonatorController();
					DonatorModel d = new DonatorModel();
					d.setDonatorId(selectedId);

					if (ctl.delete(d) == 1) {
						MyTblFunctions.updateDonatorsTable();
						enableBtns(false);
					}
				} else if (selectedId.startsWith("DON-")) {

					List<DonationDetailModel> details = DonationDetailController.getAllDonationsById(selectedId);

					List<BookModel> booksToBeUpdated = new ArrayList<>();

					for (DonationDetailModel d : details) {
						String bookId = d.getBookId();

						int qtyToBeRemoved = d.getQty();
						int bookQty = BookController.getQty(bookId);

						BookModel book = new BookModel();
						book.setId(bookId);
						book.setQty(bookQty - qtyToBeRemoved);

						booksToBeUpdated.add(book);
					}

					for (BookModel book : booksToBeUpdated) {
						if (BookController.updateQty(book) != 1) {
							JOptionPane.showMessageDialog(null, "Error updating book qty");
							return;
						}
					}

					DonationDetailController dctl = new DonationDetailController();
					DonationDetailModel detail = details.get(0);

					if (dctl.delete(detail) < 1) {
						JOptionPane.showMessageDialog(null, "Error deleting donation details");
					}

					DonationController ctl = new DonationController();
					DonationModel donation = new DonationModel();
					donation.setDonationId(selectedId);

					if (ctl.delete(donation) != 1) {
						JOptionPane.showMessageDialog(null, "Delete Failed");
					}
					MyTblFunctions.updateDonationsTable();
					enableBtns(false);

				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDelete.setBounds(1150, 97, 85, 30);
		contentPane.add(btnDelete);

		// create table
		lblTitle.setText("Book List");

		lblInfo = new JLabel("Add data and Select any table row to update and delete.");
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInfo.setBounds(136, 70, 1000, 25);
		contentPane.add(lblInfo);

		// date picker
		UtilDateModel model = new UtilDateModel();
		Properties properties = new Properties();
		JDatePanelImpl datePanel = new JDatePanelImpl(model, properties);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setVisible(false);
		datePicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date utilDate = (Date) datePicker.getModel().getValue();
				if (utilDate == null && obj.getText().equals("Borrows")) {
					MyTblFunctions.updateBorrowsTable();
					return;
				} else if (utilDate == null && obj.getText().equals("Returns")) {
					MyTblFunctions.updateReturnsTable();
					return;
				}

				java.sql.Date date = ChangeDate.toSqlDate(utilDate);

				if (obj.getText().equals("Borrows")) {
					List<BorrowModel> borrows = BorrowController.getAllBorrowsByDate(date);
					MyTblFunctions.updateBorrowsTable(borrows);
				} else if (obj.getText().equals("Returns")) {
					List<ReturnModel> returns = ReturnController.getAllReturnsByDate(date);
					MyTblFunctions.updateReturnsTable(returns);
				}
			}
		});

		datePicker.setBounds(1085, 100, 150, 25);
		getContentPane().add(datePicker);

		JButton btnToLib = new JButton("To Library");
		btnToLib.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.showFrame();
			}
		});
		MyBtn.changeMyBtnStyle(btnToLib);
		btnToLib.setBounds(1150, 650, 89, 30);
		contentPane.add(btnToLib);

		lblFilterBy = new JLabel("Filter By Date:");
		lblFilterBy.setVisible(false);
		lblFilterBy.setHorizontalAlignment(SwingConstants.TRAILING);
		lblFilterBy.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFilterBy.setBounds(975, 100, 100, 20);
		contentPane.add(lblFilterBy);

		lblSearch = new JLabel("Search By Title:");
		lblSearch.setHorizontalAlignment(SwingConstants.TRAILING);
		lblSearch.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSearch.setBounds(535, 100, 150, 25);
		contentPane.add(lblSearch);

		txtSearch = new JTextField();
		txtSearch.addFocusListener(TxtFieldFocusListener.getFocusListener(txtSearch));
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (obj.getText().equals("Books")) {
					List<BookModel> books = BookController.searchByTitle(txtSearch.getText());
					MyTblFunctions.updateBooksTable(books);
					return;
				}

				if (obj.getText().equals("Authors")) {
					List<AuthorModel> authors = AuthorController.searchByName(txtSearch.getText());
					MyTblFunctions.updateAuthorsTable(authors);
					return;
				}

				if (obj.getText().equals("Genres")) {
					List<GenreModel> genres = GenreController.searchByName(txtSearch.getText());
					MyTblFunctions.updateGenresTable(genres);
					return;
				}

				if (obj.getText().equals("Publishers")) {
					List<PublisherModel> publishers = PublisherController.searchByName(txtSearch.getText());
					MyTblFunctions.updatePublishersTable(publishers);
					return;
				}

				if (obj.getText().equals("Donators")) {
					List<DonatorModel> donators = DonatorController.searchByName(txtSearch.getText());
					MyTblFunctions.updateDonatorsTable(donators);
					return;
				}

				if (obj.getText().equals("Donations")) {
					List<DonationModel> donations = DonationController.searchByDonatorName(txtSearch.getText());
					MyTblFunctions.updateDonationsTable(donations);
					return;
				}

				if (obj.getText().equals("Borrows")) {
					List<BorrowModel> borrows = BorrowController.searchByStudentName(txtSearch.getText());
					MyTblFunctions.updateBorrowsTable(borrows);
					return;
				}

				if (obj.getText().equals("Returns")) {
					List<ReturnModel> returns = ReturnController.searchByStudentName(txtSearch.getText());
					MyTblFunctions.updateReturnsTable(returns);
					return;
				}
			}
		});
		txtSearch.setBounds(690, 100, 150, 25);
		contentPane.add(txtSearch);
		txtSearch.setColumns(10);

		String[] cols = { "No.", "ID", "Title", "Author", "Publisher", "Genre", "Published at", "Price", "Qty" };
		int[] w = { 40, 76, 343, 171, 143, 101, 97, 71, 40 };
		createTable(cols, w);

		// load data from db and update table
		MyTblFunctions.updateBooksTable();
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
			lblSearch.setText("Search By Title:");
			MyTblFunctions.updateBooksTable();
			break;
		case "Authors":
			lblSearch.setText("Search By Name:");
			MyTblFunctions.updateAuthorsTable();
			break;
		case "Genres":
			lblSearch.setText("Search By Name:");
			MyTblFunctions.updateGenresTable();
			break;
		case "Publishers":
			lblSearch.setText("Search By Name:");
			MyTblFunctions.updatePublishersTable();
			break;
		case "Donators":
			lblSearch.setText("Search By Name:");
			MyTblFunctions.updateDonatorsTable();
			break;
		case "Donations":
			lblSearch.setText("Search By Name:");
			MyTblFunctions.updateDonationsTable();
			break;
		case "Borrows":
			lblSearch.setText("Search By Name:");
			MyTblFunctions.updateBorrowsTable();
			break;
		case "Returns":
			lblSearch.setText("Search By Name:");
			MyTblFunctions.updateReturnsTable();
			break;

		}

	}

	private void enableBtns(boolean enable) {
		btnUpdate.setText(obj.getText().equals("Donations") ? "View" : "Update");
		btnUpdate.setEnabled(enable);
		btnDelete.setEnabled(enable);

		// disable for donation table
//		btnUpdate.setEnabled(obj.getText().equals("Donations") ? false : enable);

		btnUpdate.setBackground(enable ? LibColors.PRIMARY_BG : Color.LIGHT_GRAY);
		btnDelete.setBackground(enable ? LibColors.PRIMARY_BG : Color.LIGHT_GRAY);

		if (!enable) {
			tbl.clearSelection(); // remove table row selection
		}

//		if(obj.getText().equals("Donations")) { 
//			btnUpdate.setBackground(Color.LIGHT_GRAY); 
//		}
	}

	private void showBtns(boolean show) {
		
		btnAdd.setVisible(show);
		btnUpdate.setVisible(show);
		btnDelete.setVisible(show);

		datePicker.setVisible(!show);
		lblFilterBy.setVisible(!show);
	}

	public static JButton[] getBtns() {
		JButton[] btns = { btnBooks, btnAuthors, btnGenres, btnPublishers, btnDonators, btnDonation, btnBorrow,
				btnReturn };
		return btns;
	}
}
