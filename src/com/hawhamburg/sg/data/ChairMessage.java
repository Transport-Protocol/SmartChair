package com.hawhamburg.sg.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChairMessage {
	private final static int DATA_VERSION = 1;

	private long timestamp;
	private int version;
	private SensorType sensortype;
	private List<Value> values;
	private String deviceUuid;

	@JsonCreator
	public ChairMessage(@JsonProperty("deviceUuid") String deviceUuid, 
						@JsonProperty("version") int version,
						@JsonProperty("sensortype") SensorType sensortype,
						@JsonProperty("values") List<Value> values,
						@JsonProperty("timestamp") long timestamp) {
		this.sensortype = sensortype;
		this.values = values;
		this.deviceUuid = deviceUuid;
		this.version = version;
		this.timestamp=timestamp;
	}
	
	public ChairMessage(String deviceUuid, SensorType sensortype, List<Value> values) {
		this.sensortype = sensortype;
		this.values = values;
		this.deviceUuid = deviceUuid;
		this.version = DATA_VERSION;
	}

	public Integer getVersion() {
		return version;
	}

	public SensorType getSensortype() {
		return sensortype;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}

	public List<Value> getValues() {
		return values;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}
}
