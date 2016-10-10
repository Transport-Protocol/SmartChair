package de.hawhamburg.sg.db;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class DBProperties {
	private Properties props=new Properties();
	private String filename;
	
	public DBProperties(String filename, boolean nothrow) throws IOException
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
	
	public DBProperties(boolean nothrow) throws IOException
	{
		this(DBConstants.PROPERTIES_FILENAME,nothrow);
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
	
	
	public String getMqHost()
	{
		return getOrInit(DBConstants.MQ_HOST_KEY, DBConstants.DEFVAL_MQ_HOST);
	}
	
	public int getMqPort()
	{
		return Integer.parseInt(getOrInit(DBConstants.MQ_PORT_KEY, DBConstants.DEFVAL_MQ_PORT));
	}
	
	public String getMqUser()
	{
		return getOrInit(DBConstants.MQ_USER_KEY, DBConstants.DEFVAL_MQ_USER);
	}
	
	public String getMqPassword()
	{
		return getOrInit(DBConstants.MQ_PASSW_KEY, DBConstants.DEFVAL_MQ_PASSW);
	}
	
	public String getDbHost()
	{
		return getOrInit(DBConstants.MQ_HOST_KEY, DBConstants.DEFVAL_MQ_HOST);
	}
	
	public int getDbPort()
	{
		return Integer.parseInt(getOrInit(DBConstants.MQ_PORT_KEY, DBConstants.DEFVAL_MQ_PORT));
	}
	
	public String getDbUser()
	{
		return getOrInit(DBConstants.MQ_USER_KEY, DBConstants.DEFVAL_MQ_USER);
	}
	
	public String getDbPassword()
	{
		return getOrInit(DBConstants.MQ_PASSW_KEY, DBConstants.DEFVAL_MQ_PASSW);
	}

	public String getDBName() {
		return getOrInit(DBConstants.DB_NAME_KEY, DBConstants.DEFVAL_DB_NAME);
	}
}
