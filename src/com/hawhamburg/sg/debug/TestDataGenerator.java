package com.hawhamburg.sg.debug;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.Value;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TestDataGenerator {
	
	private static final int MAXVALUESPERDATA = 10;
	private static final int NUMDATA = 10;
	private static int valueId = 0;
	
	public static void main(String[] args) 
	{
		ConnectionFactory factory = new ConnectionFactory();		
		
		try {
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			
			for(int i=0; i<NUMDATA; i++)
			{
				channel.basicPublish("my-exchange", "my-routing-key", null, getExampleData().getBytes());
			}
			
			channel.close();
			conn.close();
			
		} catch (IOException | TimeoutException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static String getExampleData()
	{
		ObjectMapper objectMapper = new ObjectMapper();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i < new Random().nextInt(MAXVALUESPERDATA); i++)
		{
			Value value = new Value(valueId++,new Random().nextInt(1024));
			values.add(value);
		}
		
		String type = "";
		switch (new Random().nextInt(5))
		{
		case 0:
			type = "pressure";
			break;
		case 1:
			type = "temperature";
			break;
		case 2:
			type = "accelerometer";
			break;
		case 3:
			type = "microphone";
			break;
		case 4:
			type = "distance_sensor";
			break;
		}
		SensorMessage msg = new SensorMessage(1,type, values);
		String json = "";
		try {
			json = objectMapper.writeValueAsString(msg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}

}
