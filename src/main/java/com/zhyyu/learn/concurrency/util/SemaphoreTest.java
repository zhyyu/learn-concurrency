package com.zhyyu.learn.concurrency.util;

import java.util.concurrent.Semaphore;

/**
 * test Semaphore
 * <pre>
 * CON:
 * 1. semaphore.acquire() 获取信号量, 并将信号量减一, 等待如果没有信号量
 * 2. semaphore.release() 增加信号量
 * @author Administrator
 *
 */
public class SemaphoreTest {

	private static final Integer SEMAPHORE_PERMITS = 5;
	private static Semaphore semaphore = new Semaphore(SEMAPHORE_PERMITS);
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			new Thread(new AccquireSemaphoreTask()).start();
		}
		
		Thread.sleep(5000);
		
		for (int i = 0; i < 5; i++) {
			semaphore.release();
		}
	}
	
	public static class AccquireSemaphoreTask implements Runnable {

		@Override
		public void run() {
			try {
				semaphore.acquire();
				System.out.println("acquire from semaphore success");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
