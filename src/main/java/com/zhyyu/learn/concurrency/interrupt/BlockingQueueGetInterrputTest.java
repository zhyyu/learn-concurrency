package com.zhyyu.learn.concurrency.interrupt;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * BlockingQueueGet 阻塞方法是否会清除中断标志位
 * CON: 
 * 1. 会清除中断标志
 * 
 * @author Administrator
 *
 */
public class BlockingQueueGetInterrputTest {

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new MyTask());
		thread.start();
		thread.interrupt();
		
		// !!! 必须添加, 否则 下面thread.isInterrupted() 可能返回true
		Thread.sleep(1000);
		
		System.out.println(thread.isInterrupted());
	}

	public static class MyTask implements Runnable {

		@Override
		public void run() {
			LinkedBlockingQueue<Object> linkedBlockingQueue = new LinkedBlockingQueue<>();
			try {
				linkedBlockingQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.out.println("is linkedBlockingQueue take clear interrupt flag:" + Thread.currentThread().isInterrupted());
			}
			while (true);
		}
	}
	
}
