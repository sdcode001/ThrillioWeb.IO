package com.sd.thrillio.entities;

import com.sd.thrillio.partnersite.Shareable;

public class WebLink extends Bookmark implements Shareable {
    private String url;
	private String host;
	private String htmlPage;
	private DownloadStatus downloadStatus = DownloadStatus.NOT_ATTEMPTED;
	
	public enum DownloadStatus{
		NOT_ATTEMPTED,
		SUCCESS,
		FAILED,
		NOT_ELIGIBLE;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getHtmlPage() {
		return htmlPage;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage = htmlPage;
	}

	public DownloadStatus getDownloadStatus() {
		return downloadStatus;
	}

	public void setDownloadStatus(DownloadStatus downloadStatus) {
		this.downloadStatus = downloadStatus;
	}

	@Override
	public String toString() {
		return "WebLink "+super.toString()+"url=" + url + ", host=" + host + "]";
	}

	@Override
	public boolean iskidFriendlyEligible() {
		if(url.contains("porn") || getTitle().contains("porn") || host.contains("adult")) {
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
		.append("<type>WebLink</type>")
		.append("<title>").append(getTitle()).append("</title>")
		.append("<profileUrl>").append(getProfileUrl()).append("</profileUrl>")
		.append("<url>").append(url).append("</url>")
        .append("<host>").append(host).append("</host>")
		.append("</item>");
		return builder.toString();
	}
    
}
