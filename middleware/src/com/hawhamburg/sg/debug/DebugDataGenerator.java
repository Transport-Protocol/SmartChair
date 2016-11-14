package com.hawhamburg.sg.debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.AbstractValue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import de.hawhamburg.sg.db.DBConnector;
import de.hawhamburg.sg.db.DBProperties;
import com.hawhamburg.sg.mwrp.RabbitMqConstants;

public class DebugDataGenerator {
	
	private static final int STANDARDNUMDATA = 10;
	private static final int STANDARDDELAY = 1;
	private static final Map<SensorType, RandomDataGeneratorInterface> generatorMap = new HashMap<>();
	private static List<SensorType> TestDataType = new ArrayList<>(); //SensorTypes that will be generated
	
	static
	{
		generatorMap.put(SensorType.temperature, RandomDataGenerator::getTempData);
		generatorMap.put(SensorType.distance, RandomDataGenerator::getDistanceData);
		generatorMap.put(SensorType.pressure, RandomDataGenerator::getPressureData);
		generatorMap.put(SensorType.acceleration, RandomDataGenerator::getAccelerationData);
		generatorMap.put(SensorType.gyroscope, RandomDataGenerator::getGyroscopeData);
		generatorMap.put(SensorType.microphone, RandomDataGenerator::getMicrophoneData);
		generatorMap.put(SensorType.location, RandomDataGenerator::getLocationValueData);
	}
	
	public static void main(String[] args) 
	{
		String modus = "";
		int delay = STANDARDDELAY;
		int numData = STANDARDNUMDATA;
		boolean daemon = false;
		
		//Parse args
		for(int i = 0; i< args.length; i++)
		{
			if(args[i].equals("raspi") || args[i].equals("server") || args[i].equals("database"))
			{
				modus = args[i];
			}
			else if (args[i].equals("delay")) 
			{
				delay = Integer.parseInt(args[i+1]);
			}
			else if(args[i].equals("numData"))
			{
				numData = Integer.parseInt(args[i+1]);
			}
			else if(args[i].equals("daemon"))
			{
				daemon = true;
			}
			else if(args[i].equals("temperature") || args[i].equals("pressure") || 
					args[i].equals("distance") || args[i].equals("acceleration") || 
					args[i].equals("gyroscope") || args[i].equals("microphone") || 
					args[i].equals("location"))
			{
				TestDataType.add(SensorType.valueOf(args[i]));
			}
			else if(args[i].equals("help"))
			{
				printHelp();
				System.exit(0);
			}
			
		}
		
		//Standard: All SensorTypes
		if(TestDataType.isEmpty())
		{
			TestDataType.addAll(Arrays.asList(SensorType.values()));
		}
		
		if(modus.equals("raspi"))
		{
			do{
				testRasPi(numData, delay);
			}
			while(daemon);
		}
		else if(modus.equals("server"))
		{
			do {
			testServer(numData, delay);
			}
			while(daemon);
		}
		else if(modus.equals("database"))
		{
			do {
			testDatabase(numData, delay);
			}
			while(daemon);
		}
		else
		{
			printHelp();
		}
		
	}
	
	/*
	 * Connects to Queue1 and push numData Packages with delay ms.
	 * @param numData Count of Packages
	 * @param delay Time between Messages 
	 */
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
	
	/*
	 * Connects to Queue2 and push numData Packages with delay ms.
	 * @param numData Count of Packages
	 * @param delay Time between Messages
	 */
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
	
	/*
	 * Connects to database and push numData Packages with delay ms.
	 * @param numData Count of Packages
	 * @param delay Time between Messages
	 */
	public static void testDatabase(int numData, int delay)
	{
		DBProperties props = null;
		try {
			props = new DBProperties(false);
		    DBConnector connector = new DBConnector(props);
		    
		    for(int i = 0; i<numData; i++)
		    {
		    	ChairMessage<?> msg = getChairMessage();
		    	connector.write(msg);
		    	
				Thread.sleep(delay);
		    }
		    
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	}
	
	/*
	 * Generate a Random SensorMessage and returns it as a JSON formatted string.
	 * @return Random SensorMessage as JSON string
	 */
	public static String getSensorMessageAsJson()
	{
		ObjectMapper objectMapper = new ObjectMapper();
		Random rng=new Random();
		int o=rng.nextInt(TestDataType.size());
		SensorType type = TestDataType.get(o);
		List<AbstractValue> values = generatorMap.get(type).invoke();
		
		SensorMessage<?> msg = new SensorMessage<>(1,type, values,System.currentTimeMillis());
		String json = "";
		try {
			json = objectMapper.writeValueAsString(msg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
	
	/*
	 * Generate a Random ChairMessage and returns it as a JSON formatted string.
	 * @return Random ChairMessage as JSON string
	 */
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
	
	/*
	 * Generate a Random ChairMessage
	 * @return Random ChairMessage
	 */
	public static ChairMessage<?> getChairMessage()
	{
		Random rng=new Random();
		int o=rng.nextInt(TestDataType.size());
		SensorType type = TestDataType.get(o);
		
		List<AbstractValue> values = generatorMap.get(type).invoke();
		ChairMessage<?> msg = new ChairMessage<>("1", 1, type, values, System.currentTimeMillis());
		return msg;
	}
	
	/*
	 * Print Information about the DebugDataGenerator on a terminal.
	 */
	private static void printHelp()
	{
		System.out.println("Usage: java Myprogram MODUS [Options] || java Myprogram [Options] MODUS");
		System.out.println("MODUS: \n"
				+ "raspi			Push messages to the raspi queue(has to run on raspi)\n"
				+ "server			Push messages to the server queue(has to run on server)\n"
				+ "database			Push messages to the database(Configuration in properties file)");
		System.out.println("Options:\n"
				+ "delay NUMBER		Set the delay between the messages in ms\n"
				+ "numData NUMBER	Set the count of messages\n"
				+ "daemon			Runs DebugDataGenerator until the process got killed."
				+ "					Uses delay between messages and numData for messages per Connection");
	}


}
