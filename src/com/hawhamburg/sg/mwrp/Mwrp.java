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
		boolean noServer = false;
		
		for (String s : args) {
			switch (s) {
			case "-gui":
				useGui = true;
				break;
			case "-noserver":
				noServer = true;
				break;
			default:
				System.out.println("Unrecognized arg: " + s);
			}
		}
		MwrpProperties props = new MwrpProperties(true);

		System.out.println("DeviceId: " + props.getChairId());

		new Mwrp(props, useGui, noServer);
	}

	private Connection mq1Connection;
	private Connection mq2Connection;
	private Mq1Consumer mq1Consumer;
	private Mq2Publisher mq2Publisher;
	private MwrpProperties properties;
	private boolean useGui, winCtrl, noServer;
	private MwrpFrame frame;

	private Mwrp(MwrpProperties props, boolean useGui, boolean noServer) throws IOException, TimeoutException {
		this.useGui = useGui;
		this.noServer = noServer;
		if (useGui) {
			frame = new MwrpFrame();
			frame.setVisible(true);
		}
		properties = props;
		ConnectionFactory factory = new ConnectionFactory();
		mq1Connection = factory.newConnection();
		if (!noServer) {
			ConnectionFactory factory2 = new ConnectionFactory();
			factory2.setHost(props.getMqHost());
			factory2.setPort(props.getMqPort());
			factory2.setUsername(props.getMqUser());
			factory2.setPassword(props.getMqPassword());
			try {
				mq2Connection = factory2.newConnection();
			} catch (IOException e) {
				System.err.println("Unable to Connect to remote server, continuing without sending data to server.");
				noServer = true;
			}
		}
		mq1Consumer = new Mq1Consumer(mq1Connection);
		mq1Consumer.addMessageHandler(this::consume);
		mq1Consumer.start();
		if (!noServer) {
			mq2Publisher = new Mq2Publisher(mq2Connection);
			mq2Publisher.start();
		}

	}

	private void consume(SensorMessage sensm) {
		ChairMessage chm = new ChairMessage(properties.getChairId(), sensm.getSensortype(), sensm.getValues());
		try {
			if (!noServer) {
				mq2Publisher.publish(chm);
			}
			if (useGui) {
				switch (sensm.getSensortype()) {
				case temperature:
					frame.setTemperatureValue(sensm.getValues().get(0).getValue());
					break;
				case pressure:
					frame.setPressureValues(sensm.getValues());
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
