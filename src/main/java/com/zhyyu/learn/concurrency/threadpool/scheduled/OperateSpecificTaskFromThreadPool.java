package com.zhyyu.learn.concurrency.threadpool.scheduled;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 操作特定的任务从线程池中
 * <pre>
 * CON:
 * 使用 scheduledFutureMap 成员变量保存每个任务future 引用, 使用future 引用操作线程池中特定任务(比如取消)
 * @author zhyyu2016
 *
 */
public class OperateSpecificTaskFromThreadPool {
	
	private static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
	private static final Map<Integer, ScheduledFuture<?>> scheduledFutureMap = new HashMap<>();
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			ScheduledFuture<?> scheduledFuture = scheduledExecutorService.schedule(new MyScheduledTask(i), 3000, TimeUnit.MILLISECONDS);
			scheduledFutureMap.put(i, scheduledFuture);
		}
		
		Thread.sleep(1000);
		ScheduledFuture<?> scheduledFuture = scheduledFutureMap.get(3);
		scheduledFuture.cancel(true);
	}
	
	public static class MyScheduledTask implements Runnable {
		private int i;
		
		public MyScheduledTask(int i) {
			super();
			this.i = i;
		}

		@Override
		public void run() {
			System.out.println("my scheduled task triggered, i: " + i);
		}
		
	}

}
