package com.zhyyu.learn.concurrency.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLockSimpleTest
 * @author zhyyu2016
 *
 */
public class ReentrantLockSimpleTest {

	public static void main(String[] args) throws InterruptedException {
		ReentrantLock lock = new ReentrantLock();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				lock.lock();
				while(true);
			}
		}).start();
		
		Thread.sleep(1000);
		
		
		lock.lock();
		try {
			System.out.println("locked block running");
		} finally {
			lock.unlock();
		}
	}

}

//"main" #1 prio=5 os_prio=0 tid=0x0000000003303000 nid=0x1b28 waiting on condition [0x00000000032ff000]
//		   java.lang.Thread.State: WAITING (parking)
//		        at sun.misc.Unsafe.park(Native Method)
//		        - parking to wait for  <0x00000000d6250698> (a java.util.concurrent.locks.ReentrantLock$NonfairSync)
//		        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//		        at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
//		        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireQueued(AbstractQueuedSynchronizer.java:870)
//		        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquire(AbstractQueuedSynchronizer.java:1199)
//		        at java.util.concurrent.locks.ReentrantLock$NonfairSync.lock(ReentrantLock.java:209)
//		        at java.util.concurrent.locks.ReentrantLock.lock(ReentrantLock.java:285)
//		        at com.zhyyu.learn.concurrency.reentrantlock.ReentrantLockSimpleTest.main(ReentrantLockSimpleTest.java:26)