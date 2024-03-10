package com.sd.thrillio.entities;

import java.util.Arrays;

import com.sd.thrillio.constants.MovieGenre;

public class Movie extends Bookmark {
    private int releaseYear;
    private String cast[];
    private String directors[];
    private String genre;
	private double imdbrating;

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String[] getCast() {
		return cast;
	}

	public void setCast(String[] cast) {
		this.cast = cast;
	}

	public String[] getDirectors() {
		return directors;
	}

	public void setDirectors(String[] directors) {
		this.directors = directors;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public double getImdbrating() {
		return imdbrating;
	}

	public void setImdbrating(double imdbrating) {
		this.imdbrating = imdbrating;
	}

	@Override
	public String toString() {
		return  "Movie "+super.toString()+"releaseYear=" + releaseYear + ", cast=" + Arrays.toString(cast) + ", directors="
				+ Arrays.toString(directors) + ", genre=" + genre + ", imdbrating=" + imdbrating + "]";
	}

	@Override
	public boolean iskidFriendlyEligible() {
		if (genre.equals(MovieGenre.HORROR.getValue()) || genre.equals(MovieGenre.THRILLERS.getValue())
				|| genre.equals(MovieGenre.FOREGIN_THRILLERS.getValue()) || genre.equals(MovieGenre.GAY_AND_LESBIAN.getValue())) {
			return false;
		}
		return true;
	}
}
