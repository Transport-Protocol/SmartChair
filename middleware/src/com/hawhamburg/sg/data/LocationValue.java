package com.hawhamburg.sg.data;

import org.influxdb.dto.Point.Builder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LocationValue extends AbstractValue {
	//{"uuid" : "0613ff4c000c0e00b62bd6450252fce7", "major" : "53295", "minor" : "55638", "dB" : "-59}
	private String uuid;
	private int major;
	private int minor;
	
	@JsonCreator
	public LocationValue(@JsonProperty("uuid") String uuid,
						 @JsonProperty("major") int major,
						 @JsonProperty("minor") int minor,
						 @JsonProperty("value") int value)
	{
		super(value);
		this.uuid=uuid;
		this.major=major;
		this.minor=minor;
	}

	public String getUuid() {
		return uuid;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}
	public void addValueToPoint(Builder pointBuilder){
		pointBuilder.addField("major", getMajor());
		pointBuilder.addField("minor", getMinor());
		pointBuilder.addField("value", getValue());
	}
}
