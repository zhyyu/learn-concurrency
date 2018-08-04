package com.zhyyu.learn.concurrency.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池任务执行完毕后线程状态为 WAITING (parking), jvm 不退出
 * @author zhyyu
 *
 */
public class ThreadStatusTaksFinishedInThreadPool {

	public static void main(String[] args) {
		// jvm 不退出
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		executorService.submit(new Runnable() {
			@Override
			public void run() {
				System.out.println("task finished");
			}
		});
		
		// jvm 退出
		/*new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("task finished");
			}
		}).start();*/
	}
	
}


//"pool-1-thread-1" #11 prio=5 os_prio=0 tid=0x00000000199b4000 nid=0x11ec waiting on condition [0x000000001a68e000]
//		   java.lang.Thread.State: WAITING (parking)
//		        at sun.misc.Unsafe.park(Native Method)
//		        - parking to wait for  <0x00000000d624c9d8> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
//		        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//		        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
//		        at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
//		        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1074)
//		        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1134)
//		        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
//		        at java.lang.Thread.run(Thread.java:748)