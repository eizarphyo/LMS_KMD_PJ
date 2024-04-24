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
	private static JComboBox<String> cboDonatorId;
	private static JComboBox<String> cboTitle;
	private static JTextField txtQty;
	private static DonationView dialog;
	private static boolean update = false;
	private static Border b = BorderFactory.createLineBorder(Color.gray);
	private final JPanel contentPanel = new JPanel();

	private JLabel lblDonationid;
	private JLabel lblDonatorname;
	private JLabel lblDonatorName;
	private JLabel lblDate;
	private JLabel lblBook;
	private JLabel lblQty;
	private JTable tblBooks;
	private DefaultTableModel dtm = new DefaultTableModel();
	private JLabel lblAuthor;
	private Vector<BookModel> selectedBooks = new Vector<>();
	private JLabel lblId;
	private JLabel lblBookId;
	private JButton btnAdd;
	private JButton btnUpdate;
	private JButton btnDelete;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new DonationView();
			dialog.setTitle(AutoID.getPK("donation_id", "donation", "DON-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDialog() {
		try {
			update = false;
			dialog = new DonationView();
			dialog.setTitle(AutoID.getPK("donation_id", "donation", "DON-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showDialog(String id) {
		try {
			update = true;
			dialog = new DonationView();
			dialog.setTitle(AutoID.getPK("donation_id", "donation", "DON-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

			DonationDetailController ctl = new DonationDetailController();
			DonationDetailModel dd  = ctl.getOneDonationById(id);

			cboDonatorId.setSelectedItem(dd.getDonationId());
			cboTitle.setSelectedItem(dd.getTitle());
			txtQty.setText(dd.getQty() + "");

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

		setBounds(120, 100, 549, 576);
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

		lblDonationid = new JLabel("Donator ID:");
		lblDonationid.setBounds(10, 66, 100, 25);
		lblDonationid.setFont(new Font("Tahoma", Font.PLAIN, 13));
		contentPanel.add(lblDonationid);

		cboDonatorId = new JComboBox<String>();
		cboDonatorId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cboDonatorId.getSelectedIndex() > 0) {
					String donatorName = DonatorController.getNameById(cboDonatorId.getSelectedItem().toString());
					lblDonatorName.setText(donatorName);
				}
			}
		});
		cboDonatorId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		cboDonatorId.setBounds(120, 66, 130, 26);
		cboDonatorId.setBorder(b);
		contentPanel.add(cboDonatorId);

		lblDonatorname = new JLabel("Donator Name:");
		lblDonatorname.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDonatorname.setBounds(265, 66, 100, 25);
		contentPanel.add(lblDonatorname);

		lblDonatorName = new JLabel("");
		lblDonatorName.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDonatorName.setBounds(375, 66, 150, 25);
		lblDonatorName.setBorder(b);
		contentPanel.add(lblDonatorName);

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
		cboTitle.setBounds(120, 120, 405, 26);
		contentPanel.add(cboTitle);

		lblQty = new JLabel("Donated Qty:");
		lblQty.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQty.setBounds(280, 192, 100, 25);
		contentPanel.add(lblQty);

		txtQty = new JTextField();
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
		scrollPane.setBounds(0, 0, 515, 216);
		panelTable.add(scrollPane);

		tblBooks = new JTable();
		tblBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int r = tblBooks.getSelectedRow();

				lblBookId.setText(tblBooks.getValueAt(r, 1).toString());
				cboTitle.setSelectedItem(tblBooks.getValueAt(r, 2).toString());
				lblAuthor.setText(tblBooks.getValueAt(r, 3).toString());
				txtQty.setText(tblBooks.getValueAt(r, 4).toString());
				
				cboTitle.setEnabled(false);
				enableAddBtn(false);
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
		btnAdd.setBounds(280, 230, 65, 25);
		contentPanel.add(btnAdd);

		btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = cboTitle.getSelectedItem().toString();
				if (!hasValidInputs()) {
					return;
				}

				for(BookModel book: selectedBooks) {
					if(book.getTitle().equals(title)) {
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
		btnUpdate.setBounds(355, 230, 80, 25);
		contentPanel.add(btnUpdate);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = cboTitle.getSelectedItem().toString();
				
				for(BookModel book: selectedBooks) {
					if(book.getTitle().equals(title)) {
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
		btnDelete.setBounds(445, 230, 80, 25);
		contentPanel.add(btnDelete);

		JLabel lblauthor = new JLabel("Author:");
		lblauthor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblauthor.setBounds(10, 156, 100, 25);
		contentPanel.add(lblauthor);

		lblAuthor = new JLabel("");
		lblAuthor.setBorder(b);
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblAuthor.setBounds(120, 157, 150, 25);
		contentPanel.add(lblAuthor);

		lblId = new JLabel("Book ID:");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblId.setBounds(10, 192, 100, 25);
		contentPanel.add(lblId);

		lblBookId = new JLabel("");
		lblBookId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblBookId.setBorder(b);
		lblBookId.setBounds(120, 193, 150, 25);
		contentPanel.add(lblBookId);

		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cboDonatorId.getSelectedIndex()<=0 || selectedBooks.size() <= 0) {
					JOptionPane.showMessageDialog(null, "Please select donator and donated books");
					return;
				}
				
				
				DonationController dctl = new DonationController();
				DonationModel donation = new DonationModel();
				donation.setDonationId(dialog.getTitle());
				donation.setDate(lblDate.getText());
				donation.setDonatorId(cboDonatorId.getSelectedItem().toString());
				
				int totalQty = 0;
				for(BookModel book: selectedBooks) {
					totalQty += book.getQty();
				}
				donation.setTotalQty(totalQty);
				
				if(dctl.insert(donation) != 1) {
					JOptionPane.showMessageDialog(null, "Error creating donation");
					return;
				}
				
				for(BookModel book: selectedBooks) {
					DonationDetailController ctl = new DonationDetailController();
					DonationDetailModel detail = new DonationDetailModel();
					
					detail.setDonationId(donation.getDonationId());
					detail.setBookId(book.getId());
					detail.setQty(book.getQty());
					
					if(ctl.insert(detail) != 1) {
						JOptionPane.showMessageDialog(null, "Error creating donation details");
						return;
					}
					
					// donated qty + the qty in the library
					int bookQty = book.getQty() + BookController.getQty(book.getId());
					book.setQty(bookQty);
					
					BookController.updateQty(book);
				

				}
				JOptionPane.showMessageDialog(null, "Success!");
				clearAll();
			}
		});
		MyBtn.changeMyBtnStyle(btnSave);
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSave.setBorder(b);
		btnSave.setBounds(222, 503, 89, 27);
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
		if (cboDonatorId.getSelectedIndex() < 1 || cboTitle.getSelectedIndex() < 1 || txtQty.getText().isBlank()) {
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

	void updateTableRows() {
		dtm.setRowCount(0);

		String[] row = new String[5];

		int i = 1;
		for (BookModel book : selectedBooks) {
			row[0] = i + "";
			row[1] = book.getId();
			row[2] = book.getTitle();
			row[3] = book.getAuthorName();
			row[4] = book.getQty() + "";
			;

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
		cboDonatorId.setSelectedIndex(0);
		lblDonatorName.setText("");
		clearBookInfo();
		selectedBooks.clear();
		dtm.setRowCount(0);
	}
	
	void enableAddBtn(boolean enable) {
		btnAdd.setEnabled(enable);
		btnUpdate.setEnabled(!enable);
		btnDelete.setEnabled(!enable);
	}

	private void fillComboBoxes() {
		MyComboBox.fillComboItems("donator", "donator_id", cboDonatorId);
		MyComboBox.fillComboItems("book", "title", cboTitle);
	}
}
