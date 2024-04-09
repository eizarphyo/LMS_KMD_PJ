package view;

import java.awt.EventQueue;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminMain extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminMain frame = new AdminMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AdminMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnAddNew = new JMenu("Add New");
		menuBar.add(mnAddNew);

		JMenuItem mntmNewBook = new JMenuItem("Book");
		mntmNewBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		mnAddNew.add(mntmNewBook);

		JMenuItem mntmNewAuthor = new JMenuItem("Author");
		mntmNewAuthor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AuthorDiglogView.showDialog();
			}
		});
		mnAddNew.add(mntmNewAuthor);

		JMenuItem mntmNewGenre = new JMenuItem("Genre");
		mntmNewGenre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GenreDialogView.showDialog();
			}
		});
		mnAddNew.add(mntmNewGenre);

		JMenuItem mntmNewPublisher = new JMenuItem("Publisher");
		mntmNewPublisher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PublisherDialogView.showDialog();
			}
		});
		mnAddNew.add(mntmNewPublisher);

		JMenu mnViewList = new JMenu("View List");
		menuBar.add(mnViewList);

		JMenuItem mntmViewBook = new JMenuItem("Book");
		mnViewList.add(mntmViewBook);

		JMenuItem mntmViewAuthor = new JMenuItem("Author");
		mnViewList.add(mntmViewAuthor);

		JMenuItem mntmViewGenre = new JMenuItem("Genre");
		mnViewList.add(mntmViewGenre);

		JMenuItem mntmViewPublisher = new JMenuItem("Publisher");
		mnViewList.add(mntmViewPublisher);
		setContentPane(contentPane);
		contentPane.setLayout(null);
	}
}
