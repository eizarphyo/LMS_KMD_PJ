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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

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
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

import components.MyBtn;
import components.TxtFieldFocusListener;
import controller.BookController;
import controller.DonationController;
import controller.DonationDetailController;
import controller.DonatorController;
import model.BookModel;
import model.DonationDetailModel;
import model.DonationModel;
import model.DonatorModel;
import utilities.AutoID;
import utilities.ChangeDate;
import utilities.InputValidator;
import utilities.LibColors;
import utilities.MyComboBox;
import utilities.MyTblFunctions;
import utilities.MyDate;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DonationView extends JDialog {
//	private JPanel contentPanel;
	private static final long serialVersionUID = 1L;

	private static JButton btnSave;
	private static JComboBox<String> cboDonatorName;
	private static JComboBox<String> cboTitle;
	private static JTextField txtQty;
	private static DonationView dialog;
	private static boolean update = false;
	private static Border b = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY);
	private final JPanel contentPanel = new JPanel();

	private JLabel lblDonationname;
	private JLabel lblDonatorid;
	private JLabel lblDonatorId;
	private static JLabel lblDate;
	private JLabel lblBook;
	private JLabel lblQty;
	private static JTable tblBooks;
	private static DefaultTableModel dtm = new DefaultTableModel();
	private JLabel lblAuthor;
	private static Vector<BookModel> selectedBooks = new Vector<>();
	private JLabel lblId;
	private JLabel lblBookId;
	private static JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		try {
//			dialog = new DonationView();
//			dialog.setTitle(AutoID.getPK("donation_id", "donation", "DON-"));
//			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//			dialog.setVisible(true);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static void showDialog() {
		try {
			update = false;
			dialog = new DonationView();
			dialog.setTitle(AutoID.getPK("donation_id", "donation", "DON-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			dtm.setRowCount(0);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDialog(String id) {
		try {
			update = true;
			dialog = new DonationView();

			dialog.setTitle(id);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			DonationController ctl = new DonationController();
			DonationModel don = new DonationModel();
			don.setDonationId(id);

			don = ctl.getOneDonationById(don);
			cboDonatorName.setSelectedItem(don.getDonatorName());

			List<DonationDetailModel> details = DonationDetailController.getAllDonationsById(id);
			lblDate.setText(details.get(0).getDate());

			for (DonationDetailModel d : details) {
				BookModel book = new BookModel();
				book.setId(d.getBookId());
				book.setTitle(d.getTitle());
				book.setAuthorName(d.getAuthorName());
				book.setQty(d.getQty());
				selectedBooks.add(book);
			}

			updateTableRows();

			cboTitle.setSelectedIndex(0);
			txtQty.setText("");

			cboDonatorName.setEnabled(false);
			txtQty.setEnabled(false);
			cboTitle.setEnabled(false);
			btnAdd.setEnabled(false);
			btnAdd.setBackground(Color.LIGHT_GRAY);
			
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the panel.
	 */
	public DonationView() {
		setTitle("Donation Detail Dialog View");
		selectedBooks.clear();

		setBounds(120, 100, 549, 597);
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lbl = new JLabel("Donation Entry");
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 16));
		lbl.setBounds(10, 10, 529, 25);
		contentPanel.add(lbl);

		lblDonationname = new JLabel("Donator Name:");
		lblDonationname.setBounds(10, 66, 100, 25);
		lblDonationname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblDonationname);

		cboDonatorName = new JComboBox<String>();
		MyComboBox.changeMyCboStyle(cboDonatorName);
		cboDonatorName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cboDonatorName.getSelectedIndex() > 0) {
					String donatorId = DonatorController.getIdByName(cboDonatorName.getSelectedItem().toString());
					lblDonatorId.setText(donatorId);
				}
			}
		});
		cboDonatorName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboDonatorName.setBounds(120, 66, 200, 25);
		contentPanel.add(cboDonatorName);

		lblDonatorid = new JLabel("Donator ID:");
		lblDonatorid.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDonatorid.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDonatorid.setBounds(355, 66, 100, 25);
		contentPanel.add(lblDonatorid);

		lblDonatorId = new JLabel("");
		lblDonatorId.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDonatorId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDonatorId.setBounds(465, 66, 60, 25);
		lblDonatorId.setBorder(b);
		contentPanel.add(lblDonatorId);

		lblDate = new JLabel(ChangeDate.toMyDateFormat());
		lblDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDate.setBounds(425, 30, 100, 20);
		contentPanel.add(lblDate);

		lblBook = new JLabel("Book Title:");
		lblBook.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBook.setBounds(10, 120, 100, 25);
		contentPanel.add(lblBook);

		cboTitle = new JComboBox<String>();
		MyComboBox.changeMyCboStyle(cboTitle);
		cboTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cboTitle.getSelectedIndex() > 0) {
					BookModel book = BookController.getOneBookByTitle(cboTitle.getSelectedItem().toString());

					lblAuthor.setText(book.getAuthorName());
					lblBookId.setText(book.getId());
					txtQty.requestFocus();
				}
			}
		});
		cboTitle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboTitle.setBorder(b);
		cboTitle.setBounds(120, 120, 405, 25);
		contentPanel.add(cboTitle);

		lblQty = new JLabel("Donated Qty:");
		lblQty.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQty.setBounds(280, 192, 100, 25);
		contentPanel.add(lblQty);

		txtQty = new JTextField();
		txtQty.addFocusListener(TxtFieldFocusListener.getFocusListener(txtQty));
		txtQty.setFont(new Font("Tahoma", Font.PLAIN, 13));
		txtQty.setHorizontalAlignment(SwingConstants.TRAILING);
		txtQty.setBounds(395, 194, 130, 25);
		txtQty.setBorder(b);
		contentPanel.add(txtQty);
		txtQty.setColumns(10);

		JPanel panelTable = new JPanel();
		panelTable.setBounds(10, 277, 515, 215);
		contentPanel.add(panelTable);
		panelTable.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 515, 228);
		panelTable.add(scrollPane);

		tblBooks = new JTable();
		tblBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(update) {
					return;
				}
				
				int r = tblBooks.getSelectedRow();

				cboTitle.setEnabled(false);
				enableAddBtn(false);

				lblBookId.setText(tblBooks.getValueAt(r, 1).toString());
				cboTitle.setSelectedItem(tblBooks.getValueAt(r, 2).toString());
				lblAuthor.setText(tblBooks.getValueAt(r, 3).toString());
				txtQty.setText(tblBooks.getValueAt(r, 4).toString());

			}
		});
		tblBooks.setRowHeight(27);
		tblBooks.setSelectionBackground(LibColors.PRIMARY_SELECT_BG);
		tblBooks.setIntercellSpacing(new Dimension(7, 0));

		// Change header height
		JTableHeader header = tblBooks.getTableHeader();
		Dimension headerPreferredSize = header.getPreferredSize();
		headerPreferredSize.height = 30;
		header.setPreferredSize(headerPreferredSize);

		Font headerFont = header.getFont();
		header.setFont(headerFont.deriveFont(Font.BOLD, 14));
