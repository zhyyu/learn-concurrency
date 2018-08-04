package com.zhyyu.learn.concurrency.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * future.get timeout 触发工作线程interrupt, 工作线程空闲状态, jstack 为 WAITING (parking), 是否可以接受新任务?
 * TODO
 * 1. 线程池任务完成后线程状态
 * 2. 若工作线程无sleep/wait 操作, 如何响应interrupt
 * @author Administrator
 * @see ThreadStatusTaksFinishedInThreadPool 
 * @see HandleInteruptWithoutSleep
 *
 */
public class FutureGetTimeOutTest {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor(new MyDefaultThreadFactory());
		Future<Integer> future = executor.submit(new MyCallable());
		
		try {
			future.get(1_000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
			boolean cancel = future.cancel(true);
			System.out.println(cancel);
			
			Thread.sleep(5_000);
			System.out.println(future.isDone());
		}
		
	}
	
	public static class MyCallable implements Callable<Integer> {

		@Override
		public Integer call() throws Exception {
			try {
				Thread.sleep(2_000);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("mycallable is interrupted");
			}

			System.out.println("my callable noraml return");
			return 1024;
		}
		
	}
	
	public static class MyDefaultThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MyDefaultThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "myPool-" +
                          poolNumber.getAndIncrement() +
                         "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                                  namePrefix + threadNumber.getAndIncrement(),
                                  0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }
	
}


//"myPool-1-thread-1" #10 prio=5 os_prio=0 tid=0x000000001de2d000 nid=0x9c0 waiting on condition [0x000000001e9be000]
//		   java.lang.Thread.State: WAITING (parking)
//		        at sun.misc.Unsafe.park(Native Method)
//		        - parking to wait for  <0x000000076b3cf4f0> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
//		        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//		        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
//		        at java.util.concurrent.LinkedBlockingQueue.take(LinkedBlockingQueue.java:442)
//		        at java.util.concurrent.ThreadPoolExecutor.getTask(ThreadPoolExecutor.java:1067)
//		        at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1127)
//		        at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
//		        at java.lang.Thread.run(Thread.java:745)
