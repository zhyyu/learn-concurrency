package com.zhyyu.learn.concurrency.threadpool.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 提交负延迟执行时间任务测试
 * <pre>
 * CON:
 * 任务立即执行
 * @author zhyyu2016
 *
 */
public class SubmitNegativeDelayTest {

	public static void main(String[] args) {
		ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.schedule(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("hello");
			}
		}, -2, TimeUnit.SECONDS);
	}
	
}
