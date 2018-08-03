package com.zhyyu.learn.concurrency.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * test threadpool submit runnable ,not callable, future get. executorService.shutdown
 * <br>
 * CON:
 
 提交runnable 给threadpool, 返回future 调用get 返回null
 
 * @author Administrator
 *
 */
public class SubmitRunnableTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<?> runnableFuture = executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("do nothing");
			}
		});
		
		System.out.println(runnableFuture.get());
		
		executorService.shutdown();
		System.out.println(executorService.awaitTermination(10, TimeUnit.SECONDS));
	}
	
}
