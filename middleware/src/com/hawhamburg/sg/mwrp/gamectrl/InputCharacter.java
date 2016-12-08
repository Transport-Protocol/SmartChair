package com.hawhamburg.sg.mwrp.gamectrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum InputCharacter {/*
	BACKSPACE	(8,0xB2),
	TAB			(9,0xB3),
	RETURN		(13,0xB0),
	LEFT_CTRL	(17,0x80),
	LEFT_SHIFT	(16,0x81),
	LEFT_ALT	(18,0x82),
	RIGHT_CTRL	(17,0x84),
	RIGHT_SHIFT	(16,0x85),
	RIGHT_ALT	(18,0x86),
	PAUSE		(19,19+136),
	CAPS_LOCK	(20,0xC1),
	ESC			(27,0xB1),
	SPACE		(' ',' '),
	SPAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACE(' ',' '),
	PAGE_UP		(33,0xD3),
	PAGE_DOWN	(34,0xD6),
	END			(35,0xD5),
	HOME		(36,0xD2),
	ARROW_LEFT	(37,0xD8),
	ARROW_UP	(38,0xDA),
	ARROW_RIGHT	(39,0xD7),
	ARROW_DOWN	(40,0xD9),
	INSERT		(45,0xD1),
	DELETE		(46,0xD4),
	ZERO		('0',0x27+0x88),
	ONE			('1',0x1E+0x88),
	TWO			('2',0x1F+0x88),
	THREE		('3',0x20+0x88),
	FOUR		('4',0x21+0x88),
	FIVE		('5',0x22+0x88),
	SIX			('6',0x23+0x88),
	SEVEN		('7',0x24+0x88),
	EIGHT		('8',0x25+0x88),
	NINE		('9',0x26+0x88),
	a			('a','a'),
	b			('b','b'),
	c			('c','c'),
	d			('d','d'),
	e			('e','e'),
	f			('f','f'),
	g			('g','g'),
	h			('h','h'),
	i			('i','i'),
	j			('j','j'),
	k			('k','k'),
	l			('l','l'),
	m			('m','m'),
	n			('n','n'),
	o			('o','o'),
	p			('p','p'),
	q			('q','q'),
	r			('r','r'),
	s			('s','s'),
	t			('t','t'),
	u			('u','u'),
	v			('v','v'),
	w			('w','w'),
	x			('x','x'),
	y			('y','y'),
	z			('z','z'),
	A			('A','a'),
	B			('B','b'),
	C			('C','c'),
	D			('D','d'),
	E			('E','e'),
	F			('F','f'),
	G			('G','g'),
	H			('H','h'),
	I			('I','i'),
	J			('J','j'),
	K			('K','k'),
	L			('L','l'),
	M			('M','m'),
	N			('N','n'),
	O			('O','o'),
	P			('P','p'),
	Q			('Q','q'),
	R			('R','r'),
	S			('S','s'),
	T			('T','t'),
	U			('U','u'),
	V			('V','v'),
	W			('W','w'),
	X			('X','x'),
	Y			('Y','y'),
	Z			('Z','z'),
	LEFT_GUI	(91,0x83),
	RIGHT_GUI	(92,0x87),
	SELECT		(93,93+136),//???
	
	NUMPAD0		(96,234),
	NUMPAD1		(97,225),
	NUMPAD2		(98,226),
	NUMPAD3		(99,227),
	NUMPAD4		(100,228),
	NUMPAD5		(101,229),
	NUMPAD6		(102,230),
	NUMPAD7		(103,231),
	NUMPAD8		(104,232),
	NUMPAD9		(105,233),
	NUMPAD_MULTIPLY		(106,221),
	NUMPAD_ADD			(107,223),
	NUMPAD_SUBTRACT		(109,224),
	NUMPAD_DECIMAL_POINT(110,235),
	NUMPAD_DIVIDE		(111,220),
	
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
	F12			(123,0xCD),*/
	;

	
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
		if(kc<136)
			return (char)((kc+136)&0xFF);
		return (char)((kc-61)&0xFF);
	}
	
	public static char arduinoToKeycode(char ard)
	{
		if(ard>=136)
			return (char)((ard-136)&0xFF);
		return (char)((ard+61)&0xFF);
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
