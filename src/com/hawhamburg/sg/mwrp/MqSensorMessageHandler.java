package com.hawhamburg.sg.mwrp;

import com.hawhamburg.sg.data.SensorMessage;

@FunctionalInterface
public interface MqSensorMessageHandler {
	public void messageReceived(SensorMessage message);
}
