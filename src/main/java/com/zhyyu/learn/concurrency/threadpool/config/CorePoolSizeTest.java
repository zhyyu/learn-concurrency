package com.zhyyu.learn.concurrency.threadpool.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试corePoolSize, with maxPoolSize, activeAliveTime, workQueue
 * <pre>
 * corePoolSize 为0, blocking queue 为 ArrayBlockingQueue/LinkedBlockingQueue
 * -----------------------------------------------
 * 1. 任务填满ArrayBlockingQueue 后开始增加工作线程
 * 2. 若任务为无限任务, 则工作线程保留
 * 3. 若任务执行完, 且超过 keepAliveTime, 则工作线程不保留
 * 
 * corePoolSize 为0, blocking queue 为 SynchronousQueue
 * -----------------------------------------------
 * 1. 添加任务后立即创建线程, 无工作队列
 * 
 * corePoolSize 为5, blocking queue 为 ArrayBlockingQueue/LinkedBlockingQueue
 * -----------------------------------------------
 * 1. 任务先填满corePool, 再填满workQueue, 再填maxPool
 * 2. 任务执行完成后, 且超过keepAliveTime, 保留corePoolSize 任务
 * 
 * corePoolSize 为5, blocking queue 为 SynchronousQueue
 * -----------------------------------------------
 * 1. 任务先填满corePool, 再填maxPool, 无工作队列
 * 2. 任务执行完成后, 且超过keepAliveTime, 保留corePoolSize 任务
 * @author zhyyu2016
 *
 */
public class CorePoolSizeTest {

	public static void main(String[] args) throws InterruptedException {
		//testZeroCoreSizeWithInfiniteTask(new ArrayBlockingQueue<>(5));
		//testZeroCoreSizeWithInfiniteTask(new LinkedBlockingQueue<>(5));
		/*
		testZeroCoreSizeWithInfiniteTask: 2
		testZeroCoreSizeWithInfiniteTask: 2
		testZeroCoreSizeWithInfiniteTask: 2
		testZeroCoreSizeWithInfiniteTask: 2
		testZeroCoreSizeWithInfiniteTask: 2
		testZeroCoreSizeWithInfiniteTask: 2
		testZeroCoreSizeWithInfiniteTask: 3
		testZeroCoreSizeWithInfiniteTask: 4
		testZeroCoreSizeWithInfiniteTask: 5
		testZeroCoreSizeWithInfiniteTask: 6
		testZeroCoreSizeWithInfiniteTask: 6
		*/

		
		//testZeroCoreSizeWithFleetingTask(new ArrayBlockingQueue<>(5));
		//testZeroCoreSizeWithFleetingTask(new LinkedBlockingQueue<>(5));
		/*
		testZeroCoreSizeWithFleetingTask: 2
		testZeroCoreSizeWithFleetingTask: 2
		testZeroCoreSizeWithFleetingTask: 2
		testZeroCoreSizeWithFleetingTask: 2
		testZeroCoreSizeWithFleetingTask: 2
		testZeroCoreSizeWithFleetingTask: 2
		testZeroCoreSizeWithFleetingTask: 3
		testZeroCoreSizeWithFleetingTask: 4
		testZeroCoreSizeWithFleetingTask: 5
		testZeroCoreSizeWithFleetingTask: 6
		testZeroCoreSizeWithFleetingTask: 1
		*/
		
		//----------------------------------------------------------------------------------------------

		//testZeroCoreSizeWithFleetingTask(new SynchronousQueue<>());
		/*
		testZeroCoreSizeWithFleetingTask: 2
		testZeroCoreSizeWithFleetingTask: 3
		testZeroCoreSizeWithFleetingTask: 4
		testZeroCoreSizeWithFleetingTask: 5
		testZeroCoreSizeWithFleetingTask: 6
		testZeroCoreSizeWithFleetingTask: 7
		testZeroCoreSizeWithFleetingTask: 8
		testZeroCoreSizeWithFleetingTask: 9
		testZeroCoreSizeWithFleetingTask: 10
		testZeroCoreSizeWithFleetingTask: 11
		testZeroCoreSizeWithFleetingTask: 1
		*/
		
		//----------------------------------------------------------------------------------------------

		//testNonZeroCoreSizeWithFleetingTask(new ArrayBlockingQueue<>(3));
		/*
		testNonZeroCoreSizeWithFleetingTask: 2
		testNonZeroCoreSizeWithFleetingTask: 3
		testNonZeroCoreSizeWithFleetingTask: 4
		testNonZeroCoreSizeWithFleetingTask: 5
		testNonZeroCoreSizeWithFleetingTask: 6
		testNonZeroCoreSizeWithFleetingTask: 6
		testNonZeroCoreSizeWithFleetingTask: 6
		testNonZeroCoreSizeWithFleetingTask: 6
		testNonZeroCoreSizeWithFleetingTask: 7
		testNonZeroCoreSizeWithFleetingTask: 8
		testNonZeroCoreSizeWithFleetingTask: 6
		*/

		testNonZeroCoreSizeWithFleetingTask(new SynchronousQueue<>());
		/*
		testNonZeroCoreSizeWithFleetingTask: 2
		testNonZeroCoreSizeWithFleetingTask: 3
		testNonZeroCoreSizeWithFleetingTask: 4
		testNonZeroCoreSizeWithFleetingTask: 5
		testNonZeroCoreSizeWithFleetingTask: 6
		testNonZeroCoreSizeWithFleetingTask: 7
		testNonZeroCoreSizeWithFleetingTask: 8
		testNonZeroCoreSizeWithFleetingTask: 9
		testNonZeroCoreSizeWithFleetingTask: 10
		testNonZeroCoreSizeWithFleetingTask: 11
		testNonZeroCoreSizeWithFleetingTask: 6*/

	}
	
