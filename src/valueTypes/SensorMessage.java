package valueTypes;

import java.util.List;

public class SensorMessage {
	
	Integer version;
	
	String sensor;
	
	List<Value> values;
	
	public SensorMessage() {}
	
	public SensorMessage(Integer version, String sensor, List<Value> values) {
		this.version = version;
		this.sensor = sensor;
		this.values = values;
	}
	
	public Integer getVersion() {
		return version;
	}

	public String getSensor() {
		return sensor;
	}

	public List<Value> getValues() {
		return values;
	}
	
	
	
}
