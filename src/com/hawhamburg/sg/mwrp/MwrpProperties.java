package com.hawhamburg.sg.mwrp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;
import java.util.UUID;

public class MwrpProperties {
	private Properties props=new Properties();
	private String filename;
	
	public MwrpProperties(String filename, boolean nothrow) throws IOException
	{
		if(filename==null)
			throw new IllegalArgumentException("filename cannot be null");
		
		try {
			props.load(Files.newInputStream(Paths.get(filename), StandardOpenOption.READ));
		} catch (IOException e) {
			if(!nothrow)
				throw e;
		}
		this.filename=filename;
	}
	
	public MwrpProperties(boolean nothrow) throws IOException
	{
		this(MwrpConstants.PROPERTIES_FILENAME,nothrow);
	}
	
	public void save() throws IOException
	{
		props.store(Files.newOutputStream(Paths.get(filename), StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.CREATE,StandardOpenOption.WRITE), "");
	}
	private String getOrInit(String key, String defaultVal)
	{
		String str=props.getProperty(key);
		if(str==null)
		{
			str=defaultVal;
			props.setProperty(key,str);
			try {
				save();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return str;
	}
	
	public String getChairId()
	{
		String str=props.getProperty(MwrpConstants.CHAIR_ID_KEY);
		if(str==null)
		{
			str=UUID.randomUUID().toString();
			props.setProperty(MwrpConstants.CHAIR_ID_KEY,str);
			try {
				save();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}
	
	public String getMqHost()
	{
		return getOrInit(MwrpConstants.MQ_HOST_KEY, MwrpConstants.DEFVAL_MQ_HOST);
	}
	
	public int getMqPort()
	{
		return Integer.parseInt(getOrInit(MwrpConstants.MQ_PORT_KEY, MwrpConstants.DEFVAL_MQ_PORT));
	}
	
	public String getMqUser()
	{
		return getOrInit(MwrpConstants.MQ_USER_KEY, MwrpConstants.DEFVAL_MQ_USER);
	}
	
	public String getMqPassword()
	{
		return getOrInit(MwrpConstants.MQ_PASSW_KEY, MwrpConstants.DEFVAL_MQ_PASSW);
	}
}
