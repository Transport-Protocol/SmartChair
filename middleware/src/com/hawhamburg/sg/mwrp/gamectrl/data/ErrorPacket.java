package com.hawhamburg.sg.mwrp.gamectrl.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ErrorPacket implements IDataPacket
{
	private short sessionId;
	private int packetId;
	private int errorCode;
	private byte[] data;
	
	public ErrorPacket(short sessionId)
	{
		this.sessionId=(short)(sessionId&0x7FFF);
	}
	
	public short getSessionId()
	{
		return sessionId;
	}
	
	public int getPacketId()
	{
		return packetId;
	}
	
	public int getErrorCode()
	{
		return errorCode;
	}
	
	public byte[] getData()
	{
		return data;
	}
	
	@Override
	public void writeToStream(DataOutputStream stream) throws IOException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void readFromStream(DataInputStream stream) throws IOException {
		packetId=stream.readUnsignedShort()<<8;
		packetId|=stream.readUnsignedByte();
		errorCode=stream.readUnsignedByte();
		data=new byte[stream.readUnsignedShort()*2];
		stream.read(data);
	}
	
}
