package com.zhyyu.learn.concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLockInterruptTest
 * @author zhyyu2016
 *
 */
public class ReentrantLockInterruptTest {

	private static ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronizedMethod1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		thread1.start();
		Thread.sleep(1000L);
		System.out.println("is thread 1 interrupted: " + thread1.isInterrupted());
		
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					synchronizedMethod2();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					e.printStackTrace();
					while (true);
				}
			}
		});
		thread2.start();
		Thread.sleep(1000L);
		thread2.interrupt();
		Thread.sleep(1000L);
		System.out.println("is thread 2 interrupted: " + thread2.isInterrupted());
	}
	
	public static void synchronizedMethod1() throws InterruptedException {
		lock.lockInterruptibly();
		try {
			System.out.println("synchronizedMethod1 locked block running");
			while (true);
		} finally {
			lock.unlock();
		}
	}
	
	public static void synchronizedMethod2() throws InterruptedException {
		lock.lockInterruptibly();
		try {
			System.out.println("synchronizedMethod2 locked block running");
		} finally {
			lock.unlock();
		}
	}
	
}
