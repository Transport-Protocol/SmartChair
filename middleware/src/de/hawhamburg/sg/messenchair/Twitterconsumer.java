package de.hawhamburg.sg.messenchair;

import java.io.IOException;
import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hawhamburg.sg.data.ChatMessage;
import com.hawhamburg.sg.mwrp.RabbitMqConstants;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Twitterconsumer implements Consumer{
	private Connection connection;
	private Channel channel;

	private ObjectMapper mapper = new ObjectMapper();

	public Twitterconsumer() {
		
		ConnectionFactory factory = new ConnectionFactory();
	    
	    try {
		    connection = factory.newConnection();
			channel = connection.createChannel();
			channel.basicConsume(RabbitMqConstants.TWITTER_QUEUE_NAME, this);
		} catch (Exception e) {e.printStackTrace();}
	}

	@Override
	public void handleConsumeOk(String consumerTag) {System.out.println("ConsumeOk: " + consumerTag);}
	@Override
	public void handleCancelOk(String consumerTag) {System.out.println("CancelOk: " + consumerTag);}
	@Override
	public void handleCancel(String consumerTag) throws IOException {System.out.println("Cancel: " + consumerTag);}

	@Override
	public void handleDelivery(String arg0, Envelope arg1, BasicProperties arg2, byte[] arg3) throws IOException {
		System.out.println("Delivery: " + arg0 + "; " + arg1 + "; " + new String(arg3, Charset.forName("UTF-8")));

		try {
			ChatMessage msg = mapper.readValue(arg3, ChatMessage.class);
			if(msg.getImage() != null)
				postToTwitter(msg.getMessage(),msg.getImageName(),msg.getImage());
			else postToTwitter(msg.getMessage());
			
//			System.out.println(msg);
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
	
	public void postToTwitter(String message) throws TwitterException{
		Twitter twitter = TwitterFactory.getSingleton();
		@SuppressWarnings("unused")
		Status status = twitter.updateStatus(message);
//		System.out.println(status.getText());
	}
	public void postToTwitter(String message, String imageName, byte[] image) throws TwitterException{
		Twitter twitter = TwitterFactory.getSingleton();
		@SuppressWarnings("unused")
		Status status = twitter.updateStatus(message);
//		System.out.println(status.getText());
	}

}
