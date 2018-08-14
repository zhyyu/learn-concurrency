package com.zhyyu.learn.concurrency.threadpool.config;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 测试 RejectedExecutionHandler
 * @author zhyyu
 *
 */
public class RejectedExecutionHandlerTest {

	public static void main(String[] args) {
		/* ====================================== abort policy ====================================== */
		/*ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.AbortPolicy());
		for (int i = 0; i < 5; i++) {
			System.out.println("try submit: " + i);
			threadPoolExecutor.submit(new MyTask());
		}*/
		/**
		 * OUTPUT:
		 * try submit: 0
try submit: 1
try submit: 2
Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task java.util.concurrent.FutureTask@33909752 rejected from java.util.concurrent.ThreadPoolExecutor@55f96302[Running, pool size = 1, active threads = 1, queued tasks = 1, completed tasks = 0]
	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2063)
	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:830)
	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1379)
	at java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:112)
	at com.zhyyu.learn.concurrency.threadpool.config.RejectedExecutionHandlerTest.main(RejectedExecutionHandlerTest.java:14)
		 */
		
		/* ====================================== caller runs policy ====================================== */
		/*ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.CallerRunsPolicy());
		for (int i = 0; i < 5; i++) {
			System.out.println("try submit: " + i);
			threadPoolExecutor.submit(new MyTask());
		}*/
		/**
		 * OUTPUT:
		 * try submit: 0
try submit: 1
try submit: 2
		 */
		
		/* ====================================== DiscardPolicy ====================================== */
		/*ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardPolicy());
		for (int i = 0; i < 5; i++) {
			System.out.println("try submit: " + i);
			threadPoolExecutor.submit(new MyTask());
		}*/
		/**
		 * try submit: 0
try submit: 1
try submit: 2
try submit: 3
try submit: 4
		 */
		
		/* ====================================== DiscardOldestPolicy ====================================== */
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1), new ThreadPoolExecutor.DiscardOldestPolicy());
		for (int i = 0; i < 5; i++) {
			System.out.println("try submit: " + i);
			threadPoolExecutor.submit(new MyTask2(i));
		}
		/**
		 * try submit: 0
try submit: 1
try submit: 2
try submit: 3
try submit: 4
mytask2 running: 0
mytask2 running: 4
		 */
	}
	
	public static class MyTask implements Runnable {

		@Override
		public void run() {
			while (true);
		}
		
	}
	
	public static class MyTask2 implements Runnable {

		private int i;
		
		public MyTask2(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			System.out.println("mytask2 running: " + i);
		}
		
	}
	
}
