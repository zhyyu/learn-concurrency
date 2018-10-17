package com.zhyyu.learn.concurrency.threadpool.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池关闭测试
 * <pre>
 * 自动关闭
 * --------------------------
 * 1. 未提交任务
 * 2. 提交可执行完任务至corePoolSize=0 线程池
 * 
 * 无法关闭
 * --------------------------
 * 1. 提交无限执行任务
 * 2. 提交可执行完任务至corePoolSize != 0 线程池
 * 
 * shutdown
 * --------------------------
 * 1. 可以关闭提交可执行完任务的corePoolSize != 0 线程池
 * 2. 不可关闭提交无限执行任务的corePoolSize != 0 线程池
 * 
 * shutdownNow
 * --------------------------
 * 1. 可以关闭提交可执行完任务的corePoolSize != 0 线程池, 并且中断线程
 * 2. 不可关闭提交无限执行任务的corePoolSize != 0 线程池(该例), 中断线程后, 抛出 InterruptedException, 清除标志位, 继续无限循环, 若要关闭jvm, 可以在InterruptedException 后break 退出循环
 * 
 * @author zhyyu2016
 *
 */
public class ShutdownTest {

	public static void main(String[] args) {
		// jvm 自动 关闭
		// nonSubmitTask();
		// submitFleetingTask2ZeroCorePool();
		
		// jvm 无法关闭
		// submitInfiniteTask();
		// submitFleetingTask2NonZeroCorePool();
		
		// shutdown
		// 可以关闭
		// submitFleetingTask2NonZeroCorePoolAndShutdown();
		// 不可以关闭
		// submitInfiniteTask2NonZeroCorePoolAndShutdown();
		
		// shutdownNow
		// 可以关闭, 中断工作线程
		// submitFleetingTask2NonZeroCorePoolAndShutdownNow();
		// 不可以关闭(该例), 中断线程后, 抛出 InterruptedException, 清除标志位, 继续无限循环, 若要关闭jvm, 可以在InterruptedException 后break 退出循环
		submitInfiniteTask2NonZeroCorePoolAndShutdownNow();
	}
	
	private static void submitInfiniteTask2NonZeroCorePoolAndShutdownNow() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
		newFixedThreadPool.submit(new MyInfiniteTask());
		
		newFixedThreadPool.shutdownNow();
	}

	private static void submitFleetingTask2NonZeroCorePoolAndShutdownNow() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
		newFixedThreadPool.submit(new MyFleetingTask());
		
		newFixedThreadPool.shutdownNow();
	}

	private static void submitInfiniteTask2NonZeroCorePoolAndShutdown() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
		newFixedThreadPool.submit(new MyInfiniteTask());
		
		newFixedThreadPool.shutdown();
	}

	private static void submitFleetingTask2NonZeroCorePoolAndShutdown() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
		newFixedThreadPool.submit(new MyFleetingTask());
		
		newFixedThreadPool.shutdown();
	}

	public static void nonSubmitTask() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
	}
	
	public static void submitFleetingTask2ZeroCorePool() {
		ExecutorService zeroCoreSizeThreadPool = new ThreadPoolExecutor(0, 10, 1L, TimeUnit.SECONDS, new SynchronousQueue<>());
		zeroCoreSizeThreadPool.submit(new MyFleetingTask());
	}
	
	public static void submitInfiniteTask() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
		newFixedThreadPool.submit(new MyInfiniteTask());
	}
	
	public static void submitFleetingTask2NonZeroCorePool() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);
		newFixedThreadPool.submit(new MyFleetingTask());
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
