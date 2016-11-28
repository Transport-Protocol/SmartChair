package com.hawhamburg.sg.data;

import java.io.IOException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatMessage {
	private final static int DATA_VERSION = 1;
	private static final ObjectMapper mapper=new ObjectMapper();
	private int version;
	private String message;
	private String sender;
	private byte[] image;
	private String imageName;
	
	@JsonCreator
	public ChatMessage(	@JsonProperty("version") int version,
							@JsonProperty("message") String message,
							@JsonProperty("sender") String sender,
							@JsonProperty("image") byte[] image,
							@JsonProperty("imageName") String imageName
						) throws Exception{
		if (version != DATA_VERSION) {
			throw new Exception("VersionMismatch!");
		}
		this.version = version;
		this.message = message;
		this.sender = sender;
		this.image = image;
		this.imageName = imageName;
		
	}
	
	public ChatMessage(String message, String sender) {
		this.message = message;
		this.version = DATA_VERSION;
		this.sender = sender;
	}
	
	public static ChatMessage parseJson(byte[] b) throws JsonProcessingException, IOException
	{
		return mapper.readValue(b, ChatMessage.class);
	}

	public int getVersion() {
		return version;
	}

	public String getMessage() {
		return message;
	}

	public String getSender() {
		return sender;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public String getImageName() {
		return imageName;
	}
}
