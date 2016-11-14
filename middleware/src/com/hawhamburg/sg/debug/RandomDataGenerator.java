package com.hawhamburg.sg.debug;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.hawhamburg.sg.data.AbstractValue;
import com.hawhamburg.sg.data.LocationValue;
import com.hawhamburg.sg.data.SensorValue;

class RandomDataGenerator {
	private static int valueId = 0;		//new message -> valueId++
	private static final int MAXVALUESPERDATA = 10; //Values per Message between 0 and MAXVALUESPERDATA
	
	/*
	 * Returns a List with random generated temperature data between 0-35°
	 * @return List with random generated temperature data between 0-35° as SensorValue   
	 */
	static List<AbstractValue> getTempData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(0,rng.nextInt(35));
			values.add(value);
		}
		return values;
	}
	
	/*
	 * Returns a List with random generated pressure data between 0-1024
	 * @return List with random generated pressure data between 0-1024 as SensorValue   
	 */
	static List<AbstractValue> getPressureData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(rng.nextInt(10),rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}
	
	/*
	 * Returns a List with random generated acceleration data between 0-1024
	 * @return List with random generated acceleration data between 0-1024 as SensorValue   
	 */
	static List<AbstractValue> getAccelerationData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(0,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}
	
	/*
	 * Returns a List with random generated distance data between 0-1024
	 * @return List with random generated distance data between 0-1024 as SensorValue   
	 */
	static List<AbstractValue> getDistanceData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(0,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}
	
	/*
	 * Returns a List with random generated gyroscope data between 0-1024
	 * @return List with random generated gyroscope data between 0-1024 as SensorValue   
	 */
	static List<AbstractValue> getGyroscopeData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(0,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}
	
	/*
	 * DEPRECATED
	 * Returns a List with random generated LocationValue data.
	 * UUID: 0613ff4c000c0e00b62bd6450252fce7
	 * Major : 1
	 * Minor : 0-4
	 * value: 25-95db 
	 * @return List with random generated LocationValue data between 25-95db   
	 */
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
	
	/*
	 * Returns a List with random generated microphone data between 0 - 1024
	 * @return List with random generated microphone data between 0 - 1024 as SensorValue   
	 */
	static List<AbstractValue> getMicrophoneData()
	{
		Random rng=new Random();
		List<AbstractValue> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			AbstractValue value = new SensorValue(0,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}

}
