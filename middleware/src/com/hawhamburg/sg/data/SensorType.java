package com.hawhamburg.sg.data;

public enum SensorType {
	pressure,
	temperature,
	acceleration,
	gyroscope,
	location(LocationValue.class),
	distance,
	microphone;
	
	private Class<?> cls;
	SensorType(Class<?> cls)
	{
		this.cls=cls;
	}
	SensorType()
	{
		this(Value.class);
	}
	
	public <T> Class<T> getValueObjectClass()
	{
		return (Class<T>)cls;
	}
}
