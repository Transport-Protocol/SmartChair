package com.hawhamburg.sg.mwrp;

import java.util.LinkedList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;

public class DataProvider {
	private List<SensorMessage> pressure=new LinkedList<>();
	
	public void addValues(SensorMessage msg)
	{
		if(msg==null)
			throw new IllegalArgumentException();
		switch(msg.getSensortype())
		{
		case pressure:
			pressure.add(msg);
			break;
			default:
				throw new RuntimeException("unknown sensor type");
		}
	}
	
	public void getDataForPastXSeconds(SensorType type,long seconds)
	{
		List<SensorMessage> msg=getAllDataBySensorType(type);
	}
	
	private List<SensorMessage> getAllDataBySensorType(SensorType type)
	{
		switch(type)
		{
			case pressure:
				return pressure;
			default:
				throw new RuntimeException("unknown sensor type");
		}
	}
}
