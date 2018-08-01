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
