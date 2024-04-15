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
import model.BookModel;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel lbl;
	private JTextField txtSearch;
	private List<BookModel> books = new ArrayList<>();

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

	/**
	 * Create the frame.
	 */
	public Main() {
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

		lbl = new JLabel("Fantastic Library");
		lbl.setBounds(0, 0, 1300, 50);
		contentPanel.add(lbl);
		lbl.setIcon(icon);
		lbl.setIconTextGap(25); // Adjust the gap value between img and txt
		lbl.setOpaque(true);
		lbl.setBackground(Color.ORANGE);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.setLayout(null);

		// Search field
		JPanel searchPanel = new JPanel();
		searchPanel.setBounds(244, 61, 215, 30);
		contentPanel.add(searchPanel);

		// Create the icon
		java.net.URL iconUrl = getClass().getResource("../images/search.png");
		Image image = new ImageIcon(iconUrl).getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH); // Replace
																										// "icon.png"
																										// with the path
																										// to your icon
																										// image

		ImageIcon ic = new ImageIcon(image);
		JLabel iconLabel = new JLabel(ic);

		// Create the text field
		JTextField txtSearch = new JTextField(15);
		txtSearch.setBounds(800, 69, 150, 25);

		// Add the icon and text field to the panel
		searchPanel.add(iconLabel, BorderLayout.WEST);
		searchPanel.add(txtSearch, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.setBounds(0, 100, 1280, 580);
		panel.setLayout(null);
		contentPanel.add(panel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 1280, 580);
		panel.add(scrollPane);

		JPanel panelBooks = new JPanel();
		scrollPane.setViewportView(panelBooks);
		panelBooks.setLayout(new GridLayout(books.size() / 8, 8, 0, 20));

		JLabel title = new JLabel("Available Books");
		title.setFont(new Font("Tahoma", Font.BOLD, 15));
		title.setBounds(0, 60, 500, 30);
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

		
		
		for(BookModel book: books) {
			ImageIcon bookCover = new ImageIcon(convertBlobToImage(book.getImage()).getScaledInstance(130, 180, Image.SCALE_SMOOTH));
			
			ImageIcon img2 = new ImageIcon(
					new ImageIcon("C:\\Users\\Lenovo\\OneDrive\\Pictures\\Saved Pictures\\select.png").getImage()
							.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
			
			JCheckBox checkBox = new JCheckBox();
			checkBox.setOpaque(false); // Set opaque to false
			checkBox.setBorderPainted(false); // Remove border
			
			MyImageLabel label = new MyImageLabel(book.getTitle(), bookCover, img2, checkBox);
			
			panelBooks.add(label);
		}
		
		//---------
//		JLabel[] labels = new JLabel[20];
//		MyImageLabel[] labels = new MyImageLabel[20];
//
//		for (int i = 0; i < labels.length; i++) {
//
////			java.net.URL imgURL = getClass().getResource("../images/daring_greatly.png");
////			ImageIcon img = new ImageIcon(imgURL);
//
//			ImageIcon img1 = new ImageIcon(
//					new ImageIcon("C:\\Users\\Lenovo\\OneDrive\\Pictures\\Saved Pictures\\daring_greatly.png")
//							.getImage().getScaledInstance(130, 180, Image.SCALE_SMOOTH));
//
//			ImageIcon img2 = new ImageIcon(
//					new ImageIcon("C:\\Users\\Lenovo\\OneDrive\\Pictures\\Saved Pictures\\select.png").getImage()
//							.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
//
//			JCheckBox checkBox = new JCheckBox();
//			checkBox.setOpaque(false); // Set opaque to false
//			checkBox.setBorderPainted(false); // Remove border
//
//			labels[i] = new MyImageLabel("Daring Greatly", img1, img2, checkBox);
//			final int I = i;
//			labels[i].addMouseListener(new MouseAdapter() {
//				@Override
//				public void mouseClicked(MouseEvent e) {
//					System.out.println("Clicked " + I);
//					if (labels[I].getBorder() != null) {
//						labels[I].setBorder(null);
//						checkBox.setSelected(false);
//
//						return;
//					}
//					;
//					labels[I].setBorder(BorderFactory.createLineBorder(Color.orange));
//					checkBox.setSelected(true);
//				}
//			});
//			panelBooks.add(labels[i]);
//
//		}
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
}
