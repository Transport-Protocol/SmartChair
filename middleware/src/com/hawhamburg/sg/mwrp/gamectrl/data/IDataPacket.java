package com.hawhamburg.sg.mwrp.gamectrl.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IDataPacket {
	void writeToStream(DataOutputStream stream) throws IOException;
	void readFromStream(DataInputStream stream) throws IOException;
}
