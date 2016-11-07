package com.hawhamburg.sg.debug;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.hawhamburg.sg.data.AbstractValue;
import com.hawhamburg.sg.data.LocationValue;
import com.hawhamburg.sg.data.SensorValue;

class RandomDataGenerator {
	private static int valueId = 0;
	private static final int MAXVALUESPERDATA = 10;

	static List<AbstractValue> getTempData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(valueId++,rng.nextInt(35));
			values.add(value);
		}
		return values;
	}
	
	static List<AbstractValue> getPressureData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(valueId++,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}
	
	static List<AbstractValue> getAccelerationData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(valueId++,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}
	
	static List<AbstractValue> getDistanceData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(valueId++,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}
	
	static List<AbstractValue> getGyroscopeData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(valueId++,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}
	
	static List<AbstractValue> getLocationValueData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new LocationValue("0613ff4c000c0e00b62bd6450252fce7",1,rng.nextInt(4)+1,-1*(rng.nextInt(70)+25));
			values.add(value);
		}
		return values;
	}
	
	static List<AbstractValue> getMicrophoneData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(valueId++,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}

}
