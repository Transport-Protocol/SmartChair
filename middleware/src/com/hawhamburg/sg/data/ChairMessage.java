package com.hawhamburg.sg.data;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChairMessage<T extends AbstractValue> {
	private final static int DATA_VERSION = 1;
	private static final ObjectMapper mapper=new ObjectMapper();
	private long timestamp;
	private int version;
	private SensorType sensortype;
	private List<T> values;
	private String deviceUuid;

	@JsonCreator
	public ChairMessage(@JsonProperty("deviceUuid") String deviceUuid, 
						@JsonProperty("version") int version,
						@JsonProperty("sensortype") SensorType sensortype,
						@JsonProperty("values") List<T> values,
						@JsonProperty("timestamp") long timestamp) {
		this.sensortype = sensortype;
		this.values = values;
		this.deviceUuid = deviceUuid;
		this.version = version;
		this.timestamp=timestamp;
	}
	
	public ChairMessage(String deviceUuid, SensorType sensortype, List<T> values) {
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

	public List<T> getValues() {
		return values;
	}
	
	public long getTimestamp()
	{
		return timestamp;
	}

	public static ChairMessage<?> parseJson(byte[] b) throws JsonProcessingException, IOException
	{
		JsonNode node=mapper.readTree(b);
		SensorType sensorType= SensorType.valueOf(node.get("sensortype").asText());
		JavaType t=mapper.getTypeFactory().constructParametricType(ChairMessage.class, sensorType.getChairValueClass());
		
		return mapper.convertValue(node, t);
		
	}
}
