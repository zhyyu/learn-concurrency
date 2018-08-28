package com.zhyyu.learn.concurrency.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 测试CyclicBarrier, 循环功能, 到达循环之前邓等待到达循环点, 到达后执行barrierAction, 释放等待, 重新循环
 * <pre>
 * OUTPUT:
 * await for: 0
await for: 1
await for: 2
await for: 3
await for: 4
barrier action!
barrier reached
barrier reached
barrier reached
barrier reached
barrier reached
await for: 5
await for: 6
await for: 7
await for: 8
await for: 9
barrier action!
barrier reached
barrier reached
barrier reached
barrier reached
barrier reached

 * @author zhyyu2016
 *
 */
public class CyclicBarrierCycleTest {

	public static void main(String[] args) throws InterruptedException, BrokenBarrierException {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
			@Override
			public void run() {
				System.out.println("barrier action!");
			}
		});
		
		for (int i = 0; i < 10 ; i++) {
			System.out.println("await for: " + i);
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						cyclicBarrier.await();
						System.out.println("barrier reached");
					} catch (InterruptedException | BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			}).start();
			
			Thread.sleep(100);
		}
	}
	
}
