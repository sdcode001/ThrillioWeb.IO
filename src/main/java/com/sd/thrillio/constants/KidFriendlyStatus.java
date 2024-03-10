package com.sd.thrillio.constants;

public enum KidFriendlyStatus {	
	APPROVED("approved"),
	REJECTED("rejected"),
	UNKNOWN("unknown");
	
    private String value;
	
	private KidFriendlyStatus(String value) {
		this.value=value;
	}
	
	public String getValue() {
		return value;
	}

}
