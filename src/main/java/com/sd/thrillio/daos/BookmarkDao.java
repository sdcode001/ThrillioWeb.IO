package com.sd.thrillio.daos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.sd.thrillio.DataStore;
import com.sd.thrillio.constants.BookGenre;
import com.sd.thrillio.constants.KidFriendlyStatus;
import com.sd.thrillio.entities.Book;
import com.sd.thrillio.entities.Bookmark;
import com.sd.thrillio.entities.Movie;
import com.sd.thrillio.entities.User;
import com.sd.thrillio.entities.UserBookmark;
import com.sd.thrillio.entities.WebLink;
import com.sd.thrillio.managers.BookmarkManager;


public class BookmarkDao {
    
	public List<List<Bookmark>> getBookmarks() {
		return DataStore.getBookmarks();
	}
	
	
	public void saveUserBookmark(UserBookmark userbookmark) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		//try-with-resource => conn & stmt will be colsed
		//Connection string => <protocol>:<sub-protocol>:<data-source-details>
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root", "DeY@733129");
			Statement stmt = conn.createStatement();){
			if(userbookmark.getBookmark() instanceof Book) {
				saveUserBook(userbookmark, stmt);
			}
			else if(userbookmark.getBookmark() instanceof Movie) {
				saveUserMovie(userbookmark, stmt);
			}
			else {
				saveUserWeblink(userbookmark, stmt);
			}
		}catch(SQLException e) {
			//e.printStackTrace();
		}
	}
	
	private void saveUserWeblink(UserBookmark userbookmark, Statement stmt) throws SQLException {
		String query = "insert into User_WebLink (user_id, weblink_id) values ("
                +userbookmark.getUser().getId()+", "+userbookmark.getBookmark().getId()+")";
	
	    stmt.executeUpdate(query);
	}

	private void saveUserMovie(UserBookmark userbookmark, Statement stmt) throws SQLException {
		String query = "insert into User_Movie (user_id, movie_id) values ("
                +userbookmark.getUser().getId()+", "+userbookmark.getBookmark().getId()+")";
	
	    stmt.executeUpdate(query);
	}

	private void saveUserBook(UserBookmark userbookmark, Statement stmt) throws SQLException {
		String query = "insert into User_Book (user_id, book_id) values ("
	                   +userbookmark.getUser().getId()+", "+userbookmark.getBookmark().getId()+")";
		
		stmt.executeUpdate(query);	
	}

	//In real application we would have SQL or Hibernet queries.
	public List<WebLink> getAllWebLinks() {
		List<WebLink> result=new ArrayList<>();
		List<List<Bookmark>> bookmarks = DataStore.getBookmarks();
		for(Bookmark weblink: bookmarks.get(0)) {
			result.add((WebLink)weblink);
		}
		return result;
	}
	
	public List<WebLink> getWebLinks(WebLink.DownloadStatus downloadStatus) {
		List<WebLink> result=new ArrayList<>();
		List<WebLink> allWeblinks = getAllWebLinks();
		for(WebLink weblink:allWeblinks) {
			if(weblink.getDownloadStatus().equals(downloadStatus)) {
				result.add(weblink);
			}
		}
		return result;
	}

	public void updateKidFriendlyStatus(User markedBy, Bookmark bookmark, String kidFriendlyStatus) {
		int kidFriendlyStatus_id = KidFriendlyStatus.valueOf(kidFriendlyStatus).ordinal();
		
		String table_to_upadte = "Book";
		if(bookmark instanceof Movie) {
		   table_to_upadte = "Movie";
		}
		else if(bookmark instanceof WebLink) {
			   table_to_upadte = "WebLink";
		}
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root", "DeY@733129");
				Statement stmt = conn.createStatement();){
			    String query = "update "+ table_to_upadte + " set kid_friendly_status = "+ kidFriendlyStatus_id
			    		       + ", kid_friendly_status_markedby = "+markedBy.getId()
			    		       + " where id = "+bookmark.getId();
			    
			    stmt.executeUpdate(query);
			    
		}catch(SQLException e) {
			//e.printStackTrace();
		}
		
	}

	public void saveShareByInfo(Bookmark bookmark) {
		long user_id = bookmark.getSharedby().getId();
		
		String table_to_upadte = "Book";
		if(bookmark instanceof WebLink) {
		   table_to_upadte = "WebLink";
		}
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root", "DeY@733129");
				Statement stmt = conn.createStatement();){
			    String query = "update "+ table_to_upadte + " set bookmark_shareby = "+ user_id
			    		       + " where id = "+bookmark.getId();
			    
			    stmt.executeUpdate(query);
			    
		}catch(SQLException e) {
			//e.printStackTrace();
		}
	}

	public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		//importing the Driver class from jdbc Api.
		Collection<Bookmark> result = new ArrayList<>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root", "DeY@733129");
				Statement stmt = conn.createStatement();){
			String query = "";
			if(!isBookmarked) {
				query = "select b.id as id, title, image_url, p.name as publisher, group_concat(a.name separator ',') as authors, publication_year, book_genre_id, amazon_rating, kid_friendly_status, created_date" 
		 			       +" from Book b, Author a, Publisher p, Book_Author ba"
		 			       +" where b.publisher_id=p.id and ba.book_id=b.id and ba.author_id=a.id"
		 			       +" and b.id not in (select ub.book_id from User u, User_Book ub where u.id = " + userId
		 			       +" and u.id = ub.user_id) group by b.id";
			}
			else {
				query = "select b.id as id, title, image_url, p.name as publisher, group_concat(a.name separator ',') as authors, publication_year, book_genre_id, amazon_rating, kid_friendly_status, created_date" 
		 			       +" from Book b, Author a, Publisher p, Book_Author ba"
		 			       +" where b.publisher_id=p.id and ba.book_id=b.id and ba.author_id=a.id"
		 			       +" and b.id in (select ub.book_id from User u, User_Book ub where u.id = " + userId
		 			       +" and u.id = ub.user_id) group by b.id";
			}
		
			ResultSet rs = stmt.executeQuery(query);

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
		 		
		 		result.add(BookmarkManager.getInstance().createBook(id, title, imageUrl, "",publication_year, publisher, authors, genre, amazon_rating, kid_friendly_status));
		 	}
			   			    
		}catch(SQLException e) {
			//e.printStackTrace();
		}
		return result;
	}

	
	
	public Bookmark getBook(long bookId) {
		Book book = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root", "DeY@733129");
				Statement stmt = conn.createStatement();){
			
			String query = "select b.id as id, title, image_url, p.name as publisher, group_concat(a.name separator ',') as authors, publication_year, book_genre_id, amazon_rating, kid_friendly_status, created_date" 
 			       +" from Book b, Author a, Publisher p, Book_Author ba"
 			       +" where b.publisher_id=p.id and ba.book_id=b.id and ba.author_id=a.id and b.id="+bookId
 			       +" group by b.id";
		
			ResultSet rs = stmt.executeQuery(query);
			
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
		 		
		 		book = BookmarkManager.getInstance().createBook(id, title, imageUrl, "",publication_year, publisher, authors, genre, amazon_rating, kid_friendly_status);
		 	}
		}catch(SQLException e) {
			//e.printStackTrace();
		}
		
		return book;
	}

	public void deleteUserBookmark(UserBookmark userbookmark) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		//try-with-resource => conn & stmt will be colsed
		//Connection string => <protocol>:<sub-protocol>:<data-source-details>
		try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db_thrillio?allowPublicKeyRetrieval=true&useSSL=false", "root", "DeY@733129");
			Statement stmt = conn.createStatement();){
			if(userbookmark.getBookmark() instanceof Book) {
				deleteUserBook(userbookmark, stmt);
			}
			else if(userbookmark.getBookmark() instanceof Movie) {
				deleteUserMovie(userbookmark, stmt);
			}
			else {
				deleteUserWeblink(userbookmark, stmt);
			}
		}catch(SQLException e) {
			//e.printStackTrace();
		}
		
	}


	private void deleteUserWeblink(UserBookmark userbookmark, Statement stmt) throws SQLException {
		String query = "delete from User_WebLink where weblink_id="+userbookmark.getBookmark().getId()+" and user_id="+userbookmark.getUser().getId();

	    stmt.executeUpdate(query);
	}


	private void deleteUserMovie(UserBookmark userbookmark, Statement stmt) throws SQLException {
		String query = "delete from User_Movie where movie_id="+userbookmark.getBookmark().getId()+" and user_id="+userbookmark.getUser().getId();
		
	    stmt.executeUpdate(query);
	}


	private void deleteUserBook(UserBookmark userbookmark, Statement stmt) throws SQLException {
		String query = "delete from User_Book where book_id="+userbookmark.getBookmark().getId()+" and user_id="+userbookmark.getUser().getId();

	    stmt.executeUpdate(query);
		
	}
	
	
}
