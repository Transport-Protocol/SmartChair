package com.hawhamburg.sg.mwrp.gamectrl;

public enum GameControllerDevice {
	
	KEYBOARD_START(0x0100),

	LED_ORANGE(0x0200),
	LED_GREEN(0x0201),
	LED_RED(0x0202)
	;
	private short id;
	GameControllerDevice(int s)
	{
		id=(short)s;
	}
	
	public short getId()
	{
		return id;
	}
}
