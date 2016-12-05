package com.hawhamburg.sg.mwrp;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.io.IOException;

import com.hawhamburg.sg.data.ChairMessage;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.SensorValue;
import com.hawhamburg.sg.mwrp.gamectrl.GameController;
import com.hawhamburg.sg.mwrp.gamectrl.InputCharacter;
import com.hawhamburg.sg.mwrp.gamectrl.data.InputSignalPacket;

public final class SensorTypeBehavior 
{
	private static final Map<SensorType,SensorTypeBehaviorFunction> sensorMessageBehaviorMap=new HashMap<>();
	static
	{
		//sensorMessageBehaviorMap.put(SensorType.pressure, SensorTypeBehavior::pressureBehavior);
	}
	
	private static final SensorTypeBehaviorFunction defaultBehavior= (msg,properties,prov,pub,gameCtrl)->
	{
		try {
			if (pub!=null) {
				ChairMessage chm = new ChairMessage(properties.getChairId(), msg.getSensortype(), msg.getValues(),msg.getTimestamp());
				pub.publish(chm);
			}
			if(prov!=null)
				prov.addValues(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	};
	
	
	public static final SensorTypeBehaviorFunction getSensorTypeBehavior(SensorType sensorType)
	{
		SensorTypeBehaviorFunction stbf=sensorMessageBehaviorMap.get(sensorType);
		if(stbf==null)
			return defaultBehavior;
		return stbf;
	}
}