	public static void testNonZeroCoreSizeWithFleetingTask(BlockingQueue<Runnable> woreQueue) {
		ExecutorService zeroCoreSizeThreadPool = new ThreadPoolExecutor(5, 10, 1L, TimeUnit.SECONDS, woreQueue);
		for (int i = 0; i < 10; i++) {
			zeroCoreSizeThreadPool.submit(new MyFleetingTask());
			System.out.println("testNonZeroCoreSizeWithFleetingTask: " +Thread.activeCount());
		}
		
		sleepSeconds(5);
		System.out.println("testNonZeroCoreSizeWithFleetingTask: " +Thread.activeCount());
	}
	
	public static void testZeroCoreSizeWithFleetingTask(BlockingQueue<Runnable> woreQueue) {
		ExecutorService zeroCoreSizeThreadPool = new ThreadPoolExecutor(0, 10, 1L, TimeUnit.SECONDS, woreQueue);
		for (int i = 0; i < 10; i++) {
			zeroCoreSizeThreadPool.submit(new MyFleetingTask());
			System.out.println("testZeroCoreSizeWithFleetingTask: " +Thread.activeCount());
		}
		
		sleepSeconds(5);
		System.out.println("testZeroCoreSizeWithFleetingTask: " +Thread.activeCount());
	}
	
	public static void testZeroCoreSizeWithInfiniteTask(BlockingQueue<Runnable> woreQueue) {
		ExecutorService zeroCoreSizeThreadPool = new ThreadPoolExecutor(0, 10, 1L, TimeUnit.SECONDS, woreQueue);
		for (int i = 0; i < 10; i++) {
			zeroCoreSizeThreadPool.submit(new MyInfiniteTask());
			System.out.println("testZeroCoreSizeWithInfiniteTask: " +Thread.activeCount());
		}
		
		sleepSeconds(5);
		System.out.println("testZeroCoreSizeWithInfiniteTask: " +Thread.activeCount());
	}
	
	public static class MyInfiniteTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				sleepSeconds(1);
			}
		}
		
	}
	
	public static class MyFleetingTask implements Runnable {

		@Override
		public void run() {
			sleepSeconds(1);
		}
		
	}
	
	public static void sleepSeconds(Integer seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
