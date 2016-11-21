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
import com.ullink.slack.simpleslackapi.SlackAttachment;
import com.ullink.slack.simpleslackapi.SlackChannel;
import com.ullink.slack.simpleslackapi.SlackPreparedMessage;
import com.ullink.slack.simpleslackapi.SlackSession;
import com.ullink.slack.simpleslackapi.impl.SlackSessionFactory;
import com.rabbitmq.client.AMQP.BasicProperties;

import de.hawhamburg.sg.db.DBConnector;
import de.hawhamburg.sg.db.DBConstants;
import de.hawhamburg.sg.db.DBProperties;

public class Slackconsumer implements Consumer{
	private Connection connection;
	private Channel channel;
	@SuppressWarnings("unused")
	private DBProperties properties;
	private ObjectMapper mapper = new ObjectMapper();
	
	SlackSession session;

	public Slackconsumer(DBProperties properties) {
		this.properties = properties;
		ConnectionFactory factory = new ConnectionFactory();
		
		session = SlackSessionFactory.createWebSocketSlackSession(properties.getSlackAuthToken());
        
	    
	    try {
	    	connection = factory.newConnection();
			channel = connection.createChannel();
			channel.basicConsume(RabbitMqConstants.SLACK_QUEUE_NAME, this);
			session.connect();
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
			sendMessageWithAuthor(msg.getMessage(), msg.getSender());
			if (msg.getImage() != null)
				uploadImage(msg.getImage(), msg.getImageName());
//			System.out.println(msg.toString());
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
	
	public void sendMessageToChannel(String message){
		SlackChannel channel = session.findChannelByName(DBConstants.DEFVAL_SLACK_CHANNEL);
		session.sendMessage(channel, message);
	}
	
	public void sendMessageWithAuthor(String message,String sender)
    {
        //get a channel
        SlackChannel channel = session.findChannelByName(DBConstants.DEFVAL_SLACK_CHANNEL);
        
        //build a message object
        SlackAttachment attachment = new SlackAttachment();
        attachment.setAuthorName(sender);
        attachment.setText(message);
        
        SlackPreparedMessage preparedMessage = new SlackPreparedMessage.Builder()
//                .withMessage(message)
                .withUnfurl(false)
                .addAttachment(attachment)
//                .addAttachment(new SlackAttachment())
                .build();

        session.sendMessage(channel, preparedMessage);
    }
	public void uploadImage(byte[] img, String filename) throws IOException{
		SlackChannel channel = session.findChannelByName(DBConstants.DEFVAL_SLACK_CHANNEL);
//		session.sendFile(channel, Files.readAllBytes(new File("8a281a9e3rlBH.png").toPath()), "test.png");
		session.sendFile(channel, img, "test.png");
	}
}
