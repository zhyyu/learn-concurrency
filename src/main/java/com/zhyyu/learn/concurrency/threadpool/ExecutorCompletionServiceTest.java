package com.zhyyu.learn.concurrency.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试 executorCompletionService 返回结果顺序和提交任务顺序关系
 * <br>
 * OUTPUT:
 * get calculate result from executor, result: 2048
get calculate result from executor, result: 1024

<br>
CON:
哪个任务先执行完就返回哪个任务执行结果, 和提交任务顺序无关
 * @author Administrator
 *
 */
public class ExecutorCompletionServiceTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executor);
		executorCompletionService.submit(new MyCallable1());
		executorCompletionService.submit(new MyCallable2());
		
		for (int i = 0; i < 2; i++) {
			Integer callableResult = executorCompletionService.take().get();
			System.out.println("get calculate result from executor, result: " + callableResult);
		}
		
	}
	
	public static class MyCallable1 implements Callable<Integer> {
		@Override
		public Integer call() throws Exception {
			Thread.sleep(10_000);
			return 1024;
		}
	}
	
	public static class MyCallable2 implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			Thread.sleep(5_000);
			return 2048;
		}
		
	}
	
}
