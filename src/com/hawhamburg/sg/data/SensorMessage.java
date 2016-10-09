package com.hawhamburg.sg.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorMessage {
	
	private final static int DATA_VERSION = 1;

	private int version;
	private String sensortype;
	private List<Value> values;

	//private SensorMessage() {}
	@JsonCreator(mode=Mode.PROPERTIES)
	public SensorMessage(@JsonProperty("version") int version,
						@JsonProperty("sensortype") String sensortype,
						@JsonProperty("values") List<Value> values) {
		this.sensortype = sensortype;
		this.values = values;
		this.version = version;
	}
	
	public SensorMessage(String sensortype,
						List<Value> values) {
		this.sensortype = sensortype;
		this.values = values;
		this.version = DATA_VERSION;
	}
	
	public int getVersion() {
		return version;
	}

	public String getSensortype() {
		return sensortype;
	}

	public List<Value> getValues() {
		return values;
	}
}
