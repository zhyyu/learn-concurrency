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
		/*FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
		new Thread(futureTask).start();
		System.out.println("start future task");
		
		System.out.println("getting future result");
		Integer integer = futureTask.get();
		
		System.out.println("result: " + integer);*/
		
		
		// other way to run future task
		/**
		 * run 方法是阻塞方法, task 执行完后放行, 后可以get 到task 执行结果
		 */
		FutureTask<Integer> futureTask2 = new FutureTask<>(new MyCallable());
		futureTask2.run();
		System.out.println("futureTask2 is running");
		
		Integer integer2 = futureTask2.get();
		System.out.println("futureTask2 get result: " + integer2);
	}
	
	public static class MyCallable implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			Thread.sleep(5000);
			return 1024;
		}
	}
	
}
