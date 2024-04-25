package utilities;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import controller.AuthorController;
import controller.BookController;
import controller.BorrowController;
import controller.DonationController;
import controller.DonatorController;
import controller.GenreController;
import controller.PublisherController;
import controller.ReturnController;
import model.AuthorModel;
import model.BookModel;
import model.BorrowModel;
import model.DonationModel;
import model.DonatorModel;
import model.GenreModel;
import model.PublisherModel;
import model.ReturnModel;
import view.AdminMain;

public class MyTblFunctions {

	public static void updateBooksTable() {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		BookController bctl = new BookController();
		List<BookModel> books = bctl.getAllBooks();

		String[] row = new String[9];

		int i = 1;
		for (BookModel book : books) {
			row[0] = i + "";
			row[1] = book.getId();
			row[2] = book.getTitle();
			row[3] = book.getAuthorName();
			row[4] = book.getPublisherName();
			row[5] = book.getGenreName();
			row[6] = book.getPuplishedYr() + "";
			row[7] = CurrencyFormatter.formatCurrency(book.getPrice());
			row[8] = book.getQty() + "";

			dtm.addRow(row);
			i++;
		}
	}

	public static void updateBooksTable(List<BookModel> books) {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		String[] row = new String[9];

		int i = 1;
		for (BookModel book : books) {
			row[0] = i + "";
			row[1] = book.getId();
			row[2] = book.getTitle();
			row[3] = book.getAuthorName();
			row[4] = book.getPublisherName();
			row[5] = book.getGenreName();
			row[6] = book.getPuplishedYr() + "";
			row[7] = CurrencyFormatter.formatCurrency(book.getPrice());
			row[8] = book.getQty() + "";

			dtm.addRow(row);
			i++;
		}
	}

	public static void updateAuthorsTable() {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		AuthorController actl = new AuthorController();
		List<AuthorModel> authors = actl.getAllAuthors();

		String[] row = new String[3];

		int i = 1;
		for (AuthorModel author : authors) {
			row[0] = i + "";
			row[1] = author.getId();
			row[2] = author.getName();
			dtm.addRow(row);
			i++;
		}
	}

	public static void updateAuthorsTable(List<AuthorModel> authors) {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		String[] row = new String[3];

		int i = 1;
		for (AuthorModel author : authors) {
			row[0] = i + "";
			row[1] = author.getId();
			row[2] = author.getName();
			dtm.addRow(row);
			i++;
		}
	}

	public static void updateGenresTable() {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		GenreController ctl = new GenreController();
		List<GenreModel> genres = ctl.getAllGenres();

		String[] row = new String[3];

		int i = 1;
		for (GenreModel genre : genres) {
			row[0] = i + "";
			row[1] = genre.getId();
			row[2] = genre.getName();
			dtm.addRow(row);
			i++;
		}
	}

	public static void updateGenresTable(List<GenreModel> genres) {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		String[] row = new String[3];

		int i = 1;
		for (GenreModel genre : genres) {
			row[0] = i + "";
			row[1] = genre.getId();
			row[2] = genre.getName();
			dtm.addRow(row);
			i++;
		}
	}

	public static void updatePublishersTable() {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		PublisherController ctl = new PublisherController();
		List<PublisherModel> publishers = ctl.getAllPublishers();

		String[] row = new String[3];

		int i = 1;
		for (PublisherModel publisher : publishers) {
			row[0] = i + "";
			row[1] = publisher.getId();
			row[2] = publisher.getName();
			dtm.addRow(row);
			i++;
		}
	}

	public static void updatePublishersTable(List<PublisherModel> publishers) {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		String[] row = new String[3];

		int i = 1;
		for (PublisherModel publisher : publishers) {
			row[0] = i + "";
			row[1] = publisher.getId();
			row[2] = publisher.getName();
			dtm.addRow(row);
			i++;
		}
	}

	public static void updateDonatorsTable() {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		DonatorController actl = new DonatorController();
		List<DonatorModel> donators = actl.getAllDonators();

		String[] row = new String[6];

		int i = 1;
		for (DonatorModel donator : donators) {
			row[0] = i + "";
			row[1] = donator.getDonatorId();
			row[2] = donator.getDonatorName();
			row[3] = donator.getEmail();
			row[4] = donator.getPhone();
			row[5] = donator.getAddress();

			dtm.addRow(row);
			i++;
		}
	}
	
