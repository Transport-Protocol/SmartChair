package com.hawhamburg.sg.mwrp.gamectrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum InputCharacter {
	SPACE		(' ',' '),
	ARROW_UP	('↑',(char)0xDA),
	ARROW_DOWN	('↓',(char)0xD9),
	ARROW_LEFT	('←',(char)0xD8),
	ARROW_RIGHT	('→',(char)0xD7),
	
	A	('A','A'),
	B	('B','B'),
	C	('C','C'),
	D	('D','D'),
	E	('E','E'),
	F	('F','F'),
	G	('G','G'),
	H	('H','H'),
	I	('I','I'),
	J	('J','J'),
	K	('K','K'),
	L	('L','L'),
	M	('M','M'),
	N	('N','N'),
	O	('O','O'),
	P	('P','P'),
	Q	('Q','Q'),
	R	('R','R'),
	S	('S','S'),
	T	('T','T'),
	U	('U','U'),
	V	('V','V'),
	W	('W','W'),
	X	('X','X'),
	Y	('Y','Y'),
	Z	('Z','Z'),
	
	a	('a','a'),
	b	('b','b'),
	c	('c','c'),
	d	('d','d'),
	e	('e','e'),
	f	('f','f'),
	g	('g','g'),
	h	('h','h'),
	i	('i','i'),
	j	('j','j'),
	k	('k','k'),
	l	('l','l'),
	m	('m','m'),
	n	('n','n'),
	o	('o','o'),
	p	('p','p'),
	q	('q','q'),
	r	('r','r'),
	s	('s','s'),
	t	('t','t'),
	u	('u','u'),
	v	('v','v'),
	w	('w','w'),
	x	('x','x'),
	y	('y','y'),
	z	('z','z'),
	
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
