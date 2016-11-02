package com.hawhamburg.sg.data;

import org.influxdb.dto.Point.Builder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AbsoluteLocationValue extends AbstractValue
{
	private int x;
	private int y;
	@JsonCreator
	public AbsoluteLocationValue(@JsonProperty("x") int x, @JsonProperty("y") int y)
	{
		super(0);
		this.x=x;
		this.y=y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

	@Override
	public void addValueToPoint(Builder pointBuilder) {
		
	}
}
