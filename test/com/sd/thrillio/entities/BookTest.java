package com.sd.thrillio.entities;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import com.sd.thrillio.constants.BookGenre;
import com.sd.thrillio.constants.KidFriendlyStatus;
import com.sd.thrillio.constants.MovieGenre;
import com.sd.thrillio.managers.BookmarkManager;



class BookTest {

	@Test
	void testIskidFriendlyEligible() {
		
		//Testcase 1: genre is philosophy-false
		Book book = BookmarkManager.getInstance().createBook(4000, "Walden", "http://www.photo.com/photo?id=12", "", 1854, "Wilder Publications",
				new String[] { "Henry David Thoreau" }, BookGenre.PHILOSOPHY.getValue(), 4.3, KidFriendlyStatus.UNKNOWN.getValue());
		boolean isKidFriendly = book.iskidFriendlyEligible();
		assertFalse("Book genre is philosophy: iskidFriendlyEligible() must return false",isKidFriendly);
		
		//Testcase 1: genre is self_help-false
		book = BookmarkManager.getInstance().createBook(4000, "Walden", "http://www.photo.com/photo?id=12", "", 1854, "Wilder Publications",
				new String[] { "Henry David Thoreau" }, BookGenre.SELF_HELP.getValue(), 4.3, KidFriendlyStatus.UNKNOWN.getValue());
		isKidFriendly = book.iskidFriendlyEligible();
		assertFalse("Book genre is self_help: iskidFriendlyEligible() must return false",isKidFriendly);
	}

}
