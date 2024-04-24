package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import components.MyBtn;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import controller.BookController;
import controller.BorrowController;
import controller.BorrowDetailController;
import controller.ReturnController;
import controller.ReturnDetailController;
import model.BookModel;
import model.BorrowDetailModel;
import model.BorrowModel;
import model.ReturnDetailModel;
import model.ReturnModel;
import utilities.AutoID;
import utilities.ChangeDate;
import utilities.CompareDates;
import utilities.LibColors;
import utilities.MyComboBox;

import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;

public class ReturnDialogView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField txtStuId;
	private JTable tblReturn;
	private JComboBox<String> cboBorrowId;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Object[][] rowData;
	private Vector<String> books2bReturned = new Vector<String>();
	private JLabel lblLateFees;
	private JLabel lblTotalFine;
	private JPanel checkPanel;
	private JLabel lblDmgFine;
	private static ReturnDialogView dialog;
	private List<BorrowDetailModel> borrowData = new ArrayList<>();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			dialog = new ReturnDialogView();
			dialog.setTitle(AutoID.getPK("return_id", "lib.return", "RTN-"));

			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void showDialog() {
		try {
			dialog = new ReturnDialogView();
			dialog.setTitle(AutoID.getPK("return_id", "lib.return", "RTN-"));
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ReturnDialogView() {
		setBounds(100, 100, 553, 603);

		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		int centerX = (int) (screenDimension.getWidth() - getWidth()) / 2;
		int centerY = (int) (screenDimension.getHeight() - getHeight()) / 2;
		setLocation(centerX, centerY);

		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblTitle = new JLabel("Return Borrowed Books");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setBounds(10, 0, 280, 25);
		contentPanel.add(lblTitle);

		JLabel lblStuId = new JLabel("Student ID:");
		lblStuId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblStuId.setBounds(10, 50, 150, 25);
		contentPanel.add(lblStuId);

		txtStuId = new JTextField();
		txtStuId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_ENTER) {
					return;
				}

				if (isStuIdValid()) {
					BorrowController ctl = new BorrowController();

					List<BorrowModel> borrows = ctl.getAllUnfinishedBorrowsByStudentId(txtStuId.getText());

					cboBorrowId.removeAllItems();
					cboBorrowId.addItem("--Select--");
					for (BorrowModel b : borrows) {
						cboBorrowId.addItem(b.getBorrowId() + ", " + ChangeDate.toMyDateFormat(b.getBorrowAt()));

					}
					cboBorrowId.setEnabled(true);
					MyComboBox.changeMyCboStyle(cboBorrowId);
				}

			}
		});
		txtStuId.setBounds(170, 50, 150, 25);
		contentPanel.add(txtStuId);
		txtStuId.setColumns(10);

		JLabel lblbid = new JLabel("Select Borrow ID:");
		lblbid.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblbid.setBounds(10, 85, 150, 25);
		contentPanel.add(lblbid);

		cboBorrowId = new JComboBox<String>();

		cboBorrowId.addItem("--SELECT--");
		cboBorrowId.setEnabled(false);
//		MyComboBox.changeMyCboStyle(cboBorrowId);
		cboBorrowId.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAll();

				if (cboBorrowId.getSelectedIndex() > 0) {

					String[] parts = cboBorrowId.getSelectedItem().toString().split(", ");

					BorrowDetailController ctl = new BorrowDetailController();

					borrowData = ctl.getAllDetailsByBorrowId(parts[0]);

					Object[][] rows = new Object[borrowData.size()][6];
					dtm.setRowCount(0);

					checkPanel.removeAll();
					checkPanel.revalidate();
					checkPanel.repaint();
					for (int i = 0; i < borrowData.size(); i++) {
						BorrowDetailModel borrow = borrowData.get(i);
						rows[i][0] = borrow.isReturned() ? true : false;
						rows[i][1] = i + 1;
						rows[i][2] = borrow.getBookId();
						rows[i][3] = borrow.getBookTitle();
						rows[i][4] = borrow.getAuthorName();
						
						boolean isDamaged = false;
						if(borrow.isReturned()) {
							isDamaged = ReturnDetailController.isDamaged(borrow.getBookId(), borrow.getBorrowId());
						}
						
						rows[i][5] = isDamaged ? "Damaged" : "Good";
							
						dtm.addRow(rows[i]);
					}

				}
			}
		});
		cboBorrowId.setBounds(170, 85, 150, 25);
		contentPanel.add(cboBorrowId);

		JPanel panel = new JPanel();
		panel.setBorder(
				new TitledBorder(null, "Select Books to Return", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 125, 519, 335);
		contentPanel.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();

		scrollPane.setBounds(10, 20, 499, 195);
		panel.add(scrollPane);

		// Column names
		String[] columnNames = { "", "No.", "ID", "Title", "Author", "Condition" };

		// Create a table model
		dtm = new DefaultTableModel(rowData, columnNames) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return columnIndex == 0 ? Boolean.class : String.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				// Check if the row should be editable based on the returned value
				boolean returned = borrowData.get(row).isReturned();
				return !returned;
			}
		};

		tblReturn = new JTable(dtm) {
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				// Only allow selecting multiple rows, not individual rows
				if (columnIndex == 0) {
					super.changeSelection(rowIndex, columnIndex, toggle, extend);
				}
			}

