package com.sd.thrillio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import com.sd.thrillio.constants.BookGenre;
import com.sd.thrillio.constants.Gender;
import com.sd.thrillio.constants.KidFriendlyStatus;
import com.sd.thrillio.constants.MovieGenre;
import com.sd.thrillio.constants.UserType;
import com.sd.thrillio.entities.Bookmark;
import com.sd.thrillio.entities.User;
import com.sd.thrillio.entities.UserBookmark;
import com.sd.thrillio.managers.BookmarkManager;
import com.sd.thrillio.managers.UserManager;
import com.sd.thrillio.util.IOUtil;



public class DataStore {
	private static List<User> users = new ArrayList<>();
	private static List<List<Bookmark>> bookmarks = new ArrayList<>();
	private static List<UserBookmark> userBookmarks = new ArrayList<>();

	public static void loadData() {
		
        //importing the Driver class from jdbc Api.
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		
		//try-with-resource => conn & stmt will be colsed
		//Connection string => <protocol>:<sub-protocol>:<data-source-details>
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_thrillio?useSSL=false", "root", "DeY@733129");
			Statement stmt = conn.createStatement();){
			//loadUsers(stmt);
			loadWebLinks(stmt);
			loadMovies(stmt);
			loadBooks(stmt);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	public static List<User> getUsers() {
		return users;
	}

	public static List<List<Bookmark>> getBookmarks() {
		return bookmarks;
	}

	
	
	private static void loadWebLinks(Statement stmt) throws SQLException {
        String query = "select * from WebLink";
		
		ResultSet rs = stmt.executeQuery(query);
		
    	List<Bookmark> webLinkList = new ArrayList<>();
    	while (rs.next()) {
    		long id = rs.getLong("id");
    		String title = rs.getString("title");
    		String url = rs.getString("url");
    		String host = rs.getString("host");
    		int kid_friendly_status_id = rs.getInt("kid_friendly_status");
    		String kid_friendly_status = KidFriendlyStatus.values()[kid_friendly_status_id].getValue();
    		Timestamp created_date = rs.getTimestamp("created_date");
    		
    		System.out.println("id: " + id + ", title: " + title + ", url: " + url 
    				+ ", host: " + host + ", kid_friendly_status: " + kid_friendly_status 
    				+ ", created_date:" + created_date);
    		
    		webLinkList.add(BookmarkManager.getInstance().createWebLink(id, title, "", url, host, kid_friendly_status));
    	}
    	bookmarks.add(webLinkList);
	}
	
	private static void loadMovies(Statement stmt) throws SQLException {
		String query = "select m.id as id, title, release_year, group_concat(distinct d.name separator ',') as directors, group_concat(distinct a.name separator ',') as casts, movie_genre_id, imdb_rating, kid_friendly_status, created_date"
	                   +" from Movie m, Actor a, Director d, Movie_Actor ma, Movie_Director md"
				       +" where ma.movie_id=m.id and ma.actor_id=a.id and md.movie_id=m.id and md.director_id=d.id group by m.id";
		
		ResultSet rs = stmt.executeQuery(query);
		
    	List<Bookmark> movieList = new ArrayList<>();
    	while (rs.next()) {
    		long id = rs.getLong("id");
			String title = rs.getString("title");
			int releaseYear = rs.getInt("release_year");
			String[] cast = rs.getString("casts").split(",");
			String[] directors = rs.getString("directors").split(",");			
			int genre_id = rs.getInt("movie_genre_id");
			String genre = MovieGenre.values()[genre_id].getValue();
			double imdbRating = rs.getDouble("imdb_rating");
			int kid_friendly_status_id = rs.getInt("kid_friendly_status");
    		String kid_friendly_status = KidFriendlyStatus.values()[kid_friendly_status_id].getValue();
			Timestamp created_date = rs.getTimestamp("created_date");
			
			System.out.println("id: " + id + ", title: " + title + ", publication year: " + releaseYear 
    				+ ", directors: " + String.join(", ", directors) + ", casts: " + String.join(", ", cast) + ", genre: " 
    				+ genre + ", imdb_rating: " + imdbRating + ", created_date:" + created_date);
			
    		movieList.add(BookmarkManager.getInstance().createMovie(id, title, "", releaseYear, cast, directors, genre, imdbRating, kid_friendly_status));
    	}
    	bookmarks.add(movieList);
	}
	
	private static void loadBooks(Statement stmt) throws SQLException {
		String query = "select b.id as id, title, p.name as publisher, group_concat(a.name separator ',') as authors, publication_year, book_genre_id, amazon_rating, kid_friendly_status, created_date" 
    			       +" from Book b, Author a, Publisher p, Book_Author ba"
    			       +" where b.publisher_id=p.id and ba.book_id=b.id and ba.author_id=a.id group by b.id";
		
		ResultSet rs = stmt.executeQuery(query);
		
    	List<Bookmark> bookList = new ArrayList<>();
    	while(rs.next()) {
    		Long id = rs.getLong("id");
    		String title = rs.getString("title");
    		String imageUrl = rs.getString("image_url");
    		String publisher = rs.getString("publisher");
    		String[] authors = rs.getString("authors").split(",");
    		int publication_year = rs.getInt("publication_year");
    		int genre_id = rs.getInt("book_genre_id");
    		String genre = BookGenre.values()[genre_id].getValue();
    		double amazon_rating = rs.getDouble("amazon_rating");
    		int kid_friendly_status_id = rs.getInt("kid_friendly_status");
    		String kid_friendly_status = KidFriendlyStatus.values()[kid_friendly_status_id].getValue();
    		Timestamp created_date = rs.getTimestamp("created_date");
    		
    		System.out.println("id: " + id + ", title: " + title + ", publication year: " + publication_year 
    				+ ", publisher: " + publisher + ", authors: " + String.join(", ", authors) + ", genre: " 
    				+ genre + ", amazonRating: " + amazon_rating + ", created_date:" + created_date);
    		
    		bookList.add(BookmarkManager.getInstance().createBook(id, title, imageUrl, "",publication_year, publisher, authors, genre, amazon_rating, kid_friendly_status));
    	}
    	bookmarks.add(bookList);
    }
	
	public static void addUserBookmark(UserBookmark userbookmark) {
		userBookmarks.add(userbookmark);
	}

}
