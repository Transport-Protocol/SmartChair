package com.hawhamburg.sg.mwrp;

import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.mwrp.gamectrl.GameController;

@FunctionalInterface
public interface SensorTypeBehaviorFunction {
	public void invoke(SensorMessage smg, MwrpProperties properties, DataProvider dataProvider, Mq2Publisher publisher, GameController gameCtrl);
}
