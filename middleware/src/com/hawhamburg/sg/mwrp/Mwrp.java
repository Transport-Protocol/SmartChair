package com.hawhamburg.sg.mwrp;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.mwrp.gamectrl.GameController;
import com.hawhamburg.sg.mwrp.gui.MwrpFrame;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Mwrp {

	public static void main(String... args) throws IOException, TimeoutException {
		
		boolean useGui = false;
		boolean noServer = false;
		boolean gameController = false;
		
		for (String s : args) {
			switch (s) {
			case "-gui":
				useGui = true;
				break;
			case "-noserver":
				noServer = true;
				break;
			case "-gctrl":
				gameController = true;
				break;
			default:
				System.out.println("Unrecognized arg: " + s);
			}
		}
		MwrpProperties props = new MwrpProperties(true);

		System.out.println("DeviceId: " + props.getChairId());

		new Mwrp(props, useGui, noServer,gameController);
	}

	private Connection mq1Connection;
	private Connection mq2Connection;
	private Mq1Consumer mq1Consumer;
	private Mq2Publisher mq2Publisher;
	private GameController gameController;
	private MwrpProperties properties;
	private boolean useGui, winCtrl, noServer,useGameController;
	private MwrpFrame frame;
	private DataProvider dataProvider;

	private Mwrp(MwrpProperties props, boolean useGui, boolean noServer, boolean useGameController) throws IOException, TimeoutException {
		this.useGui = useGui;
		this.noServer = noServer;
		this.useGameController=useGameController;
		properties = props;
		ConnectionFactory factory = new ConnectionFactory();
		mq1Connection = factory.newConnection();
		mq1Consumer = new Mq1Consumer(mq1Connection);
		mq1Consumer.addMessageHandler(this::consume);
		mq1Consumer.start();
		if(useGui||useGameController)
			dataProvider=new DataProvider();
		
		if (useGui) {
			frame = new MwrpFrame(dataProvider);
			frame.setVisible(true);
		}
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
				this.noServer=noServer = true;
			}
		}
		if (!noServer) {
			mq2Publisher = new Mq2Publisher(mq2Connection);
			mq2Publisher.start();
		}
		
		if(useGameController)
		{
			System.out.println("Connecting to NodeMCU.");
			gameController=new GameController(dataProvider, "ESP_C41A0F",23);
			
			gameController.connect();
			mq1Consumer.addMessageHandler(gameController::sensorMessageReceived);
			
		}

	}

	private void consume(SensorMessage sensm) {
		
		SensorTypeBehavior.getSensorTypeBehavior(sensm.getSensortype()).invoke(sensm, properties, dataProvider, mq2Publisher, gameController);

	}
}
