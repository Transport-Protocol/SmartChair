package de.hawhamburg.sg.messenchair;

import java.io.IOException;
import java.util.List;

import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.ChatMessage;
import com.hawhamburg.sg.data.SensorValue;

public class ChatChair {
	private static final int LIMIT = 250;
	private static final int MEASUREMENTTIME = 2000;
	private boolean manned = false;
	private long firstMeasurementTimestamp = 0;
	private long firstMannedTimestamp = 0;
	private ChatPublisher publisher;
	private final String hostname;
	private int mannedCounter = 0;
	
	public ChatChair(ChatPublisher publisher, String hostname)
	{
		this.publisher = publisher;
		this.hostname = hostname;
	}
	
	void newMessage(ChairMessage<SensorValue> msg)
	{
		if(manned)
		{
			if(averageSeatPressure(msg.getValues()) < LIMIT)
			{
				if(firstMeasurementTimestamp == 0)
				{
					firstMeasurementTimestamp = msg.getTimestamp();
				}
				else if((msg.getTimestamp() - firstMeasurementTimestamp) >= MEASUREMENTTIME)
				{
					manned = !manned;
					firstMeasurementTimestamp = 0;
					printChatMessage(false, msg.getTimestamp());
				}
				
			}
			else
			{
				firstMeasurementTimestamp = 0;
			}
		}
		else
		{
			if(averageSeatPressure(msg.getValues()) > LIMIT)
			{
				if(firstMeasurementTimestamp == 0)
				{
					firstMeasurementTimestamp = msg.getTimestamp();
				}
				else if((msg.getTimestamp() - firstMeasurementTimestamp) >= MEASUREMENTTIME)
				{
					manned = !manned;
					mannedCounter++;
					firstMannedTimestamp = msg.getTimestamp();
					firstMeasurementTimestamp = 0;
					if(averageSeatPressure(msg.getValues()) > 700)
					{
						printChatMessage(true, msg.getTimestamp());
					}
					else
					{
						printChatMessage(false, msg.getTimestamp());
					}
				}
				
			}
			else
			{
				firstMeasurementTimestamp = 0;
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
	
	private void printChatMessage(boolean secret, long timestamp)
	{
		String text = "";
		if(manned)
		{			
			text = "Der Benutzer des Stuhls hat sich hingesetzt.\n"
					+ "Es wurde sich Ingesamt schon " + mannedCounter + " mal auf den Stuhl gesetzt.";
		}
		else
		{
			text = "Der Benutzer des Stuhls ist aufgestanden.\n"
					+ "Er saß für " + (timestamp - firstMannedTimestamp) + " Minuten auf dem Stuhl.";
		}
		
		if(secret)
		{
			text = text + "/n PS: Der Dünnste ist der Benutzer nicht!";
		}
		
		ChatMessage msg = new ChatMessage(text, hostname);
		
		try {
			publisher.publish(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
