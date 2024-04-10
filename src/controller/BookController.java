package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import config.DBConfig;
import model.BookModel;

public class BookController {
	private static Connection con;

	static {
		DBConfig config = new DBConfig();
		try {
			con = config.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int insert(BookModel book) {
		String query = "INSERT INTO lib.book (book_id, title, author_id, genre_id, publisher_id, published_yr, price, qty) VALUES (?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

			ps.setString(1, book.getId());
			ps.setString(2, book.getTitle());
			ps.setString(3, book.getAuthorId());
			ps.setString(4, book.getGenreId());
			ps.setString(5, book.getPublisherId());
			ps.setInt(6, book.getPuplishedYr());
			ps.setInt(7, book.getPrice());
			ps.setInt(8, book.getQty());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int update(BookModel book) {
		String query = "UPDATE lib.book SET title=?, author_id=?, genre_id=?, publisher_id=?, published_yr=?, price=?, qty=? WHERE book_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthorId());
			ps.setString(3, book.getGenreId());
			ps.setString(4, book.getPublisherId());
			ps.setInt(5, book.getPuplishedYr());
			ps.setInt(6, book.getPrice());
			ps.setInt(7, book.getQty());
			ps.setString(8, book.getId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public int delete(BookModel book) {
		String query = "DELETE FROM lib.book WHERE book_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);

			ps.setString(1, book.getId());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public boolean hasDuplicate(BookModel book) {
		String query = "SELECT * FROM lib.book WHERE title=? AND author_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, book.getTitle());
			ps.setString(2, book.getAuthorId());

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<BookModel> getAllBooks() {
		String query = "SELECT book.*, author.author_name, genre.genre_name, publisher.publisher_name FROM lib.book\r\n"
				+ "INNER JOIN lib.author ON book.author_id = author.author_id\r\n"
				+ "INNER JOIN lib.genre ON book.genre_id = genre.genre_id\r\n"
				+ "INNER JOIN lib.publisher ON book.publisher_id = publisher.publisher_id\r\n"
				+ "ORDER BY book.title ASC;";
		List<BookModel> books = new ArrayList<>();

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				BookModel b = new BookModel();
				b.setId(rs.getString("book_id"));
				b.setTitle(rs.getString("title"));
				b.setAuthorId(rs.getString("author_id"));
				b.setAuthorName(rs.getString("author_name"));
				b.setGenreId(rs.getString("genre_id"));
				b.setGenreName(rs.getString("genre_name"));
				b.setPublisherId(rs.getString("publisher_id"));
				b.setPublisherName(rs.getString("publisher_name"));
				b.setPuplishedYr(rs.getInt("published_yr"));
				b.setPrice(rs.getInt("price"));
				b.setQty(rs.getInt("qty"));

				books.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return books;
	}
	
	public BookModel getOneBookById(BookModel book) {
		String query = "SELECT book.*, author.author_name, genre.genre_name, publisher.publisher_name FROM lib.book\r\n"
				+ "INNER JOIN lib.author ON book.author_id = author.author_id\r\n"
				+ "INNER JOIN lib.genre ON book.genre_id = genre.genre_id\r\n"
				+ "INNER JOIN lib.publisher ON book.publisher_id = publisher.publisher_id\r\n"
				+ "WHERE book_id=?";

		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(query);
			ps.setString(1, book.getId());
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				BookModel b = new BookModel();
				b.setId(rs.getString("book_id"));
				b.setTitle(rs.getString("title"));
				b.setAuthorId(rs.getString("author_id"));
				b.setAuthorName(rs.getString("author_name"));
				b.setGenreId(rs.getString("genre_id"));
				b.setGenreName(rs.getString("genre_name"));
				b.setPublisherId(rs.getString("publisher_id"));
				b.setPublisherName(rs.getString("publisher_name"));
				b.setPuplishedYr(rs.getInt("published_yr"));
				b.setPrice(rs.getInt("price"));
				b.setQty(rs.getInt("qty"));

				return b;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
