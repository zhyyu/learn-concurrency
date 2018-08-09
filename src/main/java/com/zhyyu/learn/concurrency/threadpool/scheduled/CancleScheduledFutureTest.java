package com.zhyyu.learn.concurrency.threadpool.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 测试取消ScheduledFuture 计划
 * <pre>
 * CON:
 * 1. 在schedule 执行之前cancle 返回true, 任务不执行
 * 2. 在schedule 执行之后cancle, 任务未完成, 返回true, 任务被interrupt
 * 3. 在schedule 执行之后cancle, 任务完成, 返回false
 * @author zhyyu2016
 *
 */
public class CancleScheduledFutureTest {

	public static void main(String[] args) throws InterruptedException {
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(new MyScheduledTask(), 1000, TimeUnit.MILLISECONDS);
		
		Thread.sleep(500);
		long delay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
		System.out.println("remaining delay is: " + delay);
		
		Thread.sleep(200);
		boolean cancel = scheduledFuture.cancel(true);
		System.out.println("cancle schedule before start: " + cancel);
		//true
		
		/*Thread.sleep(1000);
		boolean cancel = scheduledFuture.cancel(true);
		System.out.println("cancle schedule after start: " + cancel);*/
		//true
		
		/*Thread.sleep(1000);
		boolean cancle = scheduledFuture.cancel(true);
		// comment MyScheduledTask while (true)
		System.out.println("cancle schedule after start and task finished: " + cancle);*/
		// false
	}
	
	public static class MyScheduledTask implements Runnable {
		@Override
		public void run() {
			while (true) {
				System.out.println("hello");
			}
		}
	}
	
}
