package com.zhyyu.learn.concurrency.threadpool;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ThreadPoolVsNewThread
 * <br>
 * OUTPUT:
 * new thread spend mills: 126
thread pool spend mills: 10

 * @author zhyyu
 *
 */
public class ThreadPoolVsNewThread {

	public static void main(String[] args) throws InterruptedException {
		long mills1 = System.currentTimeMillis();
		calculateByNewThread();
		
		long mills2 = System.currentTimeMillis();
		System.out.println("new thread spend mills: " + (mills2 - mills1));
		
		long mills3 = System.currentTimeMillis();
		calculateByThreadPool();
		
		long mills4 = System.currentTimeMillis();
		System.out.println("thread pool spend mills: " + (mills4- mills3));
	}
	
	public static void calculateByNewThread() throws InterruptedException {
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(1000);
		for (int i = 0; i<1000; i++) {
			new Thread(new MyTask(startLatch, endLatch, i)).start();
		}
		startLatch.countDown();
		endLatch.await();
	}
	
	public static void calculateByThreadPool() throws InterruptedException {
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
		CountDownLatch startLatch = new CountDownLatch(1);
		CountDownLatch endLatch = new CountDownLatch(1000);
		
		for (int i = 0; i<1000; i++) {
			fixedThreadPool.submit(new MyTask(startLatch, endLatch, i));
		}
		startLatch.countDown();
		endLatch.await();
	}
	
	public static class MyTask implements Runnable {
		
		private CountDownLatch startLatch;
		private CountDownLatch endLatch;
		private int i;
		
		public MyTask(CountDownLatch startLatch, CountDownLatch endLatch, int i) {
			this.startLatch = startLatch;
			this.endLatch = endLatch;
			this.i = i;
		}

		@Override
		public void run() {
			try {
				startLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			doNothingWith(i);
			endLatch.countDown();
		}

		private void doNothingWith(int num) {
			@SuppressWarnings("unused")
			long result = 1;
			
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < 1000; j++) {
					num++;
					result += num * num;
				}
			}
			
		}
		
	}
	
}
