package de.hawhamburg.sg.messenchair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.ChatMessage;
import com.hawhamburg.sg.data.SensorValue;
import com.hawhamburg.sg.mwrp.RabbitMqConstants;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class ChatPublisher {
	private Connection connection;
	private Channel channel;
	private ObjectMapper serializer=new ObjectMapper();
	private long firstTimestamp;
	private static final int LIMIT = 250;
	private static final int MEASUREMENTTIME = 5000;
	private static boolean manned = false;
	private final static Map<String, ChatChair> CHAIRS = new HashMap<>();
	
	
	public ChatPublisher(Connection connection) {

		CHAIRS.put("e46b9f05-5075-4608-9d39-049e63cb0607", new ChatChair(this, "chair1"));
		CHAIRS.put("dad82379-e0c2-4b8b-82f6-1823ac340633", new ChatChair(this, "chair2"));
		
		this.connection = connection;
		try {
			channel=connection.createChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	void newMessage(ChairMessage<SensorValue> msg)
	{
		CHAIRS.get(msg.getDeviceUuid()).newMessage(msg);
	}
	
	void publishManned(boolean manned, String sender)
	{
		String text = "";
		if(manned)
		{			
			text = "Der Benutzer des Stuhls hat sich hingesetzt";
		}
		else
		{
			text = "Der Benutzer des Stuhls ist aufgestanden.";
		}
		ChatMessage msg = new ChatMessage(text, sender);
		
		try {
			publish(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void publish(ChatMessage msg) throws IOException
	{
		channel.basicPublish(RabbitMqConstants.CHAT_EXCHANGE_NAME, RabbitMqConstants.ALL_CHAT_ROUTING_KEY,null, serializer.writeValueAsBytes(msg));
	}

}
