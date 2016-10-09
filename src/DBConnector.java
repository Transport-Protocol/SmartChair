import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDB.ConsistencyLevel;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

public class DBConnector {
	
	InfluxDB influxDB;
	String dbName;
	
	static final int VALUESPERDATA = 3;
	
	public DBConnector(String dbName){
		influxDB = InfluxDBFactory.connect("http://192.168.63.31:8086", "middleman", "middleman_pass");
		this.dbName = dbName;
		// Flush every 2000 Points, at least every 100ms
		influxDB.enableBatch(2000, 100, TimeUnit.MILLISECONDS);
	}
	
	public void write(Point point ){
		influxDB.write(dbName, "autogen", point);
	}

	public static void main(String[] args) {
		
		DBConnector db = new DBConnector("protochair_sensor");
		
		
//		BatchPoints batchPoints = BatchPoints
//                .database(dbName)
//                .tag("async", "true")
//                .retentionPolicy("autogen")
//                .consistency(ConsistencyLevel.ALL)
//                .build();
		for(int i = 0; i < VALUESPERDATA; i++)
		{
			Point point = Point.measurement("sitzplatte")
			                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
			                    .addField("sensor1", new Random().nextInt(1024))
			                    .addField("sensor2", new Random().nextInt(1024))
			                    .addField("sensor3", new Random().nextInt(1024))
			                    .addField("sensor4", new Random().nextInt(1024))
			                    .build();
			db.write(point);
			
//			batchPoints.point(point1);
//			
//			influxDB.write(dbName, "autogen", point1);
		}
//		influxDB.write(batchPoints);
	}
	
}
