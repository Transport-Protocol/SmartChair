package de.hawhamburg.sg.messenchair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ChairFileHandler {
	
	private static Path file = Paths.get("counter.txt");
	private static DataInputStream fr;
	private static DataOutputStream fw;
	
	static {
		
		if(!Files.exists(file))
		{
			try(DataOutputStream fw=new DataOutputStream(Files.newOutputStream(file, StandardOpenOption.CREATE))) {
				
				
				fw.writeUTF("chair1:0\n");
				fw.writeUTF("chair2:0\n");
				
				fw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}				
	}
	
	
	static void writeCounter(int chairNumber, int counter)
	{
		String chair1 = null;
		String chair2 = null;
		
		try(DataInputStream fr = new DataInputStream(Files.newInputStream(file)))
		{
			chair1 = fr.readLine();
			chair2 = fr.readLine();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		try {
			Files.delete(file);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try(DataOutputStream fw = new DataOutputStream(Files.newOutputStream(file, StandardOpenOption.CREATE))) {
			
			
			if(chairNumber == 1)
			{
				fw.writeUTF("chair1:" + counter);
				fw.writeUTF("chair2:" + chair2.charAt(chair2.length()-2));
			}
			else if(chairNumber == 2)
			{
				fw.writeUTF("chair1:" + chair1.charAt(chair1.length()-2));
				fw.writeUTF("chair2:" + counter);
			}
			fw.flush();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static int readCounter(int chairNumber)
	{
		String chair1 = "";
		String chair2 = "";
		try(DataInputStream fr = new DataInputStream(Files.newInputStream(file))) {
			chair1 = fr.readLine();
			chair2 = fr.readLine();			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(chairNumber == 1)
		{
			return Integer.parseInt(Character.toString(chair1.charAt(chair1.length()-1)));
		}
		else if(chairNumber == 2)
		{
			return Integer.parseInt(Character.toString(chair2.charAt(chair2.length()-1)));
		}
		
		return -1;
	}
	
	

}
