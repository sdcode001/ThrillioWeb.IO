package com.sd.thrillio.entities;

import com.sd.thrillio.constants.KidFriendlyStatus;


public abstract class Bookmark {
    private long id;
    private String title;
    private String profileUrl;
    private String kidFriendlyStatus = KidFriendlyStatus.UNKNOWN.getValue();
    private User kidFriendlyMarkedBy;
    private User sharedby;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getKidFriendlyStatus() {
		return kidFriendlyStatus;
	}

	public void setKidFriendlyStatus(String kidFriendlyStatus) {
		this.kidFriendlyStatus = kidFriendlyStatus;
	}
	
	public User getKidFriendlyMarkedBy() {
		return kidFriendlyMarkedBy;
	}

	public void setKidFriendlyMarkedBy(User kidFriendlyMarkedBy) {
		this.kidFriendlyMarkedBy = kidFriendlyMarkedBy;
	}

	public User getSharedby() {
		return sharedby;
	}

	public void setSharedby(User sharedby) {
		this.sharedby = sharedby;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", title=" + title + ", profileUrl=" + profileUrl + ", ";
	}
	
	public abstract boolean iskidFriendlyEligible();
    
}
