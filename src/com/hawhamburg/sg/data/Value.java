package com.hawhamburg.sg.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Value {
	
	private int id;
	private int value;
	
	@JsonCreator
	public Value(@JsonProperty("id") int id, @JsonProperty("value") int value){
		this.id = id;
		this.value = value;
	}
	
	public int getId() {
		return id;
	}
	
	public int getValue() {
		return value;
	}
	
}
