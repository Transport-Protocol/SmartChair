package com.hawhamburg.sg.mwrp.gamectrl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.hawhamburg.sg.data.AbstractValue;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.SensorValue;
import com.hawhamburg.sg.mwrp.DataProvider;
import com.hawhamburg.sg.mwrp.gamectrl.data.*;

import static com.hawhamburg.sg.mwrp.gamectrl.GameControllerDevice.*;

public class GameController {

	private GameControllerProperties properties;
	
	private Socket socket;

	private DataOutputStream outStream;
	private DataInputStream inStream;
	private Thread reader;
	private AtomicInteger nextPacketId = new AtomicInteger();

	private boolean connected;

	private short sessionId;
	private DataProvider dataProvider;

	public GameController(DataProvider dp, GameControllerProperties properties) {
		if(properties==null)
			throw new IllegalArgumentException("properties");
		this.dataProvider = dp;
		this.properties=properties;
	}

	public void connect() throws UnknownHostException, IOException {
		socket = new Socket();
		socket.connect(new InetSocketAddress(InetAddress.getByName(properties.getGcHost()), properties.getGcPort()));
		outStream = new DataOutputStream(socket.getOutputStream());
		inStream = new DataInputStream(socket.getInputStream());
		connected = true;
		reader = new Thread(this::readLoop);
		reader.start();
		ConnectPacket packet = new ConnectPacket(GameControllerConstants.PROTOCOL_VERSION,
				GameControllerConstants.MAGIC_NUMBER);
		sendPacket(packet);
	}

	public void sendPacket(IDataPacket packet) throws IOException {
		packet.writeToStream(outStream);
	}

	public void sendSingleDevice(short deviceId, short value) throws IOException {
		sendPacket(new InputSignalPacket(sessionId, nextPacketId.getAndIncrement(), new short[] { deviceId },
				new short[] { value }));
	}

	public void sendKeys(short msTime, InputCharacter... chars) throws IOException {
		short[] devs = new short[chars.length];
		short[] vals = new short[chars.length];
		Arrays.fill(vals, msTime);
		for (int i = 0; i < chars.length; i++) {
			devs[i] = (short) (chars[i].getArduinoCode() | 0x0100);
		}
		sendPacket(new InputSignalPacket(sessionId, nextPacketId.getAndIncrement(), devs, vals));
	}

	public void sendKeys(short msTime, String keys) throws IOException {
		sendKeys(msTime, InputCharacter.getChars(keys));
	}

	public void sendArduinoKeys(short msTime, char... chars) throws IOException {

		short[] devs = new short[chars.length];
		short[] vals = new short[chars.length];
		Arrays.fill(vals, msTime);
		for (int i = 0; i < chars.length; i++) {
			devs[i] = (short)chars[i];
		}
		sendPacket(new InputSignalPacket(sessionId, nextPacketId.getAndIncrement(), devs, vals));
	}

	public void sendArduinoKeys(short[] msTime, char... chars) throws IOException {

		short[] devs = new short[chars.length];
		for (int i = 0; i < chars.length; i++) {
			devs[i] = (short)(chars[i]|GameControllerDevice.KEYBOARD_START.getId());
		}
		sendPacket(new InputSignalPacket(sessionId, nextPacketId.getAndIncrement(), devs, msTime));
	}

	public void sendKeys(String keys, short... msTime) throws IOException {
		if (keys.length() != msTime.length)
			throw new IllegalArgumentException("keys.length()!=msTime.length");

		InputCharacter[] chars = InputCharacter.getChars(keys);
		short[] devs = new short[chars.length];
		for (int i = 0; i < chars.length; i++) {
			devs[i] = (short) (chars[i].getArduinoCode() | GameControllerDevice.KEYBOARD_START.getId());

		}
		sendPacket(new InputSignalPacket(sessionId, nextPacketId.getAndIncrement(), devs, msTime));
	}

	public void setLeds(boolean all) throws IOException {
		short x = (short) (all ? 1 : 0);
		sendPacket(new InputSignalPacket(sessionId, nextPacketId.getAndIncrement(),
				new short[] { LED_ORANGE.getId(), LED_GREEN.getId(), LED_RED.getId() }, new short[] { x, x, x }));
	}

