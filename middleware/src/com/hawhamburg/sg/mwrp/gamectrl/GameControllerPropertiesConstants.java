package com.hawhamburg.sg.mwrp.gamectrl;

import java.util.HashMap;
import java.util.Map;

public class GameControllerPropertiesConstants
{
	public static final String KEY_KEY_FORWARD="keyForward";
	public static final String KEY_KEY_BACKWARD="keyBackward";
	public static final String KEY_KEY_LEFT="keyLeft";
	public static final String KEY_KEY_RIGHT="keyRight";
	public static final String KEY_DIRECTION_CONTROLS_SIDEWAYS_THRESHOLD_VALUE="directionControlsSwThresholdValue";
	public static final String KEY_DIRECTION_CONTROLS_FORWARD_AND_BACKWARD_THRESHOLD_VALUE="directionControlsFbThresholdValue";
	public static final String KEY_GAME_CONTROLLER_HOST="gcHost";
	public static final String KEY_GAME_CONTROLLER_PORT="gcPort";
	public static final String KEY_KEY_HOP="keyHop";

	public static final String NAME_KEY_FORWARD="Forward";
	public static final String NAME_KEY_BACKWARD="Backward";
	public static final String NAME_KEY_LEFT="Left";
	public static final String NAME_KEY_RIGHT="Right";
	public static final String NAME_KEY_HOP="Jump";
	
	public static final Map<String,String> MAPPING_KEY_NAME=new HashMap<>();
	static
	{
		MAPPING_KEY_NAME.put(KEY_KEY_FORWARD, NAME_KEY_FORWARD);
		MAPPING_KEY_NAME.put(KEY_KEY_BACKWARD, NAME_KEY_BACKWARD);
		MAPPING_KEY_NAME.put(KEY_KEY_LEFT, NAME_KEY_LEFT);
		MAPPING_KEY_NAME.put(KEY_KEY_RIGHT, NAME_KEY_RIGHT);
		MAPPING_KEY_NAME.put(KEY_KEY_HOP, NAME_KEY_HOP);
	}
	
	public static final String DEFVAL_KEY_FORWARD="ARROW_UP";
	public static final String DEFVAL_KEY_BACKWARD="ARROW_DOWN";
	public static final String DEFVAL_KEY_LEFT="ARROW_LEFT";
	public static final String DEFVAL_KEY_RIGHT="ARROW_RIGHT";
	public static final String DEFVAL_DIRECTION_CONTROLS_SIDEWAYS_THRESHOLD_VALUE="150";
	public static final String DEFVAL_DIRECTION_CONTROLS_FORWARD_AND_BACKWARD_THRESHOLD_VALUE="150";
	public static final String DEFVAL_GAME_CONTROLLER_HOST="127.0.0.1";
	public static final String DEFVAL_GAME_CONTROLLER_PORT="6524";
	public static final String DEFVAL_KEY_HOP="SPAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACE";
	
	
}
