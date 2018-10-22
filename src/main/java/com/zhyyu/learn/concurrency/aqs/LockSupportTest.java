package com.zhyyu.learn.concurrency.aqs;

import java.util.concurrent.locks.LockSupport;

/**
 * 测试LockSupport
 * <pre>
 * 不同Thread 状态
 * -----------------------------------------------------------
 * 1. Thread.sleep,					TIMED_WAITING(sleeping)
 * 2. A->B, B->A synchronized 死锁,	BLOCKED(on object monitor)
 * 3. wait,							WAITING(on object monitor)
 * 4. LockSupport.park				WAITING(parking)
 * 
 * LockSupport park/unpark
 * -----------------------------------------------------------
 * 1. part, 阻塞当前线程
 * 2. unpark, 解除给定线程阻塞
 * @author zhyyu2016
 *
 */
public class LockSupportTest {

	public static void main(String[] args) throws InterruptedException {
		
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				LockSupport.park();
				System.out.println("hello");
			}
		});
		thread.start();
		
		Thread.sleep(1000L);
		LockSupport.unpark(thread);
	}
	
}
