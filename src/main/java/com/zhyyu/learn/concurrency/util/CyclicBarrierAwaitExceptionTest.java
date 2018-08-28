package com.zhyyu.learn.concurrency.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 测试CyclicBarrierAwaitException
 * <pre>
 * CON:
 * 1. 当前barrier party 线程 timeoutException, 其他已TimeoutException 阻塞线程BrokenBarrierException
 * 2. 未阻塞线程再await, BrokenBarrierException
 * 3. cyclicBarrier isBroken 返回 true
 * @author zhyyu2016
 *
 */
public class CyclicBarrierAwaitExceptionTest {

	private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
		@Override
		public void run() {
			System.out.println("barrier action!");
		}
	});
	
	public static void main(String[] args) throws InterruptedException {
		
		for (int i = 0; i < 10; i++) {
			Thread.sleep(100);
			new Thread(new MyTask(i)).start();
		}
		
		System.out.println(cyclicBarrier.isBroken());
	}
	
	public static class MyTask implements Runnable {

		private int i;
		
		public MyTask(int i) {
			this.i = i;
		}

		@Override
		public void run() {
			try {
				System.out.println("await for: " + i);
				cyclicBarrier.await(300, TimeUnit.MILLISECONDS);
				System.out.println("barrier reached");
			} catch (InterruptedException | BrokenBarrierException e) {
				System.out.println("MyTask InterruptedException/BrokenBarrierException: " + i);
				e.printStackTrace();
			} catch (TimeoutException e) {
				System.out.println("MyTask TimeoutException: " + i);
				e.printStackTrace();
			}
		}
		
	}
	
}
