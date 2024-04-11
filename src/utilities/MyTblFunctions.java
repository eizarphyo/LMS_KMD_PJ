package utilities;

import java.util.List;

import javax.swing.table.DefaultTableModel;

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
import view.Test;

public class MyTblFunctions {
	public static void updateBooksTable() {
		DefaultTableModel dtm = Test.dtm;
		dtm.setRowCount(0);
		
		BookController bctl = new BookController();
		List<BookModel> books = bctl.getAllBooks();
		
		String[] row = new String[8];

		for(BookModel book: books) {
			row[0] = book.getId();
			row[1] = book.getTitle();
			row[2] = book.getAuthorName();
			row[3] = book.getPublisherName();
			row[4] = book.getGenreName();
			row[5] = book.getPuplishedYr() + "";
			row[6] = book.getPrice() + "";
			row[7] = book.getQty() + "";
			dtm.addRow(row);
		}
	} 
	
	public static void updateAuthorsTable() {
		DefaultTableModel dtm = Test.dtm;
		dtm.setRowCount(0);
		
		AuthorController actl = new AuthorController();
		List<AuthorModel> authors = actl.getAllAuthors();
		
		String[] row = new String[2];

		for(AuthorModel author: authors) {
			row[0] = author.getId();
			row[1] = author.getName();
			dtm.addRow(row);
		}
	} 
	
	public static void updateGenresTable() {
		DefaultTableModel dtm = Test.dtm;
		dtm.setRowCount(0);
		
		GenreController ctl = new GenreController();
		List<GenreModel> genres = ctl.getAllGenres();
		
		String[] row = new String[2];


		for(GenreModel genre: genres) {
			row[0] = genre.getId();
			row[1] = genre.getName();
			dtm.addRow(row);
		}
	} 
	
	public static void updatePublishersTable() {
		DefaultTableModel dtm = Test.dtm;
		dtm.setRowCount(0);
		
		PublisherController ctl = new PublisherController();
		List<PublisherModel> publishers = ctl.getAllPublishers();
		
		String[] row = new String[2];

		for(PublisherModel publisher: publishers) {
			row[0] = publisher.getId();
			row[1] = publisher.getName();
			dtm.addRow(row);
		}
	} 
	
	public static void updateDonatorsTable() {
		DefaultTableModel dtm = Test.dtm;
		dtm.setRowCount(0);
		
		DonatorController actl = new DonatorController();
		List<DonatorModel> donators = actl.getAllDonators();
		
		String[] row = new String[5];

		for(DonatorModel donator: donators) {
			row[0] = donator.getDonatorId();
			row[1] = donator.getDonatorName();
			row[2] = donator.getEmail();
			row[3] = donator.getPhone();
			row[4] = donator.getAddress();
			
			dtm.addRow(row);
		}
	} 
	
	
}
