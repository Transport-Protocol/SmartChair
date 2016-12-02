package com.hawhamburg.sg.mwrp.gamectrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import static com.hawhamburg.sg.mwrp.gamectrl.GameControllerPropertiesConstants.*;

public class GameControllerProperties {

	private char keyForward;
	private char keyBackward;
	private char keyLeft;
	private char keyRight;
	private char keyHop;
	private int fbThreshold;
	private int swThreshold;
	private String gcHost;
	private int gcPort;
	
	private Properties properties=new Properties();
	public GameControllerProperties()
	{
		
	}
	
	public void readFromFile(Path filename) throws IOException
	{
		Properties props=new Properties();
		props.load(Files.newInputStream(filename,StandardOpenOption.READ));

		keyForward=getKeycode(props, KEY_KEY_FORWARD, DEFVAL_KEY_FORWARD);
		keyBackward=getKeycode(props, KEY_KEY_BACKWARD, DEFVAL_KEY_BACKWARD);
		keyLeft=getKeycode(props, KEY_KEY_LEFT, DEFVAL_KEY_LEFT);
		keyRight=getKeycode(props, KEY_KEY_RIGHT, DEFVAL_KEY_RIGHT);
		keyHop=getKeycode(props, KEY_KEY_HOP, DEFVAL_KEY_HOP);
		
		fbThreshold=Integer.parseInt(getOrInit(props,KEY_DIRECTION_CONTROLS_FORWARD_AND_BACKWARD_THRESHOLD_VALUE,DEFVAL_DIRECTION_CONTROLS_FORWARD_AND_BACKWARD_THRESHOLD_VALUE));
		swThreshold=Integer.parseInt(getOrInit(props,KEY_DIRECTION_CONTROLS_SIDEWAYS_THRESHOLD_VALUE,DEFVAL_DIRECTION_CONTROLS_SIDEWAYS_THRESHOLD_VALUE));

		gcHost=getOrInit(props, KEY_GAME_CONTROLLER_HOST, DEFVAL_GAME_CONTROLLER_HOST);
		gcPort=Integer.parseInt(getOrInit(props, KEY_GAME_CONTROLLER_PORT, DEFVAL_GAME_CONTROLLER_PORT));
		
		props.store(Files.newOutputStream(filename, StandardOpenOption.WRITE,StandardOpenOption.CREATE,StandardOpenOption.TRUNCATE_EXISTING),"");
	}
	private String getOrInit(Properties props,String key,String defVal)
	{
		if(props.containsKey(key))
		{
			return props.getProperty(key);
		}
		props.put(key, defVal);
		return defVal;
	}
	
	private char getKeycode(Properties props, String key,String defVal)
	{
		String str=getOrInit(props, key, defVal).trim();
		if(str.length()==1)
			return str.charAt(0);
		
		if(str.toLowerCase().startsWith("0x"))
			return (char)Integer.parseInt(str.substring(2),16);
		
		InputCharacter ch=InputCharacter.getOrNull(str);
		if(ch!=null)
			return ch.getArduinoCode();
		return (char)Integer.parseInt(str);
	}

	public char getKeyForward() {
		return keyForward;
	}

	public void setKeyForward(char keyForward) {
		this.keyForward = keyForward;
	}

	public char getKeyBackward() {
		return keyBackward;
	}

	public void setKeyBackward(char keyBackward) {
		this.keyBackward = keyBackward;
	}

	public char getKeyLeft() {
		return keyLeft;
	}

	public void setKeyLeft(char keyLeft) {
		this.keyLeft = keyLeft;
	}

	public char getKeyRight() {
		return keyRight;
	}

	public void setKeyRight(char keyRight) {
		this.keyRight = keyRight;
	}

	public char getKeyHop() {
		return keyHop;
	}

	public void setKeyHop(char keyHop) {
		this.keyHop = keyHop;
	}

	public int getFbThreshold() {
		return fbThreshold;
	}

	public void setFbThreshold(int fbThreshold) {
		this.fbThreshold = fbThreshold;
	}

	public int getSwThreshold() {
		return swThreshold;
	}

	public void setSwThreshold(int swThreshold) {
		this.swThreshold = swThreshold;
	}

	public String getGcHost() {
		return gcHost;
	}

	public void setGcHost(String gcHost) {
		this.gcHost = gcHost;
	}

	public int getGcPort() {
		return gcPort;
	}

	public void setGcPort(int gcPort) {
		this.gcPort = gcPort;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}

class GameControllerPropertiesConstants
{
	static final String KEY_KEY_FORWARD="keyForward";
	static final String KEY_KEY_BACKWARD="keyBackward";
	static final String KEY_KEY_LEFT="keyLeft";
	static final String KEY_KEY_RIGHT="keyRight";
	static final String KEY_DIRECTION_CONTROLS_SIDEWAYS_THRESHOLD_VALUE="directionControlsSwThresholdValue";
	static final String KEY_DIRECTION_CONTROLS_FORWARD_AND_BACKWARD_THRESHOLD_VALUE="directionControlsFbThresholdValue";
	static final String KEY_GAME_CONTROLLER_HOST="gcHost";
	static final String KEY_GAME_CONTROLLER_PORT="gcPort";
	static final String KEY_KEY_HOP="keyHop";
	
	static final String DEFVAL_KEY_FORWARD="ARROW_UP";
	static final String DEFVAL_KEY_BACKWARD="ARROW_DOWN";
	static final String DEFVAL_KEY_LEFT="ARROW_LEFT";
	static final String DEFVAL_KEY_RIGHT="ARROW_RIGHT";
	static final String DEFVAL_DIRECTION_CONTROLS_SIDEWAYS_THRESHOLD_VALUE="150";
	static final String DEFVAL_DIRECTION_CONTROLS_FORWARD_AND_BACKWARD_THRESHOLD_VALUE="150";
	static final String DEFVAL_GAME_CONTROLLER_HOST="127.0.0.1";
	static final String DEFVAL_GAME_CONTROLLER_PORT="6524";
	static final String DEFVAL_KEY_HOP="SPAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACE";
	
	
}