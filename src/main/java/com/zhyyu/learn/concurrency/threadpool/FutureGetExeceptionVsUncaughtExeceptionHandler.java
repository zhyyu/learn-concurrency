package com.zhyyu.learn.concurrency.threadpool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池通过设置  ThreadFactory 中线程 UncaughtExceptionHandler 在通过ExecutorService.submit 任务无效, 异常从future.get 抛出
 * @author zhyyu
 *
 */
public class FutureGetExeceptionVsUncaughtExeceptionHandler {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool(new MyThreadFactory());
		Future<Integer> future = executorService.submit(new Callable<Integer>() {

			@Override
			public Integer call() throws Exception {
				return 1/0;
			}
			
		});
		
		try {
			future.get();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			System.out.println("future get ExecutionException: " + e);
			e.printStackTrace();
		}
	}
	
	
	public static class MyThreadFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MyThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                                  Thread.currentThread().getThreadGroup();
            namePrefix = "mypool-" +
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
            
            /*=========== setUncaughtExceptionHandler ===========*/
            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
				@Override
				public void uncaughtException(Thread t, Throwable e) {
					System.out.println("handler exception thread: " + t);
					System.out.println("handler exception throwable " + e);
				}
			});
            /*===================================================*/
            
            return t;
        }
    }
}






//future get ExecutionException: java.util.concurrent.ExecutionException: java.lang.ArithmeticException: / by zero
//java.util.concurrent.ExecutionException: java.lang.ArithmeticException: / by zero
//	at java.util.concurrent.FutureTask.report(FutureTask.java:122)
//	at java.util.concurrent.FutureTask.get(FutureTask.java:192)
//	at com.zhyyu.learn.concurrency.threadpool.FutureGetExeceptionVsUncaughtExeceptionHandler.main(FutureGetExeceptionVsUncaughtExeceptionHandler.java:30)
//Caused by: java.lang.ArithmeticException: / by zero
//	at com.zhyyu.learn.concurrency.threadpool.FutureGetExeceptionVsUncaughtExeceptionHandler$1.call(FutureGetExeceptionVsUncaughtExeceptionHandler.java:24)
//	at com.zhyyu.learn.concurrency.threadpool.FutureGetExeceptionVsUncaughtExeceptionHandler$1.call(FutureGetExeceptionVsUncaughtExeceptionHandler.java:1)
//	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
//	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
//	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
//	at java.lang.Thread.run(Thread.java:745)

