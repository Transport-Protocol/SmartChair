package com.hawhamburg.sg.mwrp.gamectrl.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectAckPacket implements IDataPacket {
	private short version;
	private short sessionId;
	
	public ConnectAckPacket(){}

	public ConnectAckPacket(short version,short sessionId)
	{
		this.version=version;
		this.sessionId=sessionId;
	}
	public ConnectAckPacket(short version)
	{
		this.version=(short)(version&0x7FFF);
	}
	
	public short getVersion()
	{
		return version;
	}
	
	public short getSessionId()
	{
		return sessionId;
	}
	
	@Override
	public void writeToStream(DataOutputStream stream) throws IOException {
		stream.writeShort(version&0x8000);
		stream.writeShort(sessionId);
	}

	@Override
	public void readFromStream(DataInputStream stream) throws IOException {
		sessionId=stream.readShort();
	}

}
