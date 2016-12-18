package com.hawhamburg.sg.mwrp.gamectrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum InputCharacter {//*
	BACKSPACE	(8,0x2a),
	TAB			(9,0x2b),
	RETURN		(13,0x28),
	LEFT_CTRL	(17,0x80),
	LEFT_SHIFT	(16,0x81),
	LEFT_ALT	(18,0x82),
	RIGHT_CTRL	(17,0x84),
	RIGHT_SHIFT	(16,0x85),
	RIGHT_ALT	(18,0x86),
	PAUSE		(19,19+136),
	CAPS_LOCK	(20,0x39),
	ESC			(27,0x29),
	SPACE		(' ',0x2c),
	SPAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACE(' ',0x2c),
	PAGE_UP		(33,0x4b),
	PAGE_DOWN	(34,0x4e),
	END			(35,0x4d),
	HOME		(36,0x4a),
	ARROW_LEFT	(37,0x50),
	ARROW_UP	(38,0x52),
	ARROW_RIGHT	(39,0x4f),
	ARROW_DOWN	(40,0x51),
	INSERT		(45,0x49),
	DELETE		(46,0x4c),
	ZERO		('0',0x27),
	ONE			('1',0x1E),
	TWO			('2',0x1F),
	THREE		('3',0x20),
	FOUR		('4',0x21),
	FIVE		('5',0x22),
	SIX			('6',0x23),
	SEVEN		('7',0x24),
	EIGHT		('8',0x25),
	NINE		('9',0x26),

	/*	a			('a',0x04),
	b			('b',0x05),
	c			('c',0x06),
	d			('d',0x07),
	e			('e',0x08),
	f			('f',0x09),
	g			('g',0x0a),
	h			('h',0x0b),
	i			('i',0x0c),
	j			('j',0x0d),
	k			('k',0x0e),
	l			('l',0x0f),
	m			('m',0x10),
	n			('n',0x11),
	o			('o',0x12),
	p			('p',0x13),
	q			('q',0x14),
	r			('r',0x15),
	s			('s',0x16),
	t			('t',0x17),
	u			('u',0x18),
	v			('v',0x19),
	w			('w',0x1a),
	x			('x',0x1b),
	y			('y',0x1c),
	z			('z',0x1d),

	
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
	//*/
	a			('A',0x04),
	b			('B',0x05),
	c			('C',0x06),
	d			('D',0x07),
	e			('E',0x08),
	f			('F',0x09),
	g			('G',0x0a),
	h			('H',0x0b),
	i			('I',0x0c),
	j			('J',0x0d),
	k			('K',0x0e),
	l			('L',0x0f),
	m			('M',0x10),
	n			('N',0x11),
	o			('O',0x12),
	p			('P',0x13),
	q			('Q',0x14),
	r			('R',0x15),
	s			('S',0x16),
	t			('T',0x17),
	u			('U',0x18),
	v			('V',0x19),
	w			('W',0x1a),
	x			('X',0x1b),
	y			('Y',0x1c),
	z			('Z',0x1d),
	
	
	LEFT_GUI	(91,0x83),
	RIGHT_GUI	(92,0x87),
	//SELECT		(93,93+136),//???
	
	NUMPAD0		(96,0x62),
	NUMPAD1		(97,0x59),
	NUMPAD2		(98,0x5a),
	NUMPAD3		(99,0x5b),
	NUMPAD4		(100,0x5c),
	NUMPAD5		(101,0x5d),
	NUMPAD6		(102,0x5e),
	NUMPAD7		(103,0x5f),
	NUMPAD8		(104,0x60),
	NUMPAD9		(105,0x61),
	NUMPAD_MULTIPLY		(106,0x55),
	NUMPAD_ADD			(107,0x57),
	NUMPAD_SUBTRACT		(109,0x56),
	NUMPAD_DECIMAL_POINT(110,0x63),
	NUMPAD_DIVIDE		(111,0x54),
	
	F1			(112,0x3a),
	F2			(113,0x3b),
	F3			(114,0x3c),
	F4			(115,0x3d),
	F5			(116,0x3e),
	F6			(117,0x3f),
	F7			(118,0x40),
	F8			(119,0x41),
	F9			(120,0x42),
	F10			(121,0x43),
	F11			(122,0x45),
	F12			(123,0x46),

	SEMICOLON	(186,0x33),
	EQUALS		(187,0x2e),
	COMMA		(188,0x36),
	MINUS		(189,0x2d),
	PERIOD		(190,0x37),
	SLASH		(191,0x38),
	GRAVE		(192,0x35),
	LBRACKET	(219,0x2f),
	BACKSLASH	(220,0x31),
	RBRACKET	(221,0x30),
	QUOTE		(222,0x34),
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
		if(charMap.containsKey(kc))
			return charMap.get(kc).arduinoCode;
		return kc;
	}
	
	public static char arduinoToKeycode(char ard)
	{
		if(arduinoMap.containsKey(ard))
			return arduinoMap.get(ard).arduinoCode;
		return ard;
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
