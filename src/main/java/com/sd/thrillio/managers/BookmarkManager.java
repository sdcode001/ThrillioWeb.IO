package com.sd.thrillio.managers;

import java.util.Collection;
import java.util.List;

import com.sd.thrillio.constants.KidFriendlyStatus;
import com.sd.thrillio.daos.BookmarkDao;

import com.sd.thrillio.entities.Book;
import com.sd.thrillio.entities.Bookmark;
import com.sd.thrillio.entities.Movie;
import com.sd.thrillio.entities.User;
import com.sd.thrillio.entities.UserBookmark;
import com.sd.thrillio.entities.WebLink;

public class BookmarkManager {
   private static BookmarkManager instance = new BookmarkManager();
   private static BookmarkDao dao = new BookmarkDao();
   
   private BookmarkManager(){}
   
   public static BookmarkManager getInstance() {
	   return instance;
   }
   
   public Movie createMovie(long id, String title, String profileUrl, int releaseYear, String cast[], String directors[], String genre, double imdbrating, String kid_friendly_status) {
	   Movie movie = new Movie();
	   movie.setId(id);
	   movie.setTitle(title);
	   movie.setProfileUrl(profileUrl);
	   movie.setReleaseYear(releaseYear);
	   movie.setCast(cast);
	   movie.setDirectors(directors);
	   movie.setGenre(genre);
	   movie.setImdbrating(imdbrating);
	   movie.setKidFriendlyStatus(kid_friendly_status);
	   return movie;
   }
   
   public Book createBook(long id, String title, String imageUrl, String profileUrl, int publicationYear, String publisher, String authors[], String genre, double amazonRating, String kid_friendly_status) {
	   Book book = new Book();
	   book.setId(id);
	   book.setTitle(title);
	   book.setImageUrl(imageUrl);
	   book.setProfileUrl(profileUrl);
	   book.setPublicationYear(publicationYear);
	   book.setPublisher(publisher);
	   book.setAuthors(authors);
	   book.setGenre(genre);
	   book.setKidFriendlyStatus(kid_friendly_status);
	   book.setAmazonRating(amazonRating);
	   return book;
   }
   
   public WebLink createWebLink(long id, String title, String profileUrl, String url, String host, String kid_friendly_status) {
	   WebLink weblink = new WebLink();
	   weblink.setId(id);
	   weblink.setTitle(title);
	   weblink.setProfileUrl(profileUrl);
	   weblink.setUrl(url);
	   weblink.setHost(host);
	   weblink.setKidFriendlyStatus(kid_friendly_status);
	   return weblink;
   }
   
   public List<List<Bookmark>> getBookmarks() {
		return dao.getBookmarks();
	}

	public void setBookmarkKidFriendlyStatus(User markedBy, Bookmark bookmark, String kidFriendlyStatus) {
		String kid_friendly_status = KidFriendlyStatus.valueOf(kidFriendlyStatus).getValue();
		bookmark.setKidFriendlyStatus(kid_friendly_status);
		bookmark.setKidFriendlyMarkedBy(markedBy);
		dao.updateKidFriendlyStatus(markedBy, bookmark, kidFriendlyStatus);
		System.out.println("Kid-Friendly-Status: "+kid_friendly_status+", Marked By: "+markedBy.getEmail()+", "+ bookmark.toString());	
	}

	public void share(Bookmark bookmark, User sharedBy) {
		bookmark.setSharedby(sharedBy);
		
		//share bookmark data with partner webSite.
		System.out.print("Data to be shared: ");
		//Do down typecast to access getItemData() method.
		if(bookmark instanceof Book) {
			System.out.print(((Book)bookmark).getItemData());
		}
		else if(bookmark instanceof WebLink) {
			System.out.print(((WebLink)bookmark).getItemData());
		}
		
		dao.saveShareByInfo(bookmark);
	}
	
	public void saveUserBookmark(User user, Bookmark bookmark) {
		UserBookmark userbookmark = new UserBookmark();
		userbookmark.setUser(user);
		userbookmark.setBookmark(bookmark);
		dao.saveUserBookmark(userbookmark);
		
	}

	public Collection<Bookmark> getBooks(boolean isBookmarked, long userId) {
		return dao.getBooks(isBookmarked, userId);
	}

	public Bookmark getBook(long bookId) {
		return dao.getBook(bookId);
	}

	public void deleteUserBookmark(User user, Bookmark bookmark) {
		UserBookmark userbookmark = new UserBookmark();
		userbookmark.setUser(user);
		userbookmark.setBookmark(bookmark);
		dao.deleteUserBookmark(userbookmark);
		
	}
   
   
}