//    header.setBackground(new Color(255,238,178));

		scrollPane.setViewportView(tblBooks);
		tblBooks.setFont(new Font("Tahoma", Font.PLAIN, 13));
		scrollPane.setViewportView(tblBooks);

		btnAdd = new JButton("Add");
		MyBtn.changeMyBtnStyle(btnAdd);
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = cboTitle.getSelectedItem().toString();
				if (!hasValidInputs() || bookAlreadySelected(title)) {
					return;
				}

				BookModel book = BookController.getOneBookByTitle(title);
				book.setQty(Integer.parseInt(txtQty.getText()));
				selectedBooks.add(book);
				updateTableRows();
				clearBookInfo();
			}
		});
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnAdd.setBounds(280, 235, 65, 25);
		contentPanel.add(btnAdd);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = cboTitle.getSelectedItem().toString();
				if (!hasValidInputs()) {
					return;
				}

				for (BookModel book : selectedBooks) {
					if (book.getTitle().equals(title)) {
						book.setQty(Integer.parseInt(txtQty.getText()));
					}
				}
				updateTableRows();
				clearBookInfo();
				cboTitle.setEnabled(true);
				enableAddBtn(true);
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnUpdate.setBounds(355, 235, 80, 25);
		contentPanel.add(btnUpdate);

		btnDelete = new JButton("Delete");
		btnDelete.setEnabled(false);
		MyBtn.changeMyBtnStyle(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = cboTitle.getSelectedItem().toString();

				for (BookModel book : selectedBooks) {
					if (book.getTitle().equals(title)) {
						selectedBooks.remove(book);
						break;
					}
				}

				updateTableRows();
				clearBookInfo();
				cboTitle.setEnabled(true);
				enableAddBtn(true);
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnDelete.setBounds(445, 235, 80, 25);
		contentPanel.add(btnDelete);

		JLabel lblauthor = new JLabel("Author:");
		lblauthor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblauthor.setBounds(10, 156, 100, 25);
		contentPanel.add(lblauthor);

		lblAuthor = new JLabel("");
		lblAuthor.setBorder(b);
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAuthor.setBounds(120, 157, 200, 25);
		contentPanel.add(lblAuthor);

		lblId = new JLabel("Book ID:");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblId.setBounds(10, 192, 100, 25);
		contentPanel.add(lblId);

		lblBookId = new JLabel("");
		lblBookId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBookId.setBorder(b);
		lblBookId.setBounds(120, 193, 60, 25);
		contentPanel.add(lblBookId);

		btnSave = new JButton(update ? "Close" :"Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(update) {
					dispose();
					return;
				}
				
				if (cboDonatorName.getSelectedIndex() <= 0 || selectedBooks.size() <= 0) {
					JOptionPane.showMessageDialog(null, "Please select donator and donated books");
					return;
				}

				DonationController dctl = new DonationController();
				DonationModel donation = new DonationModel();
				donation.setDonationId(dialog.getTitle());
				donation.setDate(lblDate.getText());
				donation.setDonatorId(lblDonatorId.getText());

				int totalQty = 0;
				for (BookModel book : selectedBooks) {
					totalQty += book.getQty();
				}
				donation.setTotalQty(totalQty);

				int ok = dctl.insert(donation);
				if (ok != 1) {
					JOptionPane.showMessageDialog(null, "Error creating donation");
					return;
				}

				// if not; user did not add or deleted books while updating
				for (BookModel book : selectedBooks) {
					DonationDetailController ctl = new DonationDetailController();
					DonationDetailModel detail = new DonationDetailModel();

					detail.setDonationId(donation.getDonationId());
					detail.setBookId(book.getId());
					detail.setQty(book.getQty());

					if (ctl.insert(detail) != 1) {
						JOptionPane.showMessageDialog(null, "Error creating donation details");
						return;
					}

					// donated qty + the qty in the library
					int bookQty = book.getQty() + BookController.getQty(book.getId());
					book.setQty(bookQty);
					BookController.updateQty(book);
				}
				JOptionPane.showMessageDialog(null, "Success!");
				MyTblFunctions.updateDonationsTable();
				clearAll();
				
			}
		});
		MyBtn.changeMyBtnStyle(btnSave);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSave.setBorder(b);
		btnSave.setBounds(231, 513, 89, 27);
		contentPanel.add(btnSave);

		fillComboBoxes();
		createTable();
		enableAddBtn(true);
	}

	void setColumnWidth(int i, int width) {
		DefaultTableColumnModel tcm = (DefaultTableColumnModel) tblBooks.getColumnModel();
		TableColumn tc = tcm.getColumn(i);
		tc.setPreferredWidth(width);
	}

	void createTable() {
		dtm.setColumnCount(0);
		dtm.addColumn("No.");
		dtm.addColumn("ID");
		dtm.addColumn("Title");
		dtm.addColumn("Author");
		dtm.addColumn("Qty");
		tblBooks.setModel(dtm);

		setColumnWidth(0, 40);
		setColumnWidth(1, 80);
		setColumnWidth(2, 280);
		setColumnWidth(3, 130);
		setColumnWidth(4, 50);
	}

	private boolean hasValidInputs() {
		if (cboDonatorName.getSelectedIndex() < 1 || cboTitle.getSelectedIndex() < 1 || txtQty.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Please fill the required fields first");
			txtQty.requestFocus();
			return false;
		} else if (!InputValidator.isAllDigits(txtQty.getText())) {
			JOptionPane.showMessageDialog(null, "Quantity must be number only");
			txtQty.requestFocus();
			txtQty.selectAll();
			return false;
		} else if (Integer.parseInt(txtQty.getText()) == 0) {
			JOptionPane.showMessageDialog(null, "Quantity must be greater than zero");
			txtQty.requestFocus();
			txtQty.selectAll();
			return false;
		}
		return true;
	}

	static void updateTableRows() {
		dtm.setRowCount(0);

		String[] row = new String[5];

		int i = 1;
		for (BookModel book : selectedBooks) {
			row[0] = i + "";
			row[1] = book.getId();
			row[2] = book.getTitle();
			row[3] = book.getAuthorName();
			row[4] = book.getQty() + "";

			dtm.addRow(row);
			i++;
		}
	}

	boolean bookAlreadySelected(String title) {
		for (BookModel book : selectedBooks) {
			if (book.getTitle().equals(title)) {
				JOptionPane.showMessageDialog(null, "Book Already Selected!");
				cboTitle.requestFocus();
				return true;
			}
		}
		return false;
	}

	void clearBookInfo() {
		cboTitle.setSelectedIndex(0);
		lblAuthor.setText("");
		lblBookId.setText("");
		txtQty.setText("");
		enableAddBtn(true);
	}

	void clearAll() {
		dialog.setTitle(AutoID.getPK("donation_id", "donation", "DON-"));
		cboDonatorName.setSelectedIndex(0);
		lblDonatorId.setText("");
		clearBookInfo();
		selectedBooks.clear();
		dtm.setRowCount(0);
	}

	void enableAddBtn(boolean enable) {
		btnAdd.setEnabled(enable);
		btnUpdate.setEnabled(!enable);
		btnDelete.setEnabled(!enable);

		MyBtn.changeMyBtnStyle(btnAdd);
		MyBtn.changeMyBtnStyle(btnUpdate);
		MyBtn.changeMyBtnStyle(btnDelete);
	}

	private void fillComboBoxes() {
		MyComboBox.fillComboItems("donator", "donator_name", cboDonatorName);
		MyComboBox.fillComboItems("book", "title", cboTitle);
	}

	void changeToBottomBorder(JTextField txt) {
		BorderFactory.createCompoundBorder(txt.getBorder(), BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
	}

	boolean hasSameBooks(List<BookModel> books, List<DonationDetailModel> details) {
		for (int i = 0; i < books.size(); i++) {
			String bookId = books.get(i).getId();

			for (DonationDetailModel d : details) {
				if (!d.getBookId().equals(bookId)) {
					return false;
				}
			}
		}
		return true;
	}

	boolean bookIsInDb(String bookId, List<DonationDetailModel> details) {
		for (DonationDetailModel d : details) {
			if (d.getBookId().equals(bookId)) {
				return true;
			}
		}
		return false;
	}

	List<String> getLeftBooks(List<BookModel> books, List<DonationDetailModel> details) {
		List<String> leftBooks = new ArrayList<>();
		for (DonationDetailModel d : details) {
			String bookId = d.getBookId();

			boolean found = false;
			for (BookModel book : books) {
				if (book.getId().equals(bookId)) {
					found = true;
					break;
				}
			}

			if (!found) {
				leftBooks.add(bookId);
			}
		}
		return leftBooks;
	}

	void reduceBookQty(String bookId, String donationId) {
		DonationDetailController ctl = new DonationDetailController();
		DonationDetailModel data = new DonationDetailModel();
		data.setDonationId(donationId);
		data.setBookId(bookId);

		List<DonationDetailModel> details = ctl.getAllDonationsByDonationIdAndBookId(data);

		for (DonationDetailModel d : details) {
			int qtyToBeRemoved = d.getQty();

			int bookQty = BookController.getQty(d.getBookId());
			int finalQty = bookQty - qtyToBeRemoved;

			BookModel book = new BookModel();
			book.setId(d.getBookId());
			book.setQty(finalQty);
			BookController.updateQty(book);

		}
	}

	void addBookAndUpdateDetails(List<DonationDetailModel> details) {
		for (int i = 0; i < selectedBooks.size(); i++) {
			BookModel book = selectedBooks.get(i);

			DonationDetailController ctl = new DonationDetailController();
			DonationDetailModel data = new DonationDetailModel();

			data.setDonationId(dialog.getTitle());
			data.setBookId(book.getId());
			data.setQty(book.getQty());

			int previousDonatedQty = ctl.getDonatedQtyByBookIdAndDonationId(data);
			int updatedQty = data.getQty();
//			System.out.println("after added");
//			System.out.println("prev " + previousDonatedQty + " updated " + updatedQty);
//			
			int finalQty = book.getQty();
			// လှူတဲ့ qty ကို လျှော့လိုက်ရင် book qty ကိုလည်း လျှော့
			if (previousDonatedQty > updatedQty) {
				int qtyToBeRemoved = previousDonatedQty - updatedQty;
				finalQty = BookController.getQty(book.getId()) - qtyToBeRemoved;
//				System.out.println(finalQty + " after removed " + qtyToBeRemoved);
			} else { // book qty ကို သွား
				int qtyToBeAdded = updatedQty - previousDonatedQty;
				finalQty = BookController.getQty(book.getId()) + qtyToBeAdded;
//				System.out.println("Before: " + BookController.getQty(book.getId()) + " " + finalQty + " after added "
//						+ qtyToBeAdded);
			}

			int ok = update ? ctl.update(data) : ctl.insert(data);
			if (ok != 1) {
				JOptionPane.showMessageDialog(null,
						"Error " + (update ? "updating" : "creating") + " donation details");
				return;
			}

//			int ok;
			if (bookIsInDb(book.getId(), details)) {
				ok = ctl.update(data);
			} else {
				ok = ctl.insert(data);
			}

			if (ok != 1) {
				JOptionPane.showMessageDialog(null, "Error updating donation details");
				return;
			}

			book.setQty(finalQty);
			ok = BookController.updateQty(book);

			// donated qty + the qty in the library
//			int bookQty = book.getQty() + BookController.getQty(book.getId());
//			book.setQty(bookQty);
//			BookController.updateQty(book);

			if (ok != 1) {
				JOptionPane.showMessageDialog(null, "Error updating book qty");
				return;
			}

		}

		JOptionPane.showMessageDialog(null, "Success!");
		MyTblFunctions.updateDonationsTable();
		clearAll();
		dispose();

	}

	int deleteBooksFromDetails(String bookId, String donationId) {
		DonationDetailController ctl = new DonationDetailController();
		DonationDetailModel data = new DonationDetailModel();

		data.setBookId(bookId);
		data.setDonationId(donationId);

		int qty2BeDeleted = ctl.getDonatedQtyByBookIdAndDonationId(data);

		if (ctl.deleteByBookIdAndDonationId(data) != 1) {
			JOptionPane.showMessageDialog(null, "Error Deleting donation details");
			return 0;
		}

		// the qty in the library - donated qty

		int bookQty = BookController.getQty(bookId) - qty2BeDeleted;
		BookModel book = new BookModel();
		book.setQty(bookQty);

		BookController.updateQty(book);
		return 1;
	}
}
