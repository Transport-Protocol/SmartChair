package com.hawhamburg.sg.debug;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.Value;

public class ChairMessageGenerator {
	
	private static final int MAXVALUESPERDATA = 10;
	private static int valueId = 0;
	
	public static ChairMessage getChairMessage()
	{
		Random rng=new Random();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA); i++)
		{
			Value value = new Value(valueId++,rng.nextInt(1024));
			values.add(value);
		}
		int o=rng.nextInt(SensorType.values().length);
		SensorType type = SensorType.values()[o];
		ChairMessage msg = new ChairMessage("1", 1, type, values, System.currentTimeMillis());
		return msg;
	}

}
