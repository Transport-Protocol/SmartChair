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

import de.hawhamburg.sg.db.DBConnector;
import de.hawhamburg.sg.db.DBProperties;
import com.hawhamburg.sg.mwrp.RabbitMqConstants;

public class DebugDataGenerator {
	
	private static final int MAXVALUESPERDATA = 10;
	private static final int STANDARDNUMDATA = 10;
	private static final int STANDARDDELAY = 1;
	private static int valueId = 0;
	
	public static void main(String[] args) 
	{
		String modus = null;
		int delay = STANDARDDELAY;
		int numData = STANDARDNUMDATA;
		for(int i = 0; i< args.length; i++)
		{
			if(args[i].equals("raspi") || args[i].equals("server") || args[i].equals("database"))
			{
				modus = args[i];
			}
			else if (args[i].equals(delay)) 
			{
				delay = Integer.parseInt(args[i+1]);
			}
			else if(args[i].equals("numData"))
			{
				numData = Integer.parseInt(args[i+1]);
			}
			else if(args[i].equals("help"))
			{
				System.out.println("Usage: java Myprogram MODUS [Options] || java Myprogram [Options] MODUS");
				System.out.println("MODUS: \n"
						+ "raspi			Push messages to the raspi queue(has to run on raspi)\n"
						+ "server			Push messages to the server queue(has to run on server)\n"
						+ "database			Push messages to the database(Configuration in properties file)");
				System.out.println("Options:\n"
						+ "delay NUMBER		Set the delay between the messages in ms\n"
						+ "numData NUMBER	Set the count of messages\n");
				System.exit(0);
			}
			
		}
		
		if(modus.equals("raspi"))
		{
			testRasPi(numData, delay);
		}
		else if(modus.equals("server"))
		{
			testServer(numData, delay);
		}
		else if(modus.equals("database"))
		{
			testDatabase(numData, delay);
		}
		
	}
	
	public static void testRasPi(int numData, int delay)
	{
		ConnectionFactory factory = new ConnectionFactory();		
		
		try {
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			
			for(int i=0; i<numData; i++)
			{
				channel.basicPublish(RabbitMqConstants.MQ1_EXCHANGE_NAME, RabbitMqConstants.MQ1_ROUTING_KEY, null, getSensorMessageAsJson().getBytes());
				Thread.sleep(delay);
			}
			
			channel.close();
			conn.close();
			
		} catch (IOException | TimeoutException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public static void testServer(int numData, int delay)
	{
		ConnectionFactory factory = new ConnectionFactory();		
		
		try {
			Connection conn = factory.newConnection();
			Channel channel = conn.createChannel();
			
			for(int i=0; i<numData; i++)
			{
				channel.basicPublish(RabbitMqConstants.MQ2_EXCHANGE_NAME, RabbitMqConstants.MQ2_ROUTING_KEY, null, getChairMessageAsJson().getBytes());
				Thread.sleep(delay);
			}
			
			channel.close();
			conn.close();
			
		} catch (IOException | TimeoutException | InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void testDatabase(int numData, int delay)
	{
		DBProperties props = null;
		try {
			props = new DBProperties(false);
		    DBConnector connector = new DBConnector(props);
		    
		    for(int i = 0; i<numData; i++)
		    {
		    	ChairMessage msg = getChairMessage();
		    	if (!msg.getValues().isEmpty())
		    		connector.write(msg);
		    	
				Thread.sleep(delay);
		    }
		    
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	public static String getSensorMessageAsJson()
	{
		Random rng=new Random();
		ObjectMapper objectMapper = new ObjectMapper();
		List<Value> values = new LinkedList<>();
		for(int i = 0; i <rng.nextInt(MAXVALUESPERDATA)+1; i++)
		{
			Value value = new Value(valueId++,rng.nextInt(35));
			values.add(value);
		}
		int o=rng.nextInt(SensorType.values().length);
		SensorType type = SensorType.values()[o];
		SensorMessage msg = new SensorMessage(1,type, values,System.currentTimeMillis()+c*500);
		String json = "";
		try {
			json = objectMapper.writeValueAsString(msg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	public static String getChairMessageAsJson()
	{
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = "";
		try {
			json = objectMapper.writeValueAsString(getChairMessage());
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
