package com.zhyyu.learn.concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLockSimpleTest
 * @author zhyyu2016
 *
 */
public class ReentrantLockSimpleTest {

	public static void main(String[] args) {
		ReentrantLock lock = new ReentrantLock();
		lock.lock();
		try {
			System.out.println("locked block running");
		} finally {
			lock.unlock();
		}
	}

}
