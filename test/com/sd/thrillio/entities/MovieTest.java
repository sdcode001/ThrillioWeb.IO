package com.sd.thrillio.entities;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import com.sd.thrillio.constants.KidFriendlyStatus;
import com.sd.thrillio.constants.MovieGenre;
import com.sd.thrillio.managers.BookmarkManager;



class MovieTest {

	@Test
	void testIskidFriendlyEligible() {
		
		//Testcase 1: genre is horror-false
		Movie movie = BookmarkManager.getInstance().createMovie(3000, "Citizen Kane", "", 1941,
				new String[] { "Orson Welles", "Joseph Cotten" }, new String[] { "Orson Welles" }, MovieGenre.HORROR.getValue(),
				8.5, KidFriendlyStatus.UNKNOWN.getValue());
		boolean isKidFriendly = movie.iskidFriendlyEligible();
		assertFalse("Movie genre is Horror: iskidFriendlyEligible() must return false",isKidFriendly);
		
		//Testcase 2: genre is thrillers-false
		movie = BookmarkManager.getInstance().createMovie(3000, "Citizen Kane", "", 1941,
				new String[] { "Orson Welles", "Joseph Cotten" }, new String[] { "Orson Welles" }, MovieGenre.THRILLERS.getValue(),
				8.5, KidFriendlyStatus.UNKNOWN.getValue());
		isKidFriendly = movie.iskidFriendlyEligible();
		assertFalse("Movie genre is Thrillers: iskidFriendlyEligible() must return false",isKidFriendly);	
		
		//Testcase 3: genre is sex releted-false
		movie = BookmarkManager.getInstance().createMovie(3000, "Citizen Kane", "", 1941,
				new String[] { "Orson Welles", "Joseph Cotten" }, new String[] { "Orson Welles" }, MovieGenre.GAY_AND_LESBIAN.getValue(),
				8.5, KidFriendlyStatus.UNKNOWN.getValue());
		isKidFriendly = movie.iskidFriendlyEligible();
		assertFalse("Movie genre is sex related: iskidFriendlyEligible() must return false",isKidFriendly);
		
	}

}
