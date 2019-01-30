package com.zhyyu.learn.concurrency.collection;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 测试 ArrayBlockingQueue 读写顺序
 * <pre>
 * FIFO
 * 
 * @author zhyyu2016
 *
 */
public class ArrayBlockingQueueTest {

	public static void main(String[] args) {
		ArrayBlockingQueue<Integer> arrayBlockingQueue = new ArrayBlockingQueue<>(5);
		arrayBlockingQueue.add(1);
		arrayBlockingQueue.add(2);
		arrayBlockingQueue.add(3);
		
		while (!arrayBlockingQueue.isEmpty()) {
			System.out.println(arrayBlockingQueue.poll());
		}
	}
	
}
