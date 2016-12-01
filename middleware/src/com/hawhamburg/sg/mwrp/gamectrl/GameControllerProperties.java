package com.hawhamburg.sg.mwrp.gamectrl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class GameControllerProperties {
	private char keyForward;
	private char keyBackward;
	private char keyLeft;
	private char keyRight;
	private int fbThreshold;
	private int swThreshold;
	private String gcHost;
	private int gcPort;
	
	public GameControllerProperties()
	{
		
	}
	
	public void readFormFile(Path filename) throws IOException
	{
		Properties props=new Properties();
		props.load(Files.newInputStream(filename,StandardOpenOption.READ));
		
		keyForward=getKeycode("keyForward");
	}
	
	private char getKeycode(String key)
	{
		return 0;
	}
}
