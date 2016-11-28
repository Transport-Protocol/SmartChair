package com.hawhamburg.sg.mwrp.gamectrl.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ConnectPacket implements IDataPacket{
	private short version;
	private short magicNumber;
	
	public ConnectPacket(){}
	
	public ConnectPacket(short version, short magicNumber)
	{
		this.version=version;
		this.magicNumber=magicNumber;
	}
	
	public short getVersion()
	{
		return version;
	}
	
	public short getMagicNumber()
	{
		return magicNumber;
	}
	
	@Override
	public void writeToStream(DataOutputStream stream) throws IOException {
		stream.write(new byte[]{(byte)(0x80|version>>8),(byte)version,
								(byte)(magicNumber>>8),(byte)(magicNumber)});
	}

	@Override
	public void readFromStream(DataInputStream stream) throws IOException{
		version=(short)((stream.read()&0xff)<<8&0b01111111);
		version|=stream.read()&0xff;
		magicNumber=(short)((stream.read()&0xff)<<8);
		magicNumber|=stream.read()&0xff;
	}

}
