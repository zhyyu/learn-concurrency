package com.zhyyu.learn.concurrency.threadpool.poolsize;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.RandomUtils;

/**
 * 测试线程池合适大小
 * <pre>
 * 参考 page141, 设置合适线程池大小为 availableProcessors + 1
 * @author zhyyu
 *
 */
public class PoolSizeTest {

	public static void main(String[] args) throws InterruptedException {
		// optimizedExecutorService
		ExecutorService optimizedExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
		long millis1 = System.currentTimeMillis();
		
		List<Callable<Integer>> myTaskList = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			myTaskList.add(new MyTask());
		}
		optimizedExecutorService.invokeAll(myTaskList);
		
		long millis2 = System.currentTimeMillis();
		System.out.println("optimizedExecutorService spent: " + (millis2 - millis1));
		optimizedExecutorService.shutdown();
		while (!optimizedExecutorService.isShutdown()) {
			;
		}
		
		
		// singleExecutorService
		/*ExecutorService singleExecutorService = Executors.newFixedThreadPool(1);
		long millis3 = System.currentTimeMillis();
		
		List<Callable<Integer>> myTaskList2 = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			myTaskList2.add(new MyTask());
		}
		singleExecutorService.invokeAll(myTaskList2);
		
		long millis4 = System.currentTimeMillis();
		System.out.println("singleExecutorService spent: " + (millis4 - millis3));
		singleExecutorService.shutdown();
		while (!singleExecutorService.isShutdown()) {
			;
		}*/
		
		// hugeExecutorService
		ExecutorService hugeExecutorService = Executors.newFixedThreadPool(1000);
		long millis5 = System.currentTimeMillis();
		
		List<Callable<Integer>> myTaskList3 = new ArrayList<>();
		for (int i = 0; i < 1000; i++) {
			myTaskList3.add(new MyTask());
		}
		hugeExecutorService.invokeAll(myTaskList3);
		
		long millis6 = System.currentTimeMillis();
		System.out.println("hugeExecutorService spent: " + (millis6 - millis5));
		hugeExecutorService.shutdown();
		while (!hugeExecutorService.isShutdown()) {
			;
		}
	}
	
	public static class MyTask implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			int result = 0;
			
			int m = RandomUtils.nextInt(1, Integer.MAX_VALUE);
			for (int i = 0; i < 1000; i++) {
				for (int j = 0; j < 1000; j++) {
					for (int k = 0; k < 1000; k++) {
						m++;
						result += m * m;
					}
				}
			}
			
			return result;
		}
		
	}
	
}



/**
 * 100 任务, huge 100 线程
 */
//optimizedExecutorService spent: 11700
//singleExecutorService spent: 79597
//hugeExecutorService spent: 10573

/**
 * 1000 任务, huge 1000 线程
 */
//optimizedExecutorService spent: 98425
//hugeExecutorService spent: 110117

