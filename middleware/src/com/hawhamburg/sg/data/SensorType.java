package com.hawhamburg.sg.data;

public enum SensorType {
	pressure,
	temperature,
	acceleration,
	gyroscope,
	location(LocationValue.class,AbsoluteLocationValue.class),
	distance,
	microphone;

	private Class<?> sensorCls;
	private Class<?> chairCls;
	
	SensorType(Class<?> sensorCls, Class<?> chairCls)
	{
		this.sensorCls=sensorCls;
		this.chairCls=chairCls;
	}
	SensorType()
	{
		this(SensorValue.class,SensorValue.class);
	}
	
	public <T> Class<T> getSensorValueClass()
	{
		return (Class<T>)sensorCls;
	}	
	public <T> Class<T> getChairValueClass()
	{
		return (Class<T>)chairCls;
	}
}
