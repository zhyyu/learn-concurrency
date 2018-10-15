package com.zhyyu.learn.concurrency.threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;

/**
 * {@link https://dzone.com/articles/painless-introduction-javas-threadlocal-storage}
 * @author zhyyu
 *
 */
public class ThreadLocalWithThreadPool {
	
	private static final ThreadLocal<String> context = new ThreadLocal<String>() {
		@Override
		protected String initialValue() {
			return UUID.randomUUID().toString();
		};
	};
	
	private static ExecutorService threadPool = new ThreadPoolExecutor(5, 5,
            0L, TimeUnit.MILLISECONDS,
            new ArrayBlockingQueue<Runnable>(1000));
	
	private static List<String> threadUUIDList = new ArrayList<>();
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			threadPool.submit(new Runnable() {
				@Override
				public void run() {
					String threadUUID = context.get();
					System.out.println("thread uuid: " + threadUUID);
					if (StringUtils.isBlank(threadUUID)) {
						System.out.println("threal local not set");
						return;
					}
					
					boolean contains = threadUUIDList.contains(threadUUID);
					if (contains) {
						System.out.println("reuse old thread");
						return;
					}
					
					threadUUIDList.add(threadUUID);
					
					// 若要ThreadLocal 兼容 threadPool, 需要在线程使用完成后清理线程对应threadLocal value
					// context.remove();
				}
			});
		}
		
		Thread.sleep(2000);
		System.out.println("threadUUIDList size: " + threadUUIDList.size());
	}
	
}



//thread uuid: 8ffeccf5-a0ec-4600-9fe6-043746f2ad6f
//thread uuid: 31e675aa-489a-4360-8afa-880fe7a3d406
//thread uuid: 5b507ce7-8aed-469a-9122-50fd340117f4
//thread uuid: 2c1b283b-232d-48ff-b3f7-63f0df088e80
//thread uuid: f6fa4de1-cd08-4ae7-8015-e56202b272df
//thread uuid: 5b507ce7-8aed-469a-9122-50fd340117f4
//thread uuid: 8ffeccf5-a0ec-4600-9fe6-043746f2ad6f
//thread uuid: 31e675aa-489a-4360-8afa-880fe7a3d406
//reuse old thread
//thread uuid: f6fa4de1-cd08-4ae7-8015-e56202b272df
//reuse old thread
//thread uuid: 2c1b283b-232d-48ff-b3f7-63f0df088e80
//threadUUIDList size: 7

