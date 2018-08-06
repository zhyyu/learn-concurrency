package com.zhyyu.learn.concurrency.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ExecutorService 提交 Runnable 返回 Future 有意义, 可以中断任务
 * @author zhyyu
 *
 */
public class FutureCancleTest {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<?> future = executorService.submit(new MyTask());
		
		Thread.sleep(1000);
		System.out.println(future.cancel(true));
	}
	
	public static class MyTask implements Runnable {
		@Override
		public void run() {
			while (true) {
				if (Thread.currentThread().isInterrupted()) {
					System.out.println("mytask is interrupted");
					break;
				}
				System.out.println("mytask is running");
			}
		}
	}
	
}
