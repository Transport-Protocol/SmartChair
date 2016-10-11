package com.hawhamburg.sg.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Value {
	
	private int id;
	private double value;
	
	@JsonCreator
	public Value(@JsonProperty("id") int id, @JsonProperty("value") double value){
		this.id = id;
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
	
	public double getValue() {
		return value;
	}
	
}
