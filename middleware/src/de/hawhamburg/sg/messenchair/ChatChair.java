package de.hawhamburg.sg.messenchair;

import java.util.List;

import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.SensorValue;

public class ChatChair {
	private static final int LIMIT = 250;
	private static final int MEASUREMENTTIME = 2000;
	private static boolean manned = false;
	private long firstTimestamp = 0;
	private ChatPublisher publisher;
	private final String hostname;
	
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
				if(firstTimestamp == 0)
				{
					firstTimestamp = msg.getTimestamp();
				}
				else if((msg.getTimestamp() - firstTimestamp) >= MEASUREMENTTIME)
				{
					manned = !manned;
					firstTimestamp = 0;
					publisher.publishManned(manned, hostname);
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
					publisher.publishManned(manned, hostname);
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
}