	public static void updateDonatorsTable(List<DonatorModel> donators) {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		String[] row = new String[6];

		int i = 1;
		for (DonatorModel donator : donators) {
			row[0] = i + "";
			row[1] = donator.getDonatorId();
			row[2] = donator.getDonatorName();
			row[3] = donator.getEmail();
			row[4] = donator.getPhone();
			row[5] = donator.getAddress();

			dtm.addRow(row);
			i++;
		}
	}
	
	

	public static void updateDonationsTable() {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		DonationController ctl = new DonationController();
		List<DonationModel> donations = ctl.getAllDonation();

		String[] row = new String[6];

		int i = 1;
		for (DonationModel donation : donations) {
			row[0] = i + "";
			row[1] = donation.getDonationId();
			row[2] = donation.getDonatorId();
			row[3] = donation.getDonatorName();
			row[4] = donation.getDate();
			row[5] = donation.getTotalQty() + "";

			dtm.addRow(row);
			i++;
		}
	}
	
	public static void updateDonationsTable(List<DonationModel> donations) {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		String[] row = new String[6];

		int i = 1;
		for (DonationModel donation : donations) {
			row[0] = i + "";
			row[1] = donation.getDonationId();
			row[2] = donation.getDonatorId();
			row[3] = donation.getDonatorName();
			row[4] = donation.getDate();
			row[5] = donation.getTotalQty() + "";

			dtm.addRow(row);
			i++;
		}
	}

	public static void updateBorrowsTable() {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		BorrowController bctl = new BorrowController();
		List<BorrowModel> borrows = bctl.getAllBorrow();

		String[] row = new String[7];

		int i = 1;
		for (BorrowModel b : borrows) {
			row[0] = i + "";
			row[1] = b.getBorrowId();
			row[2] = b.getStuId();
			row[3] = b.getStuName();
			row[4] = b.getBorrowAt() + "";
			row[5] = b.getBorrowQty() + "";
			row[6] = b.getReturnedQty() + "";

			dtm.addRow(row);
			i++;
		}
	}

	public static void updateBorrowsTable(List<BorrowModel> borrows) {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		String[] row = new String[7];

		int i = 1;
		for (BorrowModel b : borrows) {
			row[0] = i + "";
			row[1] = b.getBorrowId();
			row[2] = b.getStuId();
			row[3] = b.getStuName();
			row[4] = b.getBorrowAt() + "";
			row[5] = b.getBorrowQty() + "";
			row[6] = b.getReturnedQty() + "";

			dtm.addRow(row);
			i++;
		}
	}

	public static void updateReturnsTable() {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		ReturnController ctl = new ReturnController();
		List<ReturnModel> returns = ctl.getAllReturn();

		String[] row = new String[8];

		int i = 1;
		for (ReturnModel r : returns) {
			row[0] = i + "";
			row[1] = r.getReturnId();
			row[2] = r.getBorrowId();
			row[3] = r.getStuId();
			row[4] = r.getStuName();
			row[5] = r.getReturnedAt() + "";
			row[6] = r.getReturnedQty() + "";
			row[7] = CurrencyFormatter.formatCurrency(r.getTotalFine());

			dtm.addRow(row);
			i++;
		}
	}

	public static void updateReturnsTable(List<ReturnModel> returns) {
		DefaultTableModel dtm = AdminMain.dtm;
		dtm.setRowCount(0);

		String[] row = new String[8];

		int i = 1;
		for (ReturnModel r : returns) {
			row[0] = i + "";
			row[1] = r.getReturnId();
			row[2] = r.getBorrowId();
			row[3] = r.getStuId();
			row[4] = r.getStuName();
			row[5] = r.getReturnedAt() + "";
			row[6] = r.getReturnedQty() + "";
			row[7] = CurrencyFormatter.formatCurrency(r.getTotalFine());

			dtm.addRow(row);
			i++;
		}
	}

}
