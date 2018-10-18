package com.zhyyu.learn.concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁降级测试
 * <pre>
 * 1. 持有读锁时不能再试图持有写锁, 否则死锁, 若需要持有写锁, 则解锁读锁(读锁不能升级)
 * 2. 持有写锁时可以再持有读锁(写锁可降级)
 * @author zhyyu2016
 *
 */
public class ReadWriteLockDowngradingTest {

	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();;
	private static ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
	private static ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
	
	public static void main(String[] args) {
		// onlyReadLock();
		// onlyWriteLock();
		// writeLockDowngrading2ReadLock();
		// readLockUpgrading2WriteLock();
		
		unlockReadLockAndThenLockWriteLock();
	}
	
	// jvm 退出
	public static void onlyReadLock() {
		readLock.lock();
	}
	
	// jvm 退出
	public static void onlyWriteLock() {
		writeLock.lock();
	}
	
	// jvm 退出
	public static void writeLockDowngrading2ReadLock() {
		writeLock.lock();
		readLock.lock();
	}
	
	// jvm 不退出
	public static void readLockUpgrading2WriteLock() {
		readLock.lock();
		
		// locked
		writeLock.lock();
	}
	
	// jvm 退出
	public static void unlockReadLockAndThenLockWriteLock() {
		readLock.lock();
		readLock.unlock();
		
		writeLock.lock();
	}
	
}
