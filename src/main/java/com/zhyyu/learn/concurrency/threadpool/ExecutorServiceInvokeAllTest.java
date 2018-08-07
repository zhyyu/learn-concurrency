package com.zhyyu.learn.concurrency.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.RandomUtils;

/**
 * 测试executorService.invokeAll, 返回futureList 和 callableList 顺序一致, invokeAll 为阻塞方法, 等到所有任务完成
 * <br>
 * Returns:a list of Futures representing the tasks, in the same sequential order as produced by the iterator for the given task list, each of which has completed
 * @author zhyyu
 *
 */
public class ExecutorServiceInvokeAllTest {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		List<Callable<Integer>> callableList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			callableList.add(new MyCallable(i));
		}
		
		System.out.println("try invoke all task");
		long startMillis = System.currentTimeMillis();
		List<Future<Integer>> futureList = executorService.invokeAll(callableList);
		long endMillis = System.currentTimeMillis();
		System.out.println("invoke all spent mills: " + (endMillis - startMillis));
		
		System.out.println("task result:");
		for (Future<Integer> future : futureList) {
			System.out.println(future.get());
		}
	}
	
	public static class MyCallable implements Callable<Integer> {

		private int i;
		
		public MyCallable(int i) {
			this.i = i;
		}

		@Override
		public Integer call() throws Exception {
			Thread.sleep(i * 100);
			return i;
		}
		
	}
	
}
