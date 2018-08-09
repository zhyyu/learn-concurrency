package com.zhyyu.learn.concurrency.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * 命名线程池工作线程, 使用guava ThreadFactoryBuilder
 * <pre>
 * CON:
 * 1. 当未提交工作线程, jvm 退出
 * 2. 当提交1 条工作线程  < 3 线程池 大小, 仅有一条工作线程
 * 3. 当提交5 条工作线程 > 3 线程次大小, 只有三工作线程, 见OUTPUT
 * 4. 使用ThreadFactoryBuilder 命名线程池工作线程, 设置线程名模板, %d 被第几条工作线程替换
 * @author zhyyu2016
 *
 */
public class NamingThreadPoolWorkerThreadTest {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(3, new ThreadFactoryBuilder().setNameFormat("rpc-pool-%d").build());
		
		for (int i = 0; i < 5; i++) {
			executorService.submit(new Runnable() {
				
				@Override
				public void run() {
					System.out.println("hello");
				}
			});
		}
	}
	
}


/**
 * OUTPUT:
 */
//"rpc-pool-2" #12 prio=5 os_prio=0 tid=0x000000001e4db000 nid=0x3100 waiting on condition [0x000000001f42f000]
//   java.lang.Thread.State: WAITING (parking)
//        at sun.misc.Unsafe.park(Native Method)
//        - parking to wait for  <0x000000076b425058> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
//        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
//        at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
//        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1067)
//        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
//        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
//        at java.lang.Thread.run(Thread.java:745)
//
//"rpc-pool-1" #11 prio=5 os_prio=0 tid=0x000000001e4da800 nid=0x428c waiting on condition [0x000000001f2fe000]
//   java.lang.Thread.State: WAITING (parking)
//        at sun.misc.Unsafe.park(Native Method)
//        - parking to wait for  <0x000000076b425058> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
//        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
//        at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
//        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1067)
//        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
//        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
//        at java.lang.Thread.run(Thread.java:745)
//
//"rpc-pool-0" #10 prio=5 os_prio=0 tid=0x000000001e4d9800 nid=0x3ee8 waiting on condition [0x000000001f1af000]
//   java.lang.Thread.State: WAITING (parking)
//        at sun.misc.Unsafe.park(Native Method)
//        - parking to wait for  <0x000000076b425058> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
//        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
//        at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
//        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1067)
//        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
//        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
//        at java.lang.Thread.run(Thread.java:745)