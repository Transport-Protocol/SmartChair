package com.hawhamburg.sg.mwrp.gamectrl.webinterface;

public enum MimeType {
	html("text/html"),
	js("application/javascript"),
	json("application/json"),
	css("text/css"),
	png("image/png");
	
	private String mimeType;
	
	MimeType(String mimeType)
	{
		this.mimeType=mimeType;
	}
	
	public String getMimeType()
	{
		return mimeType;
	}
}
