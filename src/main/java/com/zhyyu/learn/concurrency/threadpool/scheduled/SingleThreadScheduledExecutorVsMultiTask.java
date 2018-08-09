package com.zhyyu.learn.concurrency.threadpool.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 测试单个大小线程池执行多个定时任务, 多个定时任务同时触发
 * <pre>
 * CON:
 * 若多个任务同时执行, 因为只有一个工作线程, 所有只有一个任务可运营, 其他任务需等待, 造成运行时间不精确
 * @author zhyyu2016
 *
 */
public class SingleThreadScheduledExecutorVsMultiTask {

	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
		
		for (int i = 0; i < 5; i++) {
			scheduledExecutor.schedule(new MyScheduledTask(i), 2000, TimeUnit.MILLISECONDS);
		}
	}
	
	public static class MyScheduledTask implements Runnable {
		private int i;
		
		public MyScheduledTask(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			System.out.println("my scheduled task runing, i: " + i);
			try {
				Thread.sleep(1000 * i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
