package com.hawhamburg.sg.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SensorValue extends AbstractValue {

	private int id;
	@JsonCreator
	public SensorValue(@JsonProperty("id") int id,@JsonProperty("value") double value) {
		super(value);
		this.id=id;
	}
	
	public int getId()
	{
		return id;
	}
}
