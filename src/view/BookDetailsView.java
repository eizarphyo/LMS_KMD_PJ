package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import components.MyBtn;
import controller.BookController;
import model.BookModel;
import utilities.CurrencyFormatter;
import utilities.LibColors;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BookDetailsView extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private static JLabel lblTitle;
	private static JLabel lblAuthor;
	private static JLabel lblGenre;
	private static JLabel lblPublisher;
	private static JLabel lblPubYr;
	private static JLabel lblPrice;
	private static JLabel lblQty;
	private static JLabel lblId;
	private static JLabel lblImg;
	private JLabel lblNewLabel;
	private JLabel lblbookid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			BookDetailsView dialog = new BookDetailsView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void showDialog(String id) {
		try {
			BookDetailsView dialog = new BookDetailsView();
			
			BookModel book = BookController.getOneBookById(id);
			lblId.setText(id);
			lblTitle.setText(book.getTitle());
			lblGenre.setText(book.getGenreName());
			lblAuthor.setText(book.getAuthorName());
			lblPrice.setText(CurrencyFormatter.formatCurrency(book.getPrice())+" MMK");
			lblQty.setText(book.getQty() + "");
			lblPubYr.setText(book.getPuplishedYr()+"");
			lblPublisher.setText(book.getPublisherName());
			
			if(book.getImage() != null) {
				// Convert the byte array to an Image object
                Image image = Toolkit.getDefaultToolkit().createImage(book.getImage()).getScaledInstance(120, 160, Image.SCALE_SMOOTH);

                // Scale the image if necessary
//                Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

                // Set the image to the JLabel
                lblImg.setText("");
				lblImg.setOpaque(false); 
				lblImg.setIcon(new ImageIcon(image));	
			} 
			
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public BookDetailsView() {
		setBounds(430, 180, 450, 242);
		setUndecorated(true);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		lblImg = new JLabel("");
		lblImg.setBounds(20, 18, 120, 160);
		contentPanel.add(lblImg);
		
		lblTitle = new JLabel("New label");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTitle.setBounds(161, 11, 265, 25);
		contentPanel.add(lblTitle);
		
		lblAuthor = new JLabel("Author");
		lblAuthor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAuthor.setBounds(161, 35, 150, 20);
		contentPanel.add(lblAuthor);
		
		lblGenre = new JLabel("Genre");
		lblGenre.setHorizontalAlignment(SwingConstants.TRAILING);
		lblGenre.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGenre.setBounds(326, 35, 100, 20);
		contentPanel.add(lblGenre);
		
		JLabel lblpub = new JLabel("Publisher:");
		lblpub.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblpub.setBounds(161, 85, 100, 20);
		contentPanel.add(lblpub);
		
		lblPublisher = new JLabel("New label");
		lblPublisher.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPublisher.setBounds(273, 85, 150, 20);
		contentPanel.add(lblPublisher);
		
		JLabel lblyr = new JLabel("Published Year:");
		lblyr.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblyr.setBounds(161, 107, 100, 20);
		contentPanel.add(lblyr);
		
		lblPubYr = new JLabel("New label");
		lblPubYr.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPubYr.setBounds(273, 107, 100, 20);
		contentPanel.add(lblPubYr);
		
		JLabel lblprice = new JLabel("Price:");
		lblprice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblprice.setBounds(161, 130, 100, 20);
		contentPanel.add(lblprice);
		
		lblPrice = new JLabel("New label");
		lblPrice.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblPrice.setBounds(273, 130, 100, 20);
		contentPanel.add(lblPrice);
		
		JLabel lblavaqty = new JLabel("Available Qty:");
		lblavaqty.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblavaqty.setBounds(161, 160, 100, 25);
		contentPanel.add(lblavaqty);
		
		lblQty = new JLabel("New label");
		lblQty.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblQty.setBounds(273, 160, 100, 25);
		contentPanel.add(lblQty);
		
		lblId = new JLabel("New label");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblId.setBounds(273, 66, 80, 20);
		contentPanel.add(lblId);
		
		contentPanel.setBackground(LibColors.PRIMARY_SELECT_BG);
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		MyBtn.changeMyBtnStyle(btnClose);
		btnClose.setBounds(354, 196, 75, 27);
		contentPanel.add(btnClose);
		
		lblNewLabel = new JLabel("------------------------------------------------------------------------------------------");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 8));
		lblNewLabel.setBounds(161, 146, 265, 14);
		contentPanel.add(lblNewLabel);
		
		lblbookid = new JLabel("Book ID:");
		lblbookid.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblbookid.setBounds(161, 66, 100, 14);
		contentPanel.add(lblbookid);
	}
}
