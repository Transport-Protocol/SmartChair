package com.hawhamburg.sg.mwrp;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

public class Mq1Consumer implements Consumer {
	private Connection connection;
	private Channel channel;

	private List<MqSensorMessageHandler> msgHandlers;
	private ObjectMapper mapper = new ObjectMapper();

	public Mq1Consumer(Connection connection) {
		msgHandlers = new LinkedList<>();
		this.connection = connection;
	}

	public void start() throws IOException {
		channel = connection.createChannel();
		channel.basicConsume(RabbitMqConstants.MQ1_QUEUE_NAME, this);
	}

	public void addMessageHandler(MqSensorMessageHandler handler) {
		msgHandlers.add(handler);
	}

	@Override
	public void handleConsumeOk(String consumerTag) {
		System.out.println("ConsumeOk: " + consumerTag);

	}

	@Override
	public void handleCancelOk(String consumerTag) {
		System.out.println("CancelOk: " + consumerTag);

	}

	@Override
	public void handleCancel(String consumerTag) throws IOException {
		System.out.println("Cancel: " + consumerTag);

	}
	private long lastTime=0;
	@Override
	public void handleDelivery(String arg0, Envelope arg1, BasicProperties arg2, byte[] arg3) throws IOException {
		//System.out.println("Delivery: " + arg0 + "; " + arg1 + "; " + new String(arg3, Charset.forName("UTF-8")));

		try {
			SensorMessage msg = SensorMessage.parseJson(arg3);
			for (int i = 0; i < msgHandlers.size(); i++)
				msgHandlers.get(i).messageReceived(msg);
			channel.basicAck(arg1.getDeliveryTag(), false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
		System.out.println("Shutdown: " + consumerTag);
		System.out.println(sig);

	}

	@Override
	public void handleRecoverOk(String consumerTag) {
		System.out.println("RecoverOk: " + consumerTag);
	}

}
