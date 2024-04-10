package utilities;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.AuthorModel;
import model.BookModel;
import model.GenreModel;

public class MyTblFunctions {
	public static void updateAuthors(List<AuthorModel> authors, DefaultTableModel dtm) {
		String[] row = new String[2];

		dtm.setRowCount(0);

		for(AuthorModel author: authors) {
			row[0] = author.getId();
			row[1] = author.getName();
			System.out.println(row[1]);
			dtm.addRow(row);
		}
	} 
	
	public static void updateGenres(List<GenreModel> genres, DefaultTableModel dtm) {
		String[] row = new String[2];

		dtm.setRowCount(0);

		for(GenreModel genre: genres) {
			row[0] = genre.getId();
			row[1] = genre.getName();
			dtm.addRow(row);
		}
	} 
	
	public static void updateBooks(List<BookModel> books, DefaultTableModel dtm) {
		String[] row = new String[8];

		dtm.setRowCount(0);

		for(BookModel book: books) {
			row[0] = book.getId();
			row[1] = book.getTitle();
			row[2] = book.getAuthorName();
			row[3] = book.getGenreName();
			row[4] = book.getPublisherName();
			row[5] = book.getPuplishedYr() + "";
			row[6] = book.getPrice() + "";
			row[7] = book.getQty() + "";
			dtm.addRow(row);
		}
	} 
	
	
}
