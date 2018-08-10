package com.zhyyu.learn.concurrency.threadpool.future;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 测试 future.isDone()
 * @author zhyyu2016
 *
 */
public class FutureIsDownTest {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> future = executor.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("hello");
			}
		});
		
		Thread.sleep(1000);
		System.out.println(future.isDone());
	}
	
}
