package com.hawhamburg.sg.mwrp.gamectrl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.hawhamburg.sg.mwrp.gamectrl.data.*;

public class GameController {
	private String host;
	private int port;

	private Socket socket;

	private DataOutputStream outStream;
	private DataInputStream inStream;
	private Thread reader;
	
	private boolean connected;
	private short sessionId;

	public GameController(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public void connect() throws UnknownHostException, IOException {
		socket = new Socket();
		socket.connect(new InetSocketAddress(InetAddress.getByName(host), port));
		outStream = new DataOutputStream(socket.getOutputStream());
		inStream = new DataInputStream(socket.getInputStream());
		connected=true;
		reader = new Thread(this::readLoop);
		reader.start();
		ConnectPacket packet = new ConnectPacket(GameControllerConstants.PROTOCOL_VERSION,
				GameControllerConstants.MAGIC_NUMBER);
		sendPacket(packet);
	}

	private void sendPacket(IDataPacket packet) throws IOException {
		packet.writeToStream(outStream);
	}

	public void disconnect() throws IOException {
		connected=false;
		socket.close();
	}

	private void readLoop() {
		try {
			while (connected) {
				while(inStream.available()==0)
					;
				
				short s = inStream.readShort();
				if(s<0)
				{
					ConnectAckPacket p=new ConnectAckPacket(s);
					p.readFromStream(inStream);
					sessionId=p.getSessionId();
					System.out.println("GameController SessionId: "+sessionId);
				}
				else
				{
					ErrorPacket p=new ErrorPacket(s);
					p.readFromStream(inStream);
					System.err.printf("Error packet{ ErrorCode: %d; Packet: %d }\n",p.getErrorCode(),p.getPacketId());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
