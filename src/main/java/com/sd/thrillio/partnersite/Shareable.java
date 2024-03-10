package com.sd.thrillio.partnersite;


//this interface is used as a mixin to implement getItemData() function in Book and WebSite class.
public interface Shareable {
    
	//this method will return Book/WebSite data in XML format which is a string.
	//because sharing information between webSites are done mostly in XML/JSON format.
	String getItemData();
	
}
