package com.hawhamburg.sg.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorMessage {
	
	private final static int DATA_VERSION = 1;

	private long timestamp;
	private int version;
	private SensorType sensortype;
	private List<Value> values;

	//private SensorMessage() {}
	@JsonCreator(mode=Mode.PROPERTIES)
	public SensorMessage(@JsonProperty("version") int version,
						@JsonProperty("sensortype") SensorType sensortype,
						@JsonProperty("values") List<Value> values,
						@JsonProperty("timestamp") long timestamp) {
		this.sensortype = sensortype;
		this.values = values;
		this.version = version;
		this.timestamp=timestamp;
	}
	
	public SensorMessage(SensorType sensortype,
						List<Value> values) {
		this.sensortype = sensortype;
		this.values = values;
		this.version = DATA_VERSION;
	}
	
	public int getVersion() {
		return version;
	}

	public SensorType getSensortype() {
		return sensortype;
	}

	public List<Value> getValues() {
		return values;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}
}
