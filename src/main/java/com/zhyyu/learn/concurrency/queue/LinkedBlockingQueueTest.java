package com.zhyyu.learn.concurrency.queue;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * LinkedBlockingQueueTest
 * @author zhyyu2016
 *
 */
public class LinkedBlockingQueueTest {

	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingQueue<Integer> linkedBlockingQueue = new LinkedBlockingQueue<>();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					System.out.println("put value into queue");
					linkedBlockingQueue.put(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();;
		
		Integer takeValue = linkedBlockingQueue.take();
		System.out.println("get value from queue: " + takeValue);
	}
	
}
