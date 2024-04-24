package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import components.MyBtn;
import components.MyTable;
import controller.BookController;
import controller.BorrowController;
import controller.BorrowDetailController;
import model.BookModel;
import model.BorrowDetailModel;
import model.BorrowModel;
import utilities.AutoID;
import utilities.LibColors;
import utilities.ChangeDate;

import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.sql.Date;

public class BorrowDialogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static BorrowDialogView dialog;
	private JTextField txtStuId;
	private JTextField txtSearch;
	private JTable tblBooks;
	private DefaultTableModel dtm = new DefaultTableModel();
	private List<BookModel> selectedBooks = new ArrayList<>();
	private JButton btnRemove;
	private JLabel lblQty;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new BorrowDialogView();
			dialog.setTitle(AutoID.getPK("borrow_id", "borrow", "BRW-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDialog() {
		try {
			dialog = new BorrowDialogView();
			dialog.setTitle(AutoID.getPK("borrow_id", "borrow", "BRW-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BorrowDialogView() {
		setBounds(100, 100, 553, 603);
//		setBounds(100, 100, 600, 578);

		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblDate = new JLabel(ChangeDate.toMyDateFormat());
		lblDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDate.setBounds(443, 8, 70, 20);
		contentPanel.add(lblDate);

		JLabel lblsid = new JLabel("Student ID:");
		lblsid.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblsid.setBounds(21, 60, 100, 20);
		contentPanel.add(lblsid);

		txtStuId = new JTextField();
		txtStuId.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtStuId.setBounds(131, 60, 150, 20);
		contentPanel.add(txtStuId);
		txtStuId.setColumns(10);

		JLabel lblbbooks = new JLabel("Enter Book ID:");
		lblbbooks.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblbbooks.setBounds(21, 89, 100, 20);
		contentPanel.add(lblbbooks);

		txtSearch = new JTextField();
		txtSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_ENTER) {
					return;
				}

				if (txtSearch.getText().length() != 8 || !txtSearch.getText().startsWith("BOK-")) {
					JOptionPane.showMessageDialog(null, "Invalid Book ID!\nRequired Format: BOK-XXXX ");
					txtSearch.requestFocus();
					txtSearch.selectAll();
					return;
				}

				if (containsBook(txtSearch.getText(), selectedBooks)) {
					JOptionPane.showMessageDialog(null, "Book already selected!");
					txtSearch.requestFocus();
					txtSearch.selectAll();
					return;
				}

				BookController ctl = new BookController();
				BookModel book = new BookModel();

				book.setId(txtSearch.getText());

				book = ctl.getOneBookById(book);
				if (book == null) {
					JOptionPane.showMessageDialog(null, "Book does not exist!");
					txtSearch.requestFocus();
					txtSearch.selectAll();
					return;
				} else if (book.getQty() <= 0) {
					JOptionPane.showMessageDialog(null, "This book is unavailable");
					txtSearch.requestFocus();
					txtSearch.selectAll();
					return;
				}

				selectedBooks.add(book);
				lblQty.setText(selectedBooks.size() + "");
				updateTableRows();
				txtSearch.setText("");
				txtSearch.requestFocus();
			}
		});
		txtSearch.setBounds(131, 91, 150, 20);
		contentPanel.add(txtSearch);
		txtSearch.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Selected Books:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 120, 519, 397);
		contentPanel.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 21, 499, 318);
		panel.add(scrollPane);

		tblBooks = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				((JComponent) comp).setBorder(BorderFactory.createEmptyBorder());
				return comp;
			}
		};
		tblBooks.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnRemove.setVisible(true);
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

		tblBooks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		scrollPane.setViewportView(tblBooks);
		tblBooks.setFont(new Font("Tahoma", Font.PLAIN, 13));

		lblQty = new JLabel("0");
		lblQty.setBounds(429, 370, 80, 20);
		panel.add(lblQty);
		lblQty.setHorizontalAlignment(SwingConstants.TRAILING);
		lblQty.setFont(new Font("Tahoma", Font.PLAIN, 13));

		JLabel lblqty = new JLabel("Total Books:");
		lblqty.setBounds(319, 370, 100, 20);
		panel.add(lblqty);
		lblqty.setHorizontalAlignment(SwingConstants.TRAILING);
		lblqty.setFont(new Font("Tahoma", Font.PLAIN, 13));

		JLabel lbldue = new JLabel("Due Date:");
		lbldue.setBounds(339, 350, 80, 20);
		lbldue.setHorizontalAlignment(SwingConstants.TRAILING);
		lbldue.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lbldue);

		JLabel lblDue = new JLabel(ChangeDate.toDateAfterDays(7));
		lblDue.setBounds(439, 350, 70, 20);
		lblDue.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDue.setFont(new Font("Tahoma", Font.PLAIN, 13));
		panel.add(lblDue);

		JLabel title = new JLabel("Borrow any available books in the library");
		title.setFont(new Font("Tahoma", Font.BOLD, 16));
		title.setBounds(21, 5, 351, 25);
		contentPanel.add(title);

		btnRemove = new JButton("Remove");
		btnRemove.setVisible(false);
		MyBtn.changeMyBtnStyle(btnRemove);
		btnRemove.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int i = tblBooks.getSelectedRow();
				selectedBooks.remove(i);

				updateTableRows();
				lblQty.setText(selectedBooks.size() + "");
				tblBooks.clearSelection();
				btnRemove.setVisible(false);
			}
		});
		btnRemove.setBounds(428, 87, 85, 25);
		contentPanel.add(btnRemove);
		
		JButton btnBorrow = new JButton("Borrow");
		btnBorrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (hasValidInputs()) {
					BorrowController bctl = new BorrowController();
					BorrowModel borrow = new BorrowModel();

					borrow.setBorrowId(dialog.getTitle());
					borrow.setStuId(txtStuId.getText());
					borrow.setBorrowAt(Date.valueOf(LocalDate.now()));
					borrow.setBorrowQty(selectedBooks.size());
					borrow.setReturnedQty(0);
					borrow.setQtyToBeReturned(selectedBooks.size());
					borrow.setAllReturned(false);

					if (bctl.insert(borrow) == 1) {
						BorrowDetailController detailsCtl = new BorrowDetailController();

						for (BookModel book : selectedBooks) {
							BorrowDetailModel details = new BorrowDetailModel();

							details.setBookId(book.getId());
							details.setBorrowId(borrow.getBorrowId());
							details.setReturned(false);

							if (detailsCtl.insert(details) == 1) {
								BookController bookCtl = new BookController();
								BookModel b = new BookModel();
								b.setId(book.getId());
								b.setQty(book.getQty() - 1);

								if (bookCtl.updateQty(b) != 1) {
									JOptionPane.showMessageDialog(null, "Something went wrong");
									return;
								}
							}
						}
					}

					JOptionPane.showMessageDialog(null, "Success!");
					dialog.setTitle(AutoID.getPK("borrow_id", "borrow", "BRW-"));
					clear();

				}
			}
		});
		MyBtn.changeMyBtnStyle(btnBorrow);
		btnBorrow.setBounds(424, 528, 89, 27);
		contentPanel.add(btnBorrow);

		createTable();
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
		tblBooks.setModel(dtm);

		setColumnWidth(0, 40);
		setColumnWidth(1, 80);
		setColumnWidth(2, 280);
		setColumnWidth(3, 150);
	}

	void updateTableRows() {
		dtm.setRowCount(0);

		String[] row = new String[4];

		int i = 1;
		for (BookModel book : selectedBooks) {
			row[0] = i + "";
			row[1] = book.getId();
			row[2] = book.getTitle();
			row[3] = book.getAuthorName();

			dtm.addRow(row);
			i++;
		}
	}

	void clear() {
		txtStuId.setText("");
		txtStuId.requestFocus();

		txtSearch.setText("");
		selectedBooks.clear();
		lblQty.setText(selectedBooks.size() + "");
		dtm.setRowCount(0);

		btnRemove.setVisible(false);
	}

	boolean containsBook(String id, List<BookModel> books) {
		for (BookModel book : books) {
			if (book.getId().equals(id)) {
				return true;
			}
		}
		return false;
	}

	boolean hasValidInputs() {
		if (txtStuId.getText().isBlank()) {
			JOptionPane.showMessageDialog(null, "Please enter student ID first");
			txtStuId.requestFocus();
			return false;
		} else if (txtStuId.getText().length() != 8 || !txtStuId.getText().startsWith("STU-")) {
			JOptionPane.showMessageDialog(null, "Invalid student ID!\nRequired Format: STU-XXXX");
			txtStuId.requestFocus();
			txtStuId.selectAll();
			return false;
		} else if (selectedBooks.size() <= 0) {
			JOptionPane.showMessageDialog(null, "Please select books first");
			txtSearch.requestFocus();
			return false;
		}
		return true;
	}
}
