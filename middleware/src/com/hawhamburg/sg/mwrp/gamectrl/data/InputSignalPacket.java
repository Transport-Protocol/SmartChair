package com.hawhamburg.sg.mwrp.gamectrl.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class InputSignalPacket implements IDataPacket {
	private short sessionId;
	private int packetId;
	private short[] devices;
	private short[] values;
	
	public InputSignalPacket(){}
	
	public InputSignalPacket(short sessionId, int packetId, short[] devices, short[] values)
	{
		this.sessionId=sessionId;
		this.packetId=packetId;
		this.devices=devices;
		this.values=values;
	}
	
	
	public short getSessionId()
	{
		return sessionId;
	}
	
	public int getPacketId()
	{
		return packetId;
	}
	
	public short[] getDevices()
	{
		return devices;
	}
	
	public short[] getValues()
	{
		return values;
	}
	private static long lastTime=0;
	@Override
	public void writeToStream(DataOutputStream stream) throws IOException {
		long t=System.currentTimeMillis();
		System.out.printf("TimeDelta: %d, SessionId: %d, PacketId: %d, Devices.length: %d, [",t-lastTime,sessionId,packetId,devices.length);
		lastTime=t;
		stream.writeShort(sessionId);
		stream.writeShort(packetId>>8);
		stream.writeByte(packetId);
		stream.writeByte(devices.length);
		for(int i=0;i<Math.min(devices.length,values.length);i++)
		{
			System.out.printf("%s%x:%d",i==0?"":", ",devices[i],values[i]);
			stream.writeShort(devices[i]);
			stream.writeShort(values[i]);
		}
		System.out.println("]");
	}

	@Override
	public void readFromStream(DataInputStream stream) {
		// TODO Auto-generated method stub

	}

}
