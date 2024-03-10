package com.sd.thrillio.entities;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import com.sd.thrillio.constants.KidFriendlyStatus;
import com.sd.thrillio.managers.BookmarkManager;


class WebLinkTest {

	@Test
	void testIskidFriendlyEligible() {
		
		//TestCase-1: porn in url--false
		WebLink weblink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "",
				"http://www.javaworld.com/article/2072759/core-java/taming-porn--part-2.html",
				"http://www.javaworld.com", KidFriendlyStatus.UNKNOWN.getValue());
		boolean isKidFriendly = weblink.iskidFriendlyEligible();
		assertFalse("For porn in url : iskidFriendlyEligible() must return false",isKidFriendly);
		
		//TestCase-2: porn in title--false
		weblink = BookmarkManager.getInstance().createWebLink(2000, "Taming porn 2", "",
				"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",
				"http://www.javaworld.com", KidFriendlyStatus.UNKNOWN.getValue());
		isKidFriendly = weblink.iskidFriendlyEligible();
		assertFalse("For porn in title : iskidFriendlyEligible() must return false",isKidFriendly);
		
		//TestCase-3: adult in host--false
		weblink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "",
				"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",
				"http://www.adult.com", KidFriendlyStatus.UNKNOWN.getValue());
		isKidFriendly = weblink.iskidFriendlyEligible();
		assertFalse("For adult in host : iskidFriendlyEligible() must return false",isKidFriendly);
		
		//TestCase-4: adult in url but not in host--true
		weblink = BookmarkManager.getInstance().createWebLink(2000, "Taming Tiger, Part 2", "",
				"http://www.javaworld.com/article/2072759/core-java/taming-adult--part-2.html",
				"http://www.javaworld.com", KidFriendlyStatus.UNKNOWN.getValue());
		isKidFriendly = weblink.iskidFriendlyEligible();
		assertTrue("For porn in url : iskidFriendlyEligible() must return false",isKidFriendly);
		
		//TestCase-5: adult in title only--true
		weblink = BookmarkManager.getInstance().createWebLink(2000, "Taming Adult, Part 2", "",
				"http://www.javaworld.com/article/2072759/core-java/taming-tiger--part-2.html",
				"http://www.javaworld.com", KidFriendlyStatus.UNKNOWN.getValue());
		isKidFriendly = weblink.iskidFriendlyEligible();
		assertTrue("For porn in url : iskidFriendlyEligible() must return false",isKidFriendly);
	}

}
