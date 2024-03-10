package com.sd.thrillio;

import java.util.List;

import com.sd.thrillio.constants.KidFriendlyStatus;
import com.sd.thrillio.constants.UserType;
import com.sd.thrillio.controllers.BookmarkController;
import com.sd.thrillio.entities.Bookmark;
import com.sd.thrillio.entities.User;
import com.sd.thrillio.partnersite.Shareable;



public class View {
	
	public static void browse(User user, List<List<Bookmark>> bookmarks) {
		int bookmarkCount=0;
		System.out.println(user.getEmail()+" started browsing....");
		for(List<Bookmark> bookmarkList: bookmarks) {
			for(Bookmark bookmark:bookmarkList) {
				if(getBookmarkDecision()) {
					System.out.println("New item bookmarked..."+bookmark.toString());
					//BookmarkController.getInstance().saveUserBookmark(user, bookmark);
					bookmarkCount++;
				}
				
				//kid friendly bookmark
				if(user.getUserType().equals(UserType.EDITOR.getValue()) || user.getUserType().equals(UserType.CHIEF_EDITOR.getValue())) {					
					if(bookmark.iskidFriendlyEligible() && bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN.getValue())) {
						String getDecision = getKidFriendlyStatusDecision(bookmark);
						if(!getDecision.equals(KidFriendlyStatus.UNKNOWN.toString())) {
							//BookmarkController.getInstance().setBookmarkKidFriendlyStatus(user, bookmark, getDecision);
						}	
					}
				}
				
				//Implementing Book and WebLink sharing feature with partner webSites.
				if(user.getUserType().equals(UserType.EDITOR.getValue()) || user.getUserType().equals(UserType.CHIEF_EDITOR.getValue())) {
					if(bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED.getValue()) && bookmark instanceof Shareable) {
						if(getShareableDecision()) {
							//BookmarkController.getInstance().shareBookmark(bookmark, user);
						}
					}
				}
				
				
			}
		}
	}
	
	
	//TODO: below methods simulate user input. After IO, we took input via console.
	private static boolean getBookmarkDecision() {
		return (Math.random()<0.5)?false:true;
	}
	
	private static boolean getShareableDecision() {
		return (Math.random()<0.5)?false:true;
	}
	
	private static String getKidFriendlyStatusDecision(Bookmark bookmark) {
		double a=Math.random();
		return (a<0.4)? KidFriendlyStatus.REJECTED.toString()
				: (a>=0.4 && a<0.8)? KidFriendlyStatus.APPROVED.toString()
						: KidFriendlyStatus.UNKNOWN.toString();
	}
	
	

}
