package com.hawhamburg.sg.mwrp;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawhamburg.sg.data.ChairMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Mq2Publisher 
{
	private Connection connection;
	private Channel channel;
	private ObjectMapper serializer=new ObjectMapper();
	public Mq2Publisher(Connection connection)
	{
		this.connection=connection;
	}
	
	public void start() throws IOException
	{
		channel=connection.createChannel();
	}
	
	public void publish(ChairMessage msg) throws IOException
	{
		channel.basicPublish(RabbitMqConstants.MQ2_EXCHANGE_NAME, RabbitMqConstants.MQ2_ROUTING_KEY,null, serializer.writeValueAsBytes(msg));
	}
}
