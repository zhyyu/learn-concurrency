package com.zhyyu.learn.concurrency.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 测试提交任务给已关闭线程池
 * <br>
 * Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task java.util.concurrent.FutureTask@33909752 rejected from java.util.concurrent.ThreadPoolExecutor@55f96302[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 0]
	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2047)
	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:823)
	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1369)
	at java.util.concurrent.AbstractExecutorService.submit(AbstractExecutorService.java:112)
	at com.zhyyu.learn.concurrency.threadpool.SubmitTask2ShutdownPool.main(SubmitTask2ShutdownPool.java:12)
 * @author Administrator
 *
 */
public class SubmitTask2ShutdownPool {

	public static void main(String[] args) {
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.shutdown();
		
		executorService.submit(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("my task running");
			}
		});
	}
	
}
