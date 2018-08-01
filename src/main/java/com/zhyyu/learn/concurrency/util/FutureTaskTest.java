package com.zhyyu.learn.concurrency.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * test FutureTask
 * @author Administrator
 *
 */
public class FutureTaskTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
		new Thread(futureTask).start();
		System.out.println("start future task");
		
		System.out.println("getting future result");
		Integer integer = futureTask.get();
		
		System.out.println("result: " + integer);
	}
	
	public static class MyCallable implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			Thread.sleep(5000);
			return 1024;
		}
	}
	
}
