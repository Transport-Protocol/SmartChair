package com.hawhamburg.sg.mwrp.gamectrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum InputCharacter {
	SPACE		(' ',' '),
	SPAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACE(' ',' '),
	ARROW_UP	(38,0xDA),
	ARROW_DOWN	(40,0xD9),
	ARROW_LEFT	(37,0xD8),
	ARROW_RIGHT	(39,0xD7),
	

	LEFT_CTRL	(17,0x80),
	LEFT_SHIFT	(16,0x81),
	LEFT_ALT	(18,0x82),
	LEFT_GUI	(91,0x83),
	RIGHT_CTRL	(17,0x84),
	RIGHT_SHIFT	(16,0x85),
	RIGHT_ALT	(18,0x86),
	RIGHT_GUI	(92,0x87),
	BACKSPACE	(8,0xB2),
	TAB			(9,0xB3),
	RETURN		(13,0xB0),
	ESC			(27,0xB1),
	INSERT		(45,0xD1),
	DELETE		(46,0xD4),
	PAGE_UP		(33,0xD3),
	PAGE_DOWN	(34,0xD6),
	HOME		(36,0xD2),
	END			(35,0xD5),
	CAPS_LOCK	(20,0xC1),
	F1			(112,0xC2),
	F2			(113,0xC3),
	F3			(114,0xC4),
	F4			(115,0xC5),
	F5			(116,0xC6),
	F6			(117,0xC7),
	F7			(118,0xC8),
	F8			(119,0xC9),
	F9			(120,0xCA),
	F10			(121,0xCB),
	F11			(122,0xCC),
	F12			(123,0xCD),

	ONE		('1','1'),
	TWO		('2','2'),
	THREE	('3','3'),
	FOUR	('4','4'),
	FIVE	('5','5'),
	SIX		('6','6'),
	SEVEN	('7','7'),
	EIGHT	('8','8'),
	NINE	('9','9'),
	ZERO	('0','0');
	
	private char keyCode;
	private char arduinoCode;
	InputCharacter(char unicodePoint, char arduinoCode)
	{
		this.keyCode=unicodePoint;
		this.arduinoCode=arduinoCode;
	}
	InputCharacter(int unicodePoint, int arduinoCode)
	{
		this.keyCode=(char)unicodePoint;
		this.arduinoCode=(char)arduinoCode;
	}
	
	public char getKeyCode(){
		return keyCode;
	}
	
	public char getArduinoCode()
	{
		return arduinoCode;
	}
	
	///////////////////////

	private static final Map<Character, InputCharacter> charMap=new HashMap<>();
	private static final Map<Character, InputCharacter> arduinoMap=new HashMap<>();
	private static final Map<String, InputCharacter> nameMap=new HashMap<>();
	static
	{
		for(InputCharacter ch:values()){
			charMap.put(ch.keyCode, ch);
			arduinoMap.put(ch.arduinoCode, ch);
			nameMap.put(ch.name(), ch);
		}
		
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
	
	public static char keycodeToArduino(char kc)
	{
		InputCharacter ic=charMap.get(kc);
		return Character.toLowerCase(ic==null?kc:ic.arduinoCode);
	}
	
	public static char arduinoToKeycode(char ard)
	{
		InputCharacter ic=arduinoMap.get(ard);
		return ic==null?ard:ic.keyCode;
	}
	
	public static InputCharacter getCharByKeycode(char str)
	{
		return charMap.get(str);
	}
	
	public static InputCharacter getCharByArduinoCode(char str)
	{
		return arduinoMap.get(str);
	}
	
	public static InputCharacter getOrNull(String name)
	{
		return nameMap.get(name);
	}
}