//			@Override
//			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
//				Component component = super.prepareRenderer(renderer, row, column);
//
//				// Check if row selection is disabled
//				boolean isSelectionDisabled = (Boolean) getValueAt(row, 0);
//
//				// Change row style if selection is disabled
//				if (isSelectionDisabled) {
//					component.setBackground(Color.LIGHT_GRAY);
//					component.setForeground(Color.RED); // Change text color if needed
//				} else {
//					// Reset to default style
//					component.setBackground(getBackground());
//					component.setForeground(getForeground());
//				}
//
//				return component;
//			}

		};

		// Set selection mode to multiple intervals
		tblReturn.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		tblReturn.getColumnModel().getColumn(0).setCellRenderer(new TransparentCheckboxRenderer());

		// select book
		tblReturn.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox() {
			{
				addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int i = tblReturn.getSelectedRow();

						String[] parts = cboBorrowId.getSelectedItem().toString().split(", ");

						String borrowId = parts[0];
						String bookId = tblReturn.getValueAt(i, 2).toString();

						if (isSelected()) {
							int r = tblReturn.getSelectedRow();

							JCheckBox checkbox = new JCheckBox(tblReturn.getValueAt(r, 3).toString());
							checkbox.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									int r = getRowIndexByBookTitle(tblReturn, 3, checkbox.getText());

									BookModel book = BookController.getOneBookByTitle(checkbox.getText());

									int price = book.getPrice();
									int fine = (int) Math.round(price * 0.1);

									int dmgFees = Integer.parseInt(lblDmgFine.getText().split(" ")[0]);
									int totalFine = Integer.parseInt(lblTotalFine.getText().split(" ")[0]);

									if (checkbox.isSelected()) {
										dtm.setValueAt("Damaged", r, 5);

										dmgFees += fine;
										totalFine += fine;
									} else {
										dtm.setValueAt("Good", r, 5);
										dmgFees -= fine;
										totalFine -= fine;
									}

									lblDmgFine.setText(dmgFees + " Ks.");
									lblTotalFine.setText(totalFine + " Ks.");
								}
							});
							checkPanel.add(checkbox);

							checkPanel.revalidate();
							checkPanel.repaint();

							if (!checkPanel.isVisible()) {
								checkPanel.setVisible(true);
							}

							books2bReturned.add(bookId);

							BorrowModel borrow = BorrowController.getOneBorrowById(borrowId);

							if (CompareDates.isDue(borrow.getBorrowAt())
									&& lblLateFees.getText().split(" ")[0].equals("0")) {
								int lateFees = Integer.parseInt(lblLateFees.getText().split(" ")[0])
										+ LibColors.LATE_FINE;
								lblLateFees.setText(lateFees + " Ks.");

								int totalFine = Integer.parseInt(lblTotalFine.getText().split(" ")[0]) + lateFees;
								lblTotalFine.setText(totalFine + " Ks.");
							}

							return;

						}

						// if checkbox is not selected

						String title = tblReturn.getValueAt(i, 3).toString();
						Component[] components = checkPanel.getComponents();
						for (Component component : components) {
							JCheckBox checkbox = (JCheckBox) component;

							if (checkbox.getText().equals(title)) { // if the checkbox's text is the same as selected
																	// row's book title
								checkPanel.remove(checkbox); // remove checkbox from panel

								if (checkPanel.getComponentCount() <= 0)
									checkPanel.setVisible(false);
								checkPanel.revalidate();
								checkPanel.repaint();
							}
						}

						books2bReturned.remove(bookId);
						if (books2bReturned.size() <= 0) {
							int lateFees = Integer.parseInt(lblLateFees.getText().split(" ")[0]);
							int totalFine = Integer.parseInt(lblTotalFine.getText().split(" ")[0]);

							totalFine -= lateFees;
							lblLateFees.setText("0 Ks.");
							lblTotalFine.setText(totalFine + " Ks.");
						}
					}
				});
			}
		}));

		// Set column width
		tblReturn.getColumnModel().getColumn(0).setPreferredWidth(27);
		tblReturn.getColumnModel().getColumn(1).setPreferredWidth(33);
		tblReturn.getColumnModel().getColumn(2).setPreferredWidth(71);
		tblReturn.getColumnModel().getColumn(3).setPreferredWidth(179);
		tblReturn.getColumnModel().getColumn(4).setPreferredWidth(94);
		tblReturn.getColumnModel().getColumn(5).setPreferredWidth(92);

		tblReturn.setRowHeight(27);
		tblReturn.setSelectionBackground(LibColors.PRIMARY_SELECT_BG);
		// Disable table's row selection
		tblReturn.setRowSelectionAllowed(false);

		tblReturn.setIntercellSpacing(new Dimension(7, 0));

		// Change header height
		JTableHeader header = tblReturn.getTableHeader();
		Dimension headerPreferredSize = header.getPreferredSize();
		headerPreferredSize.height = 30;
		header.setPreferredSize(headerPreferredSize);

		Font headerFont = header.getFont();
		header.setFont(headerFont.deriveFont(Font.BOLD, 14));

		scrollPane.setViewportView(tblReturn);

		checkPanel = new JPanel();
		checkPanel.setBorder(
				new TitledBorder(null, "Select Damaged Books:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		checkPanel.setVisible(false);
		checkPanel.setBounds(10, 226, 499, 98);
		panel.add(checkPanel);

		JLabel lbld = new JLabel("Late Fine:");
		lbld.setHorizontalAlignment(SwingConstants.TRAILING);
		lbld.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbld.setBounds(339, 471, 80, 20);
		contentPanel.add(lbld);

		JLabel lbltf = new JLabel("Total Fine:");
		lbltf.setHorizontalAlignment(SwingConstants.TRAILING);
		lbltf.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lbltf.setBounds(339, 510, 80, 20);
		contentPanel.add(lbltf);

		lblTotalFine = new JLabel("0 Ks.");
		lblTotalFine.setHorizontalAlignment(SwingConstants.TRAILING);
		lblTotalFine.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTotalFine.setBounds(429, 510, 100, 20);
		contentPanel.add(lblTotalFine);

		JLabel lblDate = new JLabel(ChangeDate.toMyDateFormat());
		lblDate.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDate.setBounds(449, 3, 80, 20);
		contentPanel.add(lblDate);

		lblLateFees = new JLabel("0 Ks.");
		lblLateFees.setBounds(429, 471, 100, 16);
		contentPanel.add(lblLateFees);
		lblLateFees.setHorizontalAlignment(SwingConstants.TRAILING);
		lblLateFees.setFont(new Font("Tahoma", Font.PLAIN, 13));

		JLabel lblDamage = new JLabel("Damage Fine:");
		lblDamage.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDamage.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDamage.setBounds(319, 490, 100, 20);
		contentPanel.add(lblDamage);

		lblDmgFine = new JLabel("0 Ks.");
		lblDmgFine.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDmgFine.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDmgFine.setBounds(429, 490, 100, 20);
		contentPanel.add(lblDmgFine);

		JButton btnReturn = new JButton("Return");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtStuId.getText().isBlank())
					return;
				else if (!isStuIdValid())
					return;
				else if (cboBorrowId.getSelectedIndex() <= 0) {
					JOptionPane.showMessageDialog(null, "Please select a borrow ID first");
					cboBorrowId.requestFocus();
					return;
				}

				ReturnController ctl = new ReturnController();
				ReturnModel ret = new ReturnModel();

				String borrowId = cboBorrowId.getSelectedItem().toString().split(", ")[0];

				ret.setStuId(txtStuId.getText());
				ret.setReturnId(dialog.getTitle());
				ret.setBorrowId(borrowId);
				ret.setReturnedQty(books2bReturned.size());
				ret.setReturnedAt(Date.valueOf(LocalDate.now()));
				ret.setLateFine(Integer.parseInt(lblLateFees.getText().split(" ")[0]));
				ret.setTotalFine(Integer.parseInt(lblTotalFine.getText().split(" ")[0]));

				if (ctl.insert(ret) != 1) {
					JOptionPane.showMessageDialog(null, "Cannot Return");
					return;
				}
				// finished creating return

				ReturnDetailController dctl = new ReturnDetailController();

				for (String bookId : books2bReturned) {
					ReturnDetailModel d = new ReturnDetailModel();

					d.setBookId(bookId);
					d.setReturnId(dialog.getTitle());

					String title = BookController.getTitleById(bookId);

					int r = getRowIndexByBookTitle(tblReturn, 3, title);
					String dmg = tblReturn.getValueAt(r, 5).toString();

					d.setDamaged(dmg.equals("Damaged"));

					BookModel book = BookController.getOneBookByTitle(title);
					if (dmg.equals("Damaged")) {

						int price = book.getPrice();
						int fine = (int) Math.round(price * 0.1);

						d.setDamageFees(fine);
					}

					if (dctl.insert(d) != 1) {
						JOptionPane.showMessageDialog(null, "Error creating return details");
						return;
					}

					book.setQty(book.getQty() + 1);
					BookController bookCtl = new BookController();

					// increase and update book's qty
					if (bookCtl.updateQty(book) != 1) {
						JOptionPane.showMessageDialog(null, "Error updating book qty");
						return;
					}

					// update borrow_details' is_return field to true
					BorrowDetailModel borrowDetail = new BorrowDetailModel();

					borrowDetail.setBorrowId(borrowId);
					borrowDetail.setBookId(bookId);

					if (BorrowDetailController.updateIsReturnedByBorrowIdAndBookId(borrowDetail) != 1) {
						JOptionPane.showMessageDialog(null, "Error updating is_return field of Borrow Details");
						return;
					}

				}
				// finished creating return details

				// NEED TO UPDATE RETURN QTY --------------

				BorrowModel borrow = BorrowController.getOneBorrowById(borrowId);

				int returnedQty = borrow.getReturnedQty();

				borrow.setReturnedQty(returnedQty + books2bReturned.size());
				borrow.setQtyToBeReturned(borrow.getBorrowQty() - borrow.getReturnedQty());

				borrow.setAllReturned(borrow.getQtyToBeReturned() == 0);

				if (BorrowController.updateReturnQty(borrow) != 1) {
					JOptionPane.showMessageDialog(null, "Error updating returned_qty in Borrow");
					return;
				}

				JOptionPane.showMessageDialog(null, "Success!");
				txtStuId.setText("");
				txtStuId.requestFocus();
				cboBorrowId.setSelectedIndex(0);
				cboBorrowId.setEnabled(false);

				clearAll();
				dialog.setTitle(AutoID.getPK("return_id", "lib.return", "RTN-"));
			}
		});
		MyBtn.changeMyBtnStyle(btnReturn);
		btnReturn.setBounds(440, 532, 89, 27);
		contentPanel.add(btnReturn);

		// -----------------
