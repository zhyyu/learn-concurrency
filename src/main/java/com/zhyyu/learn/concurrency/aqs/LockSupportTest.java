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


//"Thread-0" #11 prio=5 os_prio=0 tid=0x0000000019c34800 nid=0x728 waiting on condition [0x000000001a8cf000]
//		   java.lang.Thread.State: WAITING (parking)
//		        at sun.misc.Unsafe.park(Native Method)
//		        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:304)
//		        at com.zhyyu.learn.concurrency.aqs.LockSupportTest$1.run(LockSupportTest.java:29)
//		        at java.lang.Thread.run(Thread.java:748)
