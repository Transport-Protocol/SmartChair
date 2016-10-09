package com.hawhamburg.sg.mwrp;

public final class MwrpConstants {
	private MwrpConstants(){throw new RuntimeException();}

	public static final String PROPERTIES_FILENAME="mwrp.properties";

	public static final String CHAIR_ID_KEY="chairId";
	public static final String MQ_HOST_KEY="mqHost";
	public static final String MQ_PORT_KEY="mqPort";
	public static final String MQ_USER_KEY="mqUser";
	public static final String MQ_PASSW_KEY="mqPassw";

	public static final String DEFVAL_MQ_HOST="localhost";
	public static final String DEFVAL_MQ_PORT="5672";
	public static final String DEFVAL_MQ_USER="guest";
	public static final String DEFVAL_MQ_PASSW="guest";
}
