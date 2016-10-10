package de.hawhamburg.sg.db;

public final class DBConstants {
	private DBConstants(){throw new RuntimeException();}

	public static final String PROPERTIES_FILENAME="mwrp.properties";

	public static final String MQ_HOST_KEY="mqHost";
	public static final String MQ_PORT_KEY="mqPort";
	public static final String MQ_USER_KEY="mqUser";
	public static final String MQ_PASSW_KEY="mqPassw";

	public static final String DB_HOST_KEY="mqHost";
	public static final String DB_PORT_KEY="mqPort";
	public static final String DB_USER_KEY="mqUser";
	public static final String DB_PASSW_KEY="mqPassw";
	public static final String DB_NAME_KEY="dbName";
	
	public static final String DEFVAL_MQ_HOST="localhost";
	public static final String DEFVAL_MQ_PORT="5672";
	public static final String DEFVAL_MQ_USER="guest";
	public static final String DEFVAL_MQ_PASSW="guest";
	
	public static final String DEFVAL_DB_HOST="localhost";
	public static final String DEFVAL_DB_PORT="8086";
	public static final String DEFVAL_DB_USER="guest";
	public static final String DEFVAL_DB_PASSW="guest";
	public static final String DEFVAL_DB_NAME = "clarc_sensor";
}
