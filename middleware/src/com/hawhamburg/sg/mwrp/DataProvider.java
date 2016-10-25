package com.hawhamburg.sg.mwrp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import com.hawhamburg.sg.data.LocationValue;
import com.hawhamburg.sg.data.SensorMessage;
import com.hawhamburg.sg.data.SensorType;
import com.hawhamburg.sg.data.ConcurrentCache;
import com.hawhamburg.sg.data.Value;

public class DataProvider {
	private Map<SensorType, ConcurrentCache<SensorMessage>> valueLists = new HashMap<>();

	public DataProvider() {
		for (SensorType s : SensorType.values()) {
			valueLists.put(s, new ConcurrentCache<>());
		}
	}

	public void addValues(SensorMessage msg) {
		if (msg == null)
			throw new IllegalArgumentException();
		ConcurrentCache<SensorMessage> l = valueLists.get(msg.getSensortype());
		l.writeLock();
		l.add(msg);
		l.writeUnlock();
	}

	public LinkedList<SensorMessage> getDataForPastXSeconds(SensorType type, long seconds) {
		ConcurrentCache<SensorMessage> l = valueLists.get(type);
		try {
			l.readLock();
			if (l.size() == 0)
				return null;
			LinkedList<SensorMessage> ret = new LinkedList<>();
			int index = l.size() - 1;
			SensorMessage msg = null;
			while (index >= 0 && (msg = l.get(index)).getTimestamp() >= seconds) {
				ret.add(msg);
				index--;
			}
			return ret;
		} finally {
			l.readUnlock();
		}
	}

	public List<Value> getMostRecent(SensorType type) {
		ConcurrentCache<SensorMessage> l = valueLists.get(type);
		try {
			l.readLock();
			return l.size() > 0 ? l.get(l.size() - 1).getValues() : null;
		} finally {
			l.readUnlock();
		}
	}

	private static final int MAX_LOC_LOOKUP = 50;
	private static final int NUM_BEACONS = 4;

	public LocationValue[] getMostRecentLcoation() {

		ConcurrentCache<SensorMessage> l = valueLists.get(SensorType.location);
		try {
			l.readLock();
			if (l.size() == 0)
				return null;
			LocationValue[] ret = new LocationValue[NUM_BEACONS];
			int fin = 0;
			int lu = 0;
			for (int i = l.size() - 1; i >= 0; i--) {
				lu++;
				SensorMessage msg = l.get(i);
				LocationValue val = (LocationValue) msg.getValues().get(0);
				if (val.getMajor() == 1 && val.getMinor() <= NUM_BEACONS && ret[val.getMinor() - 1] == null) {
					ret[val.getMinor() - 1] = val;
					fin++;
				}
				if (lu >= MAX_LOC_LOOKUP || fin >= NUM_BEACONS)
					break;
			}
			return ret;
		} finally {
			l.readUnlock();
		}
	}
}