	public void setLeds(boolean orange, boolean green, boolean red) throws IOException {
		sendPacket(new InputSignalPacket(sessionId, nextPacketId.getAndIncrement(),
				new short[] { LED_ORANGE.getId(), LED_GREEN.getId(), LED_RED.getId() },
				new short[] { (short) (orange ? 1 : 0), (short) (green ? 1 : 0), (short) (red ? 1 : 0) }));
	}

	public void setLedOrange(boolean value) throws IOException {
		sendSingleDevice(LED_ORANGE.getId(), (short) (value ? 1 : 0));
	}

	public void setLedGreen(boolean value) throws IOException {
		sendSingleDevice(LED_GREEN.getId(), (short) (value ? 1 : 0));
	}

	public void setLedRed(boolean value) throws IOException {
		sendSingleDevice(LED_RED.getId(), (short) (value ? 1 : 0));
	}

	public void disconnect() throws IOException {
		connected = false;
		socket.close();
	}

	private void readLoop() {
		try {
			while (connected) {
				while (inStream.available() == 0)
					;

				short s = inStream.readShort();
				if (s < 0) {
					ConnectAckPacket p = new ConnectAckPacket(s);
					p.readFromStream(inStream);
					sessionId = p.getSessionId();
					System.out.println("GameController SessionId: " + sessionId);
				} else {
					ErrorPacket p = new ErrorPacket(s);
					p.readFromStream(inStream);
					System.err.printf("Error packet{ ErrorCode: %d; Packet: %d }\n", p.getErrorCode(), p.getPacketId());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public short getSessionId() {
		return sessionId;
	}

	public int getNextPacketId() {
		return nextPacketId.getAndIncrement();
	}

	boolean lastStep = true;

	public final void sensorMessageReceived(SensorMessage<AbstractValue> smg) {
		if(smg.getSensortype()!=SensorType.pressure)
			return;
		
		int[] p = new int[4];
		for (int i = 0; i < Math.min(smg.getValues().size(), 4); i++) {
			p[i] = (int) smg.getValues().get(i).getValue();
		}
		int fb, s;
		fb = (p[0] + p[1]) - (p[2] + p[3]);
		s = (p[0] + p[2]) - (p[1] + p[3]);
		try {
			StringBuilder aChars = new StringBuilder();
			StringBuilder dChars = new StringBuilder();
			if (0 == fb && 0 == s) {
				if (lastStep) {
					dChars.append(properties.getKeyForward()).append(properties.getKeyBackward()).append(properties.getKeyLeft()).append(properties.getKeyRight());
					lastStep=false;
				}
			} else {
				if (fb < -properties.getFbThreshold()) {
					aChars.append(properties.getKeyForward());
					dChars.append(properties.getKeyBackward());
					lastStep = true;
				} else if (fb > properties.getFbThreshold()) {
					aChars.append(properties.getKeyBackward());
					dChars.append(properties.getKeyForward());
					lastStep = true;
				}

				if (s < -properties.getSwThreshold()) {
					aChars.append(properties.getKeyLeft());
					dChars.append(properties.getKeyRight());
					lastStep = true;
				} else if (s > properties.getSwThreshold()) {
					aChars.append(properties.getKeyRight());
					dChars.append(properties.getKeyLeft());
					lastStep = true;
				}
			}
			if (aChars.length() + dChars.length() > 0) {
				short[] t = new short[aChars.length() + dChars.length()];
				Arrays.fill(t, 0, aChars.length(), (short) 200);
				Arrays.fill(t, aChars.length(), t.length, (short) 0);
				sendArduinoKeys(t, (aChars.toString() + dChars.toString()).toCharArray());
				boolean[] leds = new boolean[3];
				for (int i = 0; i < aChars.length(); i++) {
					char ch=aChars.charAt(i);
					if(ch==properties.getKeyForward())
						leds[0] = true;
					if(ch==properties.getKeyLeft())
						leds[1] = true;
					if(ch==properties.getKeyRight())
						leds[2] = true;
					
				}
				setLeds(leds[2], leds[0], leds[1]);
			}
		} catch (IOException e) {
		}
	}
}
