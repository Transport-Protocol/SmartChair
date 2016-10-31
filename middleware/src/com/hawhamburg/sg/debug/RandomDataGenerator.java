package com.hawhamburg.sg.debug;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.hawhamburg.sg.data.Value;

class RandomDataGenerator {
	private static int valueId = 0;
	private static final int MAXVALUESPERDATA = 10;

	List<Value> getTempData()
	{
		Random rng=new Random();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			Value value = new Value(valueId++,rng.nextInt(35));
			values.add(value);
		}
		return values;
	}
	
	List<Value> getPressureData()
	{
		Random rng=new Random();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			Value value = new Value(valueId++,rng.nextInt(1024));
			values.add(value);
		}
		return values;
	}
	
	List<Value> getAccelerationData()
	{
		Random rng=new Random();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			Value value = new Value(valueId++,rng.nextInt(35));
			values.add(value);
		}
		return values;
	}
	
	List<Value> getDistance()
	{
		Random rng=new Random();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			Value value = new Value(valueId++,rng.nextInt(35));
			values.add(value);
		}
		return values;
	}
	
	List<Value> getGyroscope()
	{
		Random rng=new Random();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA-1)+1; i++)
		{
			Value value = new Value(valueId++,rng.nextInt(35));
			values.add(value);
		}
		return values;
	}

}
