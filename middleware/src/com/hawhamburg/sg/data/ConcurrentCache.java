package com.hawhamburg.sg.data;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentCache<T> {
	private static final int DEFAULT_SIZE = 0x10000;// 64kb
	private T[] values;

	private int size;

	private ReadWriteLock lock = new ReentrantReadWriteLock();

	public ConcurrentCache(int size) {
		values = (T[]) new Object[size];
	}

	public ConcurrentCache() {
		this(DEFAULT_SIZE);
	}

	public void add(T obj) {
		values[size++ % values.length] = obj;
	}

	public T get(int index) {
		int max = size();
		if (index >= max)
			throw new IndexOutOfBoundsException(index + " is out of bounds (0.." + max + ")");
		int offset = size > values.length ? size % values.length : 0;
		T t = values[offset + index % values.length];
		return t;
	}

	public void readLock() {
		lock.readLock().lock();
	}

	public void readUnlock() {
		lock.readLock().unlock();
	}

	public void writeLock() {
		lock.writeLock().lock();
	}

	public void writeUnlock() {
		lock.writeLock().unlock();
	}

	public int size() {
		return Math.min(size, values.length);
	}
}
