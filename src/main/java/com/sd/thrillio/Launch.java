package com.sd.thrillio;

import java.util.List;

import com.sd.thrillio.backgroundjobs.WebpageDownloaderTask;
import com.sd.thrillio.entities.Bookmark;
import com.sd.thrillio.entities.User;
import com.sd.thrillio.managers.BookmarkManager;
import com.sd.thrillio.managers.UserManager;


public class Launch {
	private static List<User> users;
	private static List<List<Bookmark>> bookmarks;
	
	private static void loadData() {
		System.out.println("Loading data...");
		DataStore.loadData();
		//users = UserManager.getInstance().getUsers();
		bookmarks = BookmarkManager.getInstance().getBookmarks();
//		System.out.println("Printing Users...");
//		printUsers();
//		System.out.println("Printing Bookmarks...");
//		printBookmarks();
	}
	
	private static void printBookmarks() {
		for(List<Bookmark> bookmarkType:bookmarks) {
			for(Bookmark bookmark:bookmarkType) {
				System.out.println(bookmark.toString());
			}
		}
	}
	

	private static void printUsers() {
		for(User user:users) {
			System.out.println(user.toString());
		}
	}

	private static void start() {
		System.out.println("Starting application....");
		for(User user:users) {
			View.browse(user, bookmarks);	
			System.out.println('\n');
		}
	}
	
	private static void runDownloaderJob() {
		WebpageDownloaderTask task = new WebpageDownloaderTask(true);
		
		(new Thread(task)).start();
	}
	
	
	public static void main(String[] args) {	
		loadData();
		start();
		////Background jobs
		//runDownloaderJob();
	}
	

}
