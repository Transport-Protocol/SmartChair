package de.hawhamburg.sg.db;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class DBProperties {
	private Properties props=new Properties();
	private String filename;
	
	public DBProperties(String filename, boolean nothrow) throws IOException{
		if(filename==null)
			throw new IllegalArgumentException("filename cannot be null");
		this.filename=filename;
		try {
			File f = new File(filename);
			if(f.exists() && !f.isDirectory()){
				props.load(Files.newInputStream(f.toPath(), StandardOpenOption.READ));
			} else {
				initializePorpertiesFile();
			}
		} catch (IOException e) {
			if(!nothrow)
				throw e;
		}
		
	}
	
	public DBProperties(boolean nothrow) throws IOException{
		this(DBConstants.PROPERTIES_FILENAME,nothrow);
	}
	
	public void save() throws IOException{
		props.store(Files.newOutputStream(Paths.get(filename), StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.CREATE,StandardOpenOption.WRITE), "");
	}
	
	private String getOrInit(String key, String defaultVal){
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
	
	
	public String getMqHost(){return getOrInit(DBConstants.MQ_HOST_KEY, DBConstants.DEFVAL_MQ_HOST);}
	
	public int getMqPort(){return Integer.parseInt(getOrInit(DBConstants.MQ_PORT_KEY, DBConstants.DEFVAL_MQ_PORT));}
	
	public String getMqUser(){return getOrInit(DBConstants.MQ_USER_KEY, DBConstants.DEFVAL_MQ_USER);}
	
	public String getMqPassword(){return getOrInit(DBConstants.MQ_PASSW_KEY, DBConstants.DEFVAL_MQ_PASSW);}
	
	public String getDbHost(){return getOrInit(DBConstants.DB_HOST_KEY, DBConstants.DEFVAL_DB_HOST);}
	
	public int getDbPort(){return Integer.parseInt(getOrInit(DBConstants.DB_PORT_KEY, DBConstants.DEFVAL_DB_PORT));}
	
	public String getDbUser(){return getOrInit(DBConstants.DB_USER_KEY, DBConstants.DEFVAL_DB_USER);}
	
	public String getDbPassword(){return getOrInit(DBConstants.DB_PASSW_KEY, DBConstants.DEFVAL_DB_PASSW);}

	public String getDBName() {return getOrInit(DBConstants.DB_NAME_KEY, DBConstants.DEFVAL_DB_NAME);}
	
	public void initializePorpertiesFile() throws IOException{
		for (Field f : DBConstants.class.getDeclaredFields()){
			if (f.getName().endsWith("_KEY")){
				try {
					String defaultValue = (String) DBConstants.class.getDeclaredField("DEFVAL_" + f.getName().split("_KEY")[0]).get(getClass());
					props.setProperty((String) f.get(getClass()),defaultValue);
				} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {e.printStackTrace();}
				
			}
		}
		save();
		System.out.println("Had to initialize the .properties-file, please check the values");
		System.exit(42);
	}
	
}
