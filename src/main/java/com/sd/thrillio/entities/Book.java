package com.sd.thrillio.entities;

import java.util.Arrays;
import com.sd.thrillio.constants.BookGenre;
import com.sd.thrillio.partnersite.Shareable;



public class Book extends Bookmark implements Shareable {
	private int publicationYear;
	private String publisher;
	private String imageUrl;
	private String authors[];
	private String genre;
	private double amazonRating;

	public int getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public double getAmazonRating() {
		return amazonRating;
	}

	public void setAmazonRating(double amazonRating) {
		this.amazonRating = amazonRating;
	}

	@Override
	public String toString() {
		return "Book "+super.toString()+"publicationYear=" + publicationYear + ", publisher=" + publisher + ", authors="
				+ Arrays.toString(authors) + ", genre=" + genre + ", amazonRating=" + amazonRating + "]";
	}

	@Override
	public boolean iskidFriendlyEligible() {
		if(genre.equals(BookGenre.PHILOSOPHY.getValue()) || genre.equals(BookGenre.SELF_HELP.getValue())) {
			return false;
		}
		return true;
	}

	//Here we will use StringBuilder instead of + operator to build the XML format string of all data 
	//because in critical usage it is not safe to use + operator.
	@Override
	public String getItemData() {
		StringBuilder builder = new StringBuilder();
		builder.append("<item>")
		.append("<type>Book</type>")
		.append("<title>").append(getTitle()).append("</title>")
		.append("<profileUrl>").append(getProfileUrl()).append("</profileUrl>")
		.append("<authors>").append(String.join(",", authors)).append("</authors>")
		.append("<publisher>").append(publisher).append("</publisher>")
		.append("<publicationYear>").append(publicationYear).append("</publicationYear>")
		.append("<genre>").append(genre).append("</genre>")
        .append("<amazonRating>").append(amazonRating).append("</amazonRating>")
		.append("</item>");
		return builder.toString();
	}

}
