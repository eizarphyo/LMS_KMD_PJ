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

import components.MyBtn;
import controller.AuthorController;
import controller.BookController;
import controller.DonatorController;
import controller.GenreController;
import controller.PublisherController;
import model.AuthorModel;
import model.BookModel;
import model.DonatorModel;
import model.GenreModel;
import model.PublisherModel;
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
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Test extends JFrame {

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
	private JLabel lbl;
	private JButton btnUpdate;
	private JButton btnDelete;

	private String selectedId;
	private JLabel lblInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Test frame = new Test();
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
			Test frame = new Test();
			frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // set the frame to be maximized
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
		Image img = new ImageIcon(imgURL).getImage().getScaledInstance(45, 45, Image.SCALE_SMOOTH);

		ImageIcon icon = new ImageIcon(img);

		lbl = new JLabel("Fantastic Library");
		lbl.setIcon(icon);
		lbl.setOpaque(true);
		lbl.setBackground(Color.ORANGE);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbl.setBounds(0, 0, 1300, 50);
		contentPane.add(lbl);

		Box verticalBox = Box.createVerticalBox();
		verticalBox.setBounds(0, 70, 126, 263);
//		verticalBox.setBorder(BorderFactory.createLineBorder(getForeground()));
		contentPane.add(verticalBox);

		// BOOKS BTN
		btnBooks = new JButton("Books");
		btnBooks.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
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
				enableBtns(false);
			}
		});

		verticalBox.add(btnBooks);

		// AUTHORS BTN
		btnAuthors = new JButton("Authors");
		
		btnAuthors.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		MyBtn.changeMySideNaveStyle(btnAuthors);
		btnAuthors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableBtns(false);
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
		btnGenres.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

		MyBtn.changeMySideNaveStyle(btnGenres);
		btnGenres.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				enableBtns(false);
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
				enableBtns(false);
				lblTitle.setText("Publisher List");

				String[] cols = { "ID", "Name" };
				int[] w = { 50, 80 };
				createTable(cols, w);

				obj = btnPublishers;
				updateTable(obj.getText());

			}
		});
		btnPublishers.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		verticalBox.add(btnPublishers);

		// DONATORS BTN
		btnDonators = new JButton("Donators");
		MyBtn.changeMySideNaveStyle(btnDonators);
		btnDonators.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTitle.setText("Donator List");

				String[] cols = { "ID", "Name", "Email", "Phone", "Address" };
				int[] w = { 10, 50, 80, 50, 100 };
				createTable(cols, w);

				obj = btnDonators;
				updateTable(obj.getText());
			}
		});
		btnDonators.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Set max size equal to max size of the
																			// vertical box
		verticalBox.add(btnDonators);

		// DONTATION BTN
		btnDonation = new JButton("Donations");
		MyBtn.changeMySideNaveStyle(btnDonation);
		btnDonation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTitle.setText("Dontaion List");
				enableBtns(false);

				String[] cols = { "ID", "Donator Name", "Book Qty" };
				int[] w = { 50, 80, 10 };
				createTable(cols, w);

				obj = btnDonation;
				updateTable(obj.getText());
			}
		});
		btnDonation.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Set max size equal to max size of the
																			// vertical box
		verticalBox.add(btnDonation);
		
		// BORROW BTN
		JButton btnBorrow = new JButton("Borrows");
		MyBtn.changeMySideNaveStyle(btnBorrow);
		btnBorrow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		verticalBox.add(btnBorrow);
		
		// RETURN BTN
		JButton btnReturn = new JButton("Returns");
		MyBtn.changeMySideNaveStyle(btnReturn);
		btnReturn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		verticalBox.add(btnReturn);

		// TABLE TITLE
		lblTitle = new JLabel("");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setBounds(136, 105, 500, 25);
		contentPane.add(lblTitle);

		// TABLE PANEL
		JPanel panel = new JPanel();
		panel.setBounds(136, 135, 1000, 500);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1000, 500);
		panel.add(scrollPane);

		tbl = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());

//                if (isCellSelected(row, column)) {
//                    ((JComponent) comp).setBorder(BorderFactory.createLineBorder(Color.ORANGE));
//                } else {
//                    ((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());
//                }
//                DefaultTableCellRenderer cellRenderer = (DefaultTableCellRenderer) renderer;           
//                if (column == getColumnCount() - 1) { // Check if it's the last column
//                	cellRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
//                } else {
//                	cellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
//                }

				return comp;
			}
		};

		tbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = tbl.getSelectedRow();
				enableBtns(true);

				selectedId = tbl.getValueAt(row, 0).toString();
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
		JButton btnAdd = new JButton("Add");
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
				case "Students":
//					StudentDialogView.showDialog();
					break;
				}
			}
		});
		btnAdd.setBounds(830, 100, 80, 30);
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
				}
				
				enableBtns(false);
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnUpdate.setBounds(937, 100, 85, 30);
		contentPane.add(btnUpdate);

		// DELETE BTN
		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		MyBtn.changeMyBtnStyle(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
				}
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDelete.setBounds(1047, 100, 85, 30);
		contentPane.add(btnDelete);

		// create table
		lblTitle.setText("Book List");
		
		lblInfo = new JLabel("Add data and Select any table row to update and delete.");
		lblInfo.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInfo.setBounds(136, 70, 1000, 25);
		contentPane.add(lblInfo);
		String[] cols = { "ID", "Title", "Author", "Publisher", "Genre", "Published at", "Price", "Qty" };
		int[] w = { 5, 50, 20, 30, 10, 20, 1, 5 };
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
			MyTblFunctions.updateBooksTable();
			break;
		case "Authors":
			MyTblFunctions.updateAuthorsTable();
			break;
		case "Genres":
			MyTblFunctions.updateGenresTable();
			break;
		case "Publishers":
			MyTblFunctions.updatePublishersTable();
			break;
		case "Donators":
			MyTblFunctions.updateDonatorsTable();
			break;
		case "Students":

			break;
		}
	}

	private void enableBtns(boolean enable) {
		btnUpdate.setEnabled(enable);
		btnDelete.setEnabled(enable);

		btnUpdate.setBackground(enable ? LibColors.PRIMARY_BG : Color.LIGHT_GRAY);
		btnDelete.setBackground(enable ? LibColors.PRIMARY_BG : Color.LIGHT_GRAY);
		
		if(!enable) {
			tbl.clearSelection();	// remove table row selection
		}
	}

	public static JButton[] getBtns() {
		JButton[] btns = { btnBooks, btnAuthors, btnGenres, btnPublishers, btnDonators, btnDonation };
		return btns;
	}
}
