package com.zhyyu.learn.concurrency.util;

import java.util.concurrent.CountDownLatch;

/**
 * test CountDownLatch
 * @author Administrator
 *
 */
public class CountDownLatchTest {

	private static final Integer WAIT_THREAD_COUNT = 10;
	
	public static void main(String[] args) throws InterruptedException {

		CountDownLatch startCountDownLatch = new CountDownLatch(1);
		CountDownLatch endCountDownLatch = new CountDownLatch(WAIT_THREAD_COUNT);
		
		for (int i = 0; i < WAIT_THREAD_COUNT; i++) {
			new Thread(new MyTask(startCountDownLatch, endCountDownLatch)).start();
		}
		System.out.println("start all thread");
		
		Thread.sleep(5000);
		System.out.println("enable start latch");
		startCountDownLatch.countDown();
		
		System.out.println("wait end latch");
		endCountDownLatch.await();
		
		System.out.println("task finished");
	}
	
	public static class MyTask implements Runnable {

		private CountDownLatch startCountDownLatch;
		private CountDownLatch endCountDownLatch;
		
		public MyTask(CountDownLatch startCountDownLatch, CountDownLatch endCountDownLatch) {
			this.startCountDownLatch = startCountDownLatch;
			this.endCountDownLatch = endCountDownLatch;
		}
		
		@Override
		public void run() {
			try {
				startCountDownLatch.await();
				
				System.out.println("my task running");
				
				Thread.sleep(5000);
				
				endCountDownLatch.countDown();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}


//"Thread-9" #20 prio=5 os_prio=0 tid=0x0000000019739000 nid=0x2074 waiting on condition [0x000000001ad9f000]
//		   java.lang.Thread.State: WAITING (parking)
//		        at sun.misc.Unsafe.park(Native Method)
//		        - parking to wait for  <0x00000000d6250568> (a java.util.concurrent.CountDownLatch$Sync)
//		        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//		        at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
//		        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:997)
//		        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1304)
//		        at java.util.concurrent.CountDownLatch.await(CountDownLatch.java:231)
//		        at com.zhyyu.learn.concurrency.util.CountDownLatchTest$MyTask.run(CountDownLatchTest.java:47)
//		        at java.lang.Thread.run(Thread.java:748)
//
//"Thread-8" #19 prio=5 os_prio=0 tid=0x0000000019738800 nid=0x3b2c waiting on condition [0x000000001ac9f000]
//   java.lang.Thread.State: WAITING (parking)
//        at sun.misc.Unsafe.park(Native Method)
//        - parking to wait for  <0x00000000d6250568> (a java.util.concurrent.CountDownLatch$Sync)
//        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//        at java.util.concurrent.locks.AbstractQueuedSynchronizer.parkAndCheckInterrupt(AbstractQueuedSynchronizer.java:836)
//        at java.util.concurrent.locks.AbstractQueuedSynchronizer.doAcquireSharedInterruptibly(AbstractQueuedSynchronizer.java:997)
//        at java.util.concurrent.locks.AbstractQueuedSynchronizer.acquireSharedInterruptibly(AbstractQueuedSynchronizer.java:1304)
//        at java.util.concurrent.CountDownLatch.await(CountDownLatch.java:231)
//        at com.zhyyu.learn.concurrency.util.CountDownLatchTest$MyTask.run(CountDownLatchTest.java:47)
//        at java.lang.Thread.run(Thread.java:748)
