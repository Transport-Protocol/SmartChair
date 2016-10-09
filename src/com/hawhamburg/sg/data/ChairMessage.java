package com.hawhamburg.sg.data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ChairMessage {
	private final static int DATA_VERSION = 1;

	private int version;
	private String sensortype;
	private List<Value> values;
	private String deviceUuid;

	@JsonCreator
	public ChairMessage(@JsonProperty("deviceUuid") String deviceUuid, 
						@JsonProperty("version") int version,
						@JsonProperty("sensortype") String sensortype,
						@JsonProperty("values") List<Value> values) {
		this.sensortype = sensortype;
		this.values = values;
		this.deviceUuid = deviceUuid;
		this.version = version;
	}
	
	public ChairMessage(String deviceUuid, String sensortype, List<Value> values) {
		this.sensortype = sensortype;
		this.values = values;
		this.deviceUuid = deviceUuid;
		this.version = DATA_VERSION;
	}

	public Integer getVersion() {
		return version;
	}

	public String getSensortype() {
		return sensortype;
	}

	public String getDeviceUuid() {
		return deviceUuid;
	}


	public List<Value> getValues() {
		return values;
	}
}
