package com.hawhamburg.sg.data;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SensorMessage<T extends AbstractValue> {
	
	private final static int DATA_VERSION = 1;
	private final static ObjectMapper mapper=new ObjectMapper();

	private long timestamp;
	private int version;
	private SensorType sensortype;
	private List<T> values;

	//private SensorMessage() {}
	@JsonCreator(mode=Mode.PROPERTIES)
	public SensorMessage(@JsonProperty("version") int version,
						@JsonProperty("sensortype") SensorType sensortype,
						@JsonProperty("values") List<T> values,
						@JsonProperty("timestamp") long timestamp) {
		this.sensortype = sensortype;
		this.values = values;
		this.version = version;
		this.timestamp=timestamp;
	}
	
	public SensorMessage(SensorType sensortype,
						List<T> values) {
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

	public List<T> getValues() {
		return values;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}
	
	public static SensorMessage<?> parseJson(byte[] b) throws JsonProcessingException, IOException
	{
		JsonNode node=mapper.readTree(b);
		SensorType sensorType= SensorType.valueOf(node.get("sensortype").asText());
		JavaType t=mapper.getTypeFactory().constructParametricType(SensorMessage.class, sensorType.getSensorValueClass());
		
		return mapper.convertValue(node, t);
		
	}
}
