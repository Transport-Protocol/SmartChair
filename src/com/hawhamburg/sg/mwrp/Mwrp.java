package com.hawhamburg.sg.mwrp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.mwrp.gui.MwrpFrame;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Mwrp {

	public static void main(String... args) throws IOException, TimeoutException {
		boolean useGui = false;
		for (String s : args) {
			switch (s) {
			case "-gui":
				useGui = true;
				break;
			default:
				System.out.println("Unrecognized arg: " + s);
			}
		}
		MwrpProperties props = new MwrpProperties(true);

		System.out.println("DeviceId: " + props.getChairId());

		new Mwrp(props, useGui);
	}

	private Connection mq1Connection;
	private Connection mq2Connection;
	private Mq1Consumer mq1Consumer;
	private Mq2Publisher mq2Publisher;
	private MwrpProperties properties;
	private boolean useGui;

	private Mwrp(MwrpProperties props, boolean useGui) throws IOException, TimeoutException {
		this.useGui = useGui;
		if (useGui) {
			MwrpFrame frame = new MwrpFrame();
			frame.setVisible(true);
		}
		properties = props;
		ConnectionFactory factory = new ConnectionFactory();
		mq1Connection = factory.newConnection();

		ConnectionFactory factory2 = new ConnectionFactory();
		factory2.setHost(props.getMqHost());
		factory2.setPort(props.getMqPort());
		factory2.setUsername(props.getMqUser());
		factory2.setPassword(props.getMqPassword());
		mq2Connection = factory2.newConnection();

		mq1Consumer = new Mq1Consumer(mq1Connection);
		mq1Consumer.addMessageHandler(this::consume);
		mq1Consumer.start();

		mq2Publisher = new Mq2Publisher(mq2Connection);
		mq2Publisher.start();

	}

	private void consume(SensorMessage sensm) {
		ChairMessage chm = new ChairMessage(properties.getChairId(), sensm.getSensortype(), sensm.getValues());
		try {
			mq2Publisher.publish(chm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
