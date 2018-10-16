package com.zhyyu.learn.concurrency.threadpool.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * TODO 待测试 LinkedBlockingQueue ArrayBlockingQueue 区别, 读api
 * <pre>
 * 1. 均会阻塞, 到达队列大小
 * @author zhyyu2016
 *
 */
public class LinkedBlockingQueueVsArrayBlockingQueue {

	public static void main(String[] args) throws InterruptedException {
		LinkedBlockingQueue<Object> linkedBlockingQueue = new LinkedBlockingQueue<>(2);
		for (int i = 0; i < 3; i++) {
			linkedBlockingQueue.put(i);
			System.out.println("linkedBlockingQueue put: " + i);
		}
		
		ArrayBlockingQueue<Object> arrayBlockingQueue = new ArrayBlockingQueue<>(2);
		for (int i = 0; i < 3; i++) {
			arrayBlockingQueue.put(i);
			System.out.println("arrayBlockingQueue put: " + i);
		}
	}
	
}
