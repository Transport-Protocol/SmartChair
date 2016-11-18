package com.hawhamburg.sg.mwrp;

public final class RabbitMqConstants {
	private RabbitMqConstants(){throw new RuntimeException();}

	public static final String MQ1_QUEUE_NAME="sg.q.sensor_values";
	public static final String MQ1_EXCHANGE_NAME="sg.ex.sensor_values";
	public static final String MQ1_ROUTING_KEY="sg.rk.sensor_values";
	
	public static final String MQ2_QUEUE_NAME="sg.q.ss";
	public static final String MQ2_EXCHANGE_NAME="sg.ex.ss";
	public static final String MQ2_ROUTING_KEY="sg.rk.ss";
	
	public static final String CHAT_EXCHANGE_NAME="sg.ex.chat";
	public static final String TWITTER_QUEUE_NAME="sg.q.twitter";
	public static final String TWITTER_ROUTING_KEY="sg.rk.twitter";
	public static final String SLACK_QUEUE_NAME = "sg.q.slack";
	public static final String SLACK_ROUTING_KEY="sg.rk.slack";
}
