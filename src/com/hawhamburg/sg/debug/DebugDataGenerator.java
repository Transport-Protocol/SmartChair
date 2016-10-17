package com.hawhamburg.sg.debug;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.Value;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import com.hawhamburg.sg.mwrp.RabbitMqConstants;

public class DebugDataGenerator {
	
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
				channel.basicPublish(RabbitMqConstants.MQ1_EXCHANGE_NAME, RabbitMqConstants.MQ1_ROUTING_KEY, null, getExampleData().getBytes());
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
		Random rng=new Random();
		ObjectMapper objectMapper = new ObjectMapper();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA); i++)
		{
			Value value = new Value(valueId++,rng.nextInt(1024));
			values.add(value);
		}
		int o=rng.nextInt(SensorType.values().length);
		SensorType type = SensorType.values()[o];
		SensorMessage msg = new SensorMessage(1,type, values,System.currentTimeMillis());
		String json = "";
		try {
			json = objectMapper.writeValueAsString(msg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public static ChairMessage getChairMessage()
	{
		Random rng=new Random();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA); i++)
		{
			Value value = new Value(valueId++,rng.nextInt(1024));
			values.add(value);
		}
		int o=rng.nextInt(SensorType.values().length);
		SensorType type = SensorType.values()[o];
		ChairMessage msg = new ChairMessage("1", 1, type, values, System.currentTimeMillis());
		return msg;
	}


}
