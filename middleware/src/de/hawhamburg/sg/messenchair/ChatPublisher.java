package de.hawhamburg.sg.messenchair;

import java.io.IOException;
import java.util.List;

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
	
	public ChatPublisher(Connection connection) {
		this.connection = connection;
		try {
			channel=connection.createChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	void newMessage(ChairMessage<SensorValue> msg)
	{
		if(manned)
		{
			if(averageSeatPressure(msg.getValues()) < LIMIT)
			{
				if(firstTimestamp == 0)
				{
					firstTimestamp = msg.getTimestamp();
				}
				else if((msg.getTimestamp() - firstTimestamp) >= MEASUREMENTTIME)
				{
					manned = !manned;
					firstTimestamp = 0;
					publishManned(manned);
				}
				
			}
			else
			{
				firstTimestamp = 0;
			}
		}
		else
		{
			if(averageSeatPressure(msg.getValues()) > LIMIT)
			{
				if(firstTimestamp == 0)
				{
					firstTimestamp = msg.getTimestamp();
				}
				else if((msg.getTimestamp() - firstTimestamp) >= MEASUREMENTTIME)
				{
					manned = !manned;
					firstTimestamp = 0;
					publishManned(manned);
				}
				
			}
			else
			{
				firstTimestamp = 0;
			}
		}
	}
	
	private int averageSeatPressure(List<SensorValue> values)
	{
		double average = 0;
		for(SensorValue value: values)
		{
			// Seat pressure Sensor id 0-3
			if(value.getId() <= 3)
			{
				average += value.getValue();
			}
		}
		return (int)(average / 4);
	}
	
	private void publishManned(boolean manned)
	{
		String text = "";
		if(manned)
		{
			text = "Der Benutzer des Stuhls ist aufgestanden.";
		}
		else
		{
			text = "Der Benutzer des Stuhls hat sich hingesetzt";
		}
		ChatMessage msg = new ChatMessage(text, "chair");
		
		try {
			publish(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void publish(ChatMessage msg) throws IOException
	{
		channel.basicPublish(RabbitMqConstants.CHAT_EXCHANGE_NAME, RabbitMqConstants.CHAT_ROUTING_KEY,null, serializer.writeValueAsBytes(msg));
	}

}
