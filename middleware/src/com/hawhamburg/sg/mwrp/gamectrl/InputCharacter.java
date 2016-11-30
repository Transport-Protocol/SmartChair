package com.hawhamburg.sg.mwrp.gamectrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum InputCharacter {
	SPACE		(' ',' '),
	ARROW_UP	('↑',(char)0xDA),
	ARROW_DOWN	('↓',(char)0xD9),
	ARROW_LEFT	('←',(char)0xD8),
	ARROW_RIGHT	('→',(char)0xD7);
	
	private char unicodePoint;
	private char arduinoCode;
	InputCharacter(char unicodePoint, char arduinoCode)
	{
		this.unicodePoint=unicodePoint;
		this.arduinoCode=arduinoCode;
	}
	
	public char getUnicodePoint(){
		return unicodePoint;
	}
	
	public char getArduinoCode()
	{
		return arduinoCode;
	}
	
	///////////////////////
	
	private static final Map<Character, InputCharacter> charMap=new HashMap<>();
	static
	{
		for(InputCharacter ch:values())
			charMap.put(ch.unicodePoint, ch);
		
	}
	
	public static InputCharacter[] getChars(String str)
	{
		InputCharacter[] ic=new InputCharacter[str.length()];
		for(int i=0;i<ic.length;i++)
		{
			ic[i]=charMap.get(str.charAt(i));
		}
		return ic;
	}
}
