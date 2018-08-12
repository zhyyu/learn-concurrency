package com.zhyyu.learn.concurrency.threadpool.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试 corePoolSize maxPoolSize keepAliveTime
 * @author zhyyu
 * <pre>
 * CON:
 * corePoolSize 基本线程数
 * maxPoolSize 最大同时可执行线程数
 * 线程数大于corePoolSize 的空闲线程, 空闲时间操作keepAliveTime, 将被回收
 * 
 * 1. maxPoolSize < corePoolSize java.lang.IllegalArgumentException
 * 2. 设置maxPoolSize = corePoolSize,  超过 poolSize 线程不执行
 *
 * 3. coreSize = 0 maxSize > 0, 提交多于 SynchronousQueue 任务, RejectedExecutionException异常
 * 4. coreSize = 0 maxSize > 0, 使用 SynchronousQueue, 提交多少任务就有多少个工作线程 (没有线程WAITING, 工作线程数小于线程池maxSize, Executor 将创建一个新线程)
 * 5. coreSize = 0 maxSize > 0, 使用LinkedBlockingQueue, 仅有一个工作线程工作  (没有线程WAITING, 工作线程数小于线程池maxSize, Executor 将等待工作线程空闲后将LinkedQueue 中任务添加)
 * 6. coreSize = 0 maxSize > 0, 到达keepAliveTime, 清理以完成任务(空闲)线程
 */
public class PoolCoreMaxSizeAndKeepAliveTimeTest {

	public static void main(String[] args) throws InterruptedException {
		/*ExecutorService testCoreSize = testCoreSize();
		Thread.sleep(1000);
		System.out.println(Thread.activeCount());
		testCoreSize.shutdown();
		Thread.sleep(1000);*/
		
		ExecutorService testMaxSize = testMaxSize();
		Thread.sleep(1000);
		System.out.println(Thread.activeCount());
		Thread.sleep(5000);
		System.out.println(Thread.activeCount());
		testMaxSize.shutdown();
		Thread.sleep(1000);
		
	}
	
	public static ExecutorService testCoreSize() {
		ExecutorService fixedThreadPool = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
		for (int i = 0; i < 10; i++) {
			fixedThreadPool.submit(new Runnable() {
				@Override
				public void run() {
					System.out.println("hello");
				}
			});
		}
		return fixedThreadPool;
	}
	
	public static ExecutorService testMaxSize() {
		ExecutorService cachedThreadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 3000L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());
		// ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		for (int i = 0; i < 10; i++) {
			cachedThreadPool.submit(new Runnable() {
				@Override
				public void run() {
					System.out.println("hello");
				}
			});
		}
		return cachedThreadPool;
	}
	
}
