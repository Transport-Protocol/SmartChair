package de.hawhamburg.sg.db;

import java.io.IOException;

import de.hawhamburg.sg.messenchair.ChatConsumer;
import de.hawhamburg.sg.messenchair.Slackconsumer;
import de.hawhamburg.sg.messenchair.Twitterconsumer;

public class Main {
	
	private static boolean twitterActivated = false;
	private static boolean slackActivated = false;
	private static boolean dbConnectorActivated = false;
	
	public static void main(String[] args) {
		
		DBProperties props = null;
		try {
			props = new DBProperties(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		for(int i = 0; i< args.length; i++)
		{
			if(args[i].equals("twitter"))
			{
				twitterActivated = true;
			}
			else if(args[i].equals("slack"))
			{
				slackActivated = true;
			}
			else if(args[i].equals("DBConnector"))
			{
				dbConnectorActivated = true;
			}
			else if(args[i].equals("help"))
			{
				printHelp();
				System.exit(0);
			}
			
		}
		
		if(twitterActivated)
		{
			new Twitterconsumer();		
		}
		else if(slackActivated)
		{
			new Slackconsumer(props);
		}
		else if(dbConnectorActivated)
		{
			DBConnector connector = new DBConnector(props);
		    new MqConsumer(connector);
		}
		else
		{
			DBConnector connector = new DBConnector(props);
		    new MqConsumer(connector);
		}
	    
	    if(twitterActivated || slackActivated)
	    {
	    	new ChatConsumer();
	    }
	}
	
	
	private static void printHelp()
	{
		System.out.println("Starts the DBConnector.\n"
				+ "No Parameters: Only the database modul starts. If you specify the moduls, only the specific moduls will start."
				+ "twitter: The twittermodul starts."
				+ "slack: The slackmodul starts."
				+ "DBConnect: the databasemodul starts.");
	}

}
