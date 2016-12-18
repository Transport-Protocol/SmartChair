package de.hawhamburg.sg.messenchair;

import java.io.IOException;
import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.SensorValue;
import com.hawhamburg.sg.mwrp.RabbitMqConstants;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;

/**
 * ChatConsumer consumes a ChairMessage
 * and creates Messages for Twitter-/SlackConsumers
 */
public class ChatConsumer implements Consumer {
	
	private Connection connection;
	private Channel channel;
	private final ChatPublisher publisher;
	
	
	public ChatConsumer() {
		ConnectionFactory factory = new ConnectionFactory();
		
	    try {
		    connection = factory.newConnection();
			channel = connection.createChannel();
			channel.basicConsume(RabbitMqConstants.CHAT_QUEUE_NAME, this);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    publisher = new ChatPublisher(connection);
	}
	
	@Override
	public void handleConsumeOk(String consumerTag) {System.out.println("ConsumeOk: " + consumerTag);}
	@Override
	public void handleCancelOk(String consumerTag) {System.out.println("CancelOk: " + consumerTag);}
	@Override
	public void handleCancel(String consumerTag) throws IOException {System.out.println("Cancel: " + consumerTag);}

	@SuppressWarnings("unchecked")
	@Override
	public void handleDelivery(String arg0, Envelope arg1, BasicProperties arg2, byte[] arg3) throws IOException {
//		System.out.println("Delivery: " + arg0 + "; " + arg1 + "; " + new String(arg3, Charset.forName("UTF-8")));

		try {
			ChairMessage<?> msg = ChairMessage.parseJson(arg3);
//			System.out.println(msg.toString());
			//only pressure for twitter
			if ((!msg.getValues().isEmpty()) && (msg.getSensortype() == SensorType.pressure)){
				publisher.newMessage((ChairMessage<SensorValue>)msg);
			} else{
				
			}
				
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
