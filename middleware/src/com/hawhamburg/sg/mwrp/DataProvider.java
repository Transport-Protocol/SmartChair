package com.hawhamburg.sg.mwrp;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.Value;

public class DataProvider {
	private Map<SensorType, List<SensorMessage>> valueLists=new HashMap<>();
	
	public DataProvider()
	{
		for(SensorType s:SensorType.values())
		{
			valueLists.put(s,new LinkedList<>());
		}
	}
	
	public void addValues(SensorMessage msg)
	{
		if(msg==null)
			throw new IllegalArgumentException();
		valueLists.get(msg.getSensortype()).add(msg);
	}
	
	public List<SensorMessage> getDataForPastXSeconds(SensorType type,long seconds)
	{
		List<SensorMessage> l=new LinkedList<>(valueLists.get(type));
		if(l.size()==0)
			return l;
		List<SensorMessage> ret=new LinkedList<>();
		int index=l.size()-1;
		SensorMessage msg=null;
		while(index>=0&&(msg=l.get(index)).getTimestamp()>=seconds)
		{
			ret.add(0,msg);
			index--;
		}
		return ret;
	}
	
	public List<Value> getMostRecent(SensorType type)
	{
		List<SensorMessage> l=valueLists.get(type);
		return l.size()>0?l.get(l.size()-1).getValues():null;
	}
}
