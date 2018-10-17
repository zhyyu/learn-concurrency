package com.zhyyu.learn.concurrency.threadpool.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试提交任务至工作队列
 * <pre>
 * 设置corePoolSize = 0
 * ----------------------
 * 1. 提交的第一个任务不进工作队列, 直接生成工作线程
 * 2. 之后提交的任务, 任务数小于工作队列大小, 提交至工作队列
 * 3. 提交任务填满工作队列后, 新建工作线程
 * @author zhyyu2016
 *
 */
public class SubmitTask2WoreQueueJstackTest {
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService threadPool = new ThreadPoolExecutor(0, 10, 1L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10));
		
		/**
		 * 当设置<=11 (10 + 1) 时, 1 个工作线程
		 * 当设置>=12 是, (12 - 10) 个工作线程
		 */
		for (int i = 0; i < 11; i++) {
			threadPool.submit(new MyInfiniteTask(i));
			System.out.println(Thread.activeCount());
		}
		
		while (true);
	}
	
	public static class MyInfiniteTask implements Runnable {

		private Integer i;
		
		public MyInfiniteTask(Integer i) {
			this.i = i;
		}

		@Override
		public void run() {
			while (true) {
				System.out.println("MyInfiniteTask: " + i);
				sleepSeconds(1);
			}
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

// ============================= taskCount = workQueue + 2;
//"pool-1-thread-2" #11 prio=5 os_prio=0 tid=0x000000001df04800 nid=0xea0c waiting on condition [0x000000001ed1f000]
//		   java.lang.Thread.State: TIMED_WAITING (sleeping)
//		        at java.lang.Thread.sleep(Native Method)
//		        at com.zhyyu.learn.concurrency.threadpool.config.SubmitTask2WoreQueueJstackTest.sleepSeconds(SubmitTask2WoreQueueJstackTest.java:57)
//		        at com.zhyyu.learn.concurrency.threadpool.config.SubmitTask2WoreQueueJstackTest$MyInfiniteTask.run(SubmitTask2WoreQueueJstackTest.java:33)
//		        at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
//		        at java.util.concurrent.FutureTask.run(FutureTask.java:266)
//		        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
//		        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
//		        at java.lang.Thread.run(Thread.java:745)
//
//		"pool-1-thread-1" #10 prio=5 os_prio=0 tid=0x000000001deff800 nid=0xeb70 waiting on condition [0x000000001eb7e000]
//		   java.lang.Thread.State: TIMED_WAITING (sleeping)
//		        at java.lang.Thread.sleep(Native Method)
//		        at com.zhyyu.learn.concurrency.threadpool.config.SubmitTask2WoreQueueJstackTest.sleepSeconds(SubmitTask2WoreQueueJstackTest.java:57)
//		        at com.zhyyu.learn.concurrency.threadpool.config.SubmitTask2WoreQueueJstackTest$MyInfiniteTask.run(SubmitTask2WoreQueueJstackTest.java:33)
//		        at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
//		        at java.util.concurrent.FutureTask.run(FutureTask.java:266)
//		        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
//		        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
//		        at java.lang.Thread.run(Thread.java:745)


//============================= taskCount <= workQueue + 1;
//"pool-1-thread-1" #10 prio=5 os_prio=0 tid=0x000000001dcd2800 nid=0xeb54 waiting on condition [0x000000001eadf000]
//		   java.lang.Thread.State: TIMED_WAITING (sleeping)
//		        at java.lang.Thread.sleep(Native Method)
//		        at com.zhyyu.learn.concurrency.threadpool.config.SubmitTask2WoreQueueJstackTest.sleepSeconds(SubmitTask2WoreQueueJstackTest.java:41)
//		        at com.zhyyu.learn.concurrency.threadpool.config.SubmitTask2WoreQueueJstackTest$MyInfiniteTask.run(SubmitTask2WoreQueueJstackTest.java:33)
//		        at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
//		        at java.util.concurrent.FutureTask.run(FutureTask.java:266)
//		        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
//		        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
//		        at java.lang.Thread.run(Thread.java:745)