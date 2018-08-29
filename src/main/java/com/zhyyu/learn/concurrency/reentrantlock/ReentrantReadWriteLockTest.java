package com.zhyyu.learn.concurrency.reentrantlock;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.crypto.Data;

/**
 * ReentrantReadWriteLockTest, jdk1.8 docs
 * @author zhyyu2016
 *
 */
public class ReentrantReadWriteLockTest {

	public static void main(String[] args) {
		RWDictionary rwDictionary = new RWDictionary();
		
		rwDictionary.put("key1", null);
		System.out.println(rwDictionary.get("key1"));
	}

	public static class RWDictionary {
		private final Map<String, Data> m = new TreeMap<String, Data>();
		private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
		private final Lock r = rwl.readLock();
		private final Lock w = rwl.writeLock();

		public Data get(String key) {
			r.lock();
			try {
				return m.get(key);
			} finally {
				r.unlock();
			}
		}

		public String[] allKeys() {
			r.lock();
			try {
				return (String[]) m.keySet().toArray();
			} finally {
				r.unlock();
			}
		}

		public Data put(String key, Data value) {
			w.lock();
			try {
				return m.put(key, value);
			} finally {
				w.unlock();
			}
		}

		public void clear() {
			w.lock();
			try {
				m.clear();
			} finally {
				w.unlock();
			}
		}
	}

}
