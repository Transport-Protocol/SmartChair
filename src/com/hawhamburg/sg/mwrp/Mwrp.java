package com.hawhamburg.sg.mwrp;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.SensorMessage;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Mwrp {

	public static void main(String... args) throws IOException, TimeoutException {
		
		MwrpProperties props=new MwrpProperties(true);
		
		System.out.println(props.getChairId());
	    new Mwrp(props);
	}

	private Connection mq1Connection;
	private Connection mq2Connection;
	private Mq1Consumer mq1Consumer;
	private Mq2Publisher mq2Publisher;
	private MwrpProperties properties;
	
	private Mwrp(MwrpProperties props) throws IOException, TimeoutException
	{
		properties=props;
		ConnectionFactory factory = new ConnectionFactory();
	    mq1Connection = factory.newConnection();
	    
		ConnectionFactory factory2 = new ConnectionFactory();
		factory2.setHost(props.getMqHost());
		factory2.setPort(props.getMqPort());
		factory2.setUsername(props.getMqUser());
		factory2.setPassword(props.getMqPassword());
	    mq2Connection = factory2.newConnection();
	    
	    mq1Consumer=new Mq1Consumer(mq1Connection);
	    mq1Consumer.addMessageHandler(this::consume);
	    mq1Consumer.start();
	    
	    mq2Publisher=new Mq2Publisher(mq2Connection);
	    mq2Publisher.start();
	    
	}
	
	private void consume(SensorMessage sensm)
	{
		ChairMessage chm=new ChairMessage(properties.getChairId(), sensm.getSensortype(),sensm.getValues());
		try {
			mq2Publisher.publish(chm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