//		{
//			JPanel buttonPane = new JPanel();
//			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
//			getContentPane().add(buttonPane, BorderLayout.SOUTH);
//
//			JButton btnReturn = new JButton("Return");
//
//			btnReturn.addActionListener(new ActionListener() {
//				public void actionPerformed(ActionEvent e) {
//					if (txtStuId.getText().isBlank())
//						return;
//					else if (!isStuIdValid())
//						return;
//					else if (cboBorrowId.getSelectedIndex() <= 0) {
//						JOptionPane.showMessageDialog(null, "Please select a borrow ID first");
//						cboBorrowId.requestFocus();
//						return;
//					}
//
//					ReturnController ctl = new ReturnController();
//					ReturnModel ret = new ReturnModel();
//
//					String borrowId = cboBorrowId.getSelectedItem().toString().split(", ")[0];
//
//					ret.setStuId(txtStuId.getText());
//					ret.setReturnId(dialog.getTitle());
//					ret.setBorrowId(borrowId);
//					ret.setReturnedQty(books2bReturned.size());
//					ret.setReturnedAt(Date.valueOf(LocalDate.now()));
//					ret.setLateFine(Integer.parseInt(lblLateFees.getText().split(" ")[0]));
//					ret.setTotalFine(Integer.parseInt(lblTotalFine.getText().split(" ")[0]));
//
//					if (ctl.insert(ret) != 1) {
//						JOptionPane.showMessageDialog(null, "Cannot Return");
//						return;
//					}
//					// finished creating return
//
//					ReturnDetailController dctl = new ReturnDetailController();
//
//					for (String bookId : books2bReturned) {
//						ReturnDetailModel d = new ReturnDetailModel();
//
//						d.setBookId(bookId);
//						System.out.println(bookId + " " + d.getBookId());
//						d.setReturnId(dialog.getTitle());
//
//						String title = BookController.getTitleById(bookId);
//
//						int r = getRowIndexByBookTitle(tblReturn, 3, title);
//						String dmg = tblReturn.getValueAt(r, 5).toString();
//
//						d.setDamaged(dmg.equals("Damaged"));
//
//						BookModel book = BookController.getOneBookByTitle(title);
//						if (dmg.equals("Damaged")) {
//
//							int price = book.getPrice();
//							int fine = (int) Math.round(price * 0.1);
//
//							d.setDamageFees(fine);
//						}
//
//						if (dctl.insert(d) != 1) {
//							JOptionPane.showMessageDialog(null, "Error creating return details");
//							return;
//						}
//
//						book.setQty(book.getQty() + 1);
//						BookController bookCtl = new BookController();
//
//						// increase and update book's qty
//						if (bookCtl.updateQty(book) != 1) {
//							JOptionPane.showMessageDialog(null, "Error updating book qty");
//							return;
//						}
//
//						// update borrow_details' is_return field to true
//						BorrowDetailModel borrowDetail = new BorrowDetailModel();
//
//						borrowDetail.setBorrowId(borrowId);
//						borrowDetail.setBookId(bookId);
//
//						if (BorrowDetailController.updateIsReturnedByBorrowIdAndBookId(borrowDetail) != 1) {
//							JOptionPane.showMessageDialog(null, "Error updating is_return field of Borrow Details");
//							return;
//						}
//
//					}
//					// finished creating return details
//
//					// NEED TO UPDATE RETURN QTY --------------
//
//					BorrowModel borrow = BorrowController.getOneBorrowById(borrowId);
//
//					int returnedQty = borrow.getReturnedQty();
//
//					borrow.setReturnedQty(returnedQty + books2bReturned.size());
//					borrow.setQtyToBeReturned(borrow.getBorrowQty() - borrow.getReturnedQty());
//
//					borrow.setAllReturned(borrow.getQtyToBeReturned() == 0);
//
//					if (BorrowController.updateReturnQty(borrow) != 1) {
//						JOptionPane.showMessageDialog(null, "Error updating returned_qty in Borrow");
//						return;
//					}
//
//					JOptionPane.showMessageDialog(null, "Success!");
//					txtStuId.setText("");
//					cboBorrowId.setSelectedIndex(0);
//					cboBorrowId.setEnabled(false);
//
//					clearAll();
//					dialog.setTitle(AutoID.getPK("return_id", "lib.return", "RTN-"));
//				}
//			});
//			buttonPane.add(btnReturn);
//
//		}
		// ----

	}

	void clearAll() {
		books2bReturned.clear();
		lblLateFees.setText("0 Ks.");
		lblDmgFine.setText("0 Ks.");
		lblTotalFine.setText("0 Ks.");

		dtm.setRowCount(0);
		checkPanel.removeAll();
		checkPanel.revalidate();
		checkPanel.repaint();
	}

	void setColumnWidth(int i, int width) {
		DefaultTableColumnModel tcm = (DefaultTableColumnModel) tblReturn.getColumnModel();
		TableColumn tc = tcm.getColumn(i);
		tc.setPreferredWidth(width);
	}

	void createTable() {
		dtm.setColumnCount(0);
		dtm.addColumn("");
		dtm.addColumn("No.");
		dtm.addColumn("ID");
		dtm.addColumn("Title");
		dtm.addColumn("Author");
		dtm.addColumn("Condition");
		tblReturn.setModel(dtm);

		setColumnWidth(0, 40);
		setColumnWidth(1, 40);
		setColumnWidth(2, 80);
		setColumnWidth(3, 280);
		setColumnWidth(4, 150);
		setColumnWidth(5, 50);

		String[] row = { "", "1", "Sf", "df" };
		dtm.addRow(row);
	}

	boolean isStuIdValid() {
		if (txtStuId.getText().length() != 8 || !txtStuId.getText().startsWith("STU-")) {
			JOptionPane.showMessageDialog(null, "Invalid Student ID\nRequired Format: STU-XXXX");
			txtStuId.requestFocus();
			txtStuId.selectAll();
			return false;
		}
		return true;
	}

	public static int getRowIndexByBookTitle(JTable table, int columnIndex, String title) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();

		for (int i = 0; i < rowCount; i++) {
			Object value = model.getValueAt(i, columnIndex);

			if (value.equals(title)) {
				return i;
			}

		}
		return -1;
	}
}

// Custom renderer for checkbox with transparent background
class TransparentCheckboxRenderer extends JCheckBox implements TableCellRenderer {
	public TransparentCheckboxRenderer() {
		setOpaque(false); // Make the checkbox transparent
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (value instanceof Boolean) {
			setSelected(value != null && (Boolean) value); // Set checkbox value

		}
		return this;
	}
}
