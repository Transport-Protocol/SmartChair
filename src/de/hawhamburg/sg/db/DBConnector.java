package de.hawhamburg.sg.db;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Point;
import org.influxdb.dto.Point.Builder;

import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.Value;

public class DBConnector {
	
	private InfluxDB influxDB;
	private String dbName;
	
	private DBProperties properties;

	
	public DBConnector(DBProperties props){
		try {
			influxDB = InfluxDBFactory.connect("http://"+props.getDbHost()+":"+props.getDbPort(),props.getDbUser(), props.getDbPassword());
			this.properties = props;
			this.dbName = props.getDBName();
			// Flush every 2000 Points, at least every 100ms
			influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void write(ChairMessage msg ){
		influxDB.write(dbName, "autogen", chairMessageToPoint(msg));
	}
	
	public static void main(String... args) throws IOException, TimeoutException {
		
		
		
		DBProperties props = new DBProperties(true);
	    DBConnector connector = new DBConnector(props);
	    
	    MqConsumer cosumer = new MqConsumer(connector);
	}
//		
	 private Point chairMessageToPoint(ChairMessage msg){
		 
		 Builder pointBuilder = Point.measurement(msg.getSensortype()).time(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                 for (Value v : msg.getValues()) {
					pointBuilder.addField(String.valueOf(v.getId()), v.getValue());
				}
                 pointBuilder.tag("ChairUUID", msg.getDeviceUuid());
		return pointBuilder.build();
	 }
	
	
	
}
