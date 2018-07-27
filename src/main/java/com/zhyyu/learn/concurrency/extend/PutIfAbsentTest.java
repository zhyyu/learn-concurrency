package com.zhyyu.learn.concurrency.extend;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.RandomUtils;

/**
 * 测试外部加锁, 锁不一致造成线程安全问题
 * <br>
 * OUTPUT:
 * <br>
 * Exception in thread "Thread-5" java.lang.RuntimeException: putIfAbsent not thread safe!!!
	at com.zhyyu.learn.concurrency.extend.PutIfAbsentTest.putIfAbsent(PutIfAbsentTest.java:29)
	at com.zhyyu.learn.concurrency.extend.PutIfAbsentTest$PutIfAbsentTask.run(PutIfAbsentTest.java:56)
	at java.lang.Thread.run(Thread.java:745)

CON:
putIfAbsent 锁对象为 PutIfAbsentTest.class
Collections.synchronizedSet 锁对象为 synchronizedSet
两把锁不一致, 故putIfAbsent synchronizedSet.add 两方法冲突

 * @author Administrator
 *
 */
public class PutIfAbsentTest {

	public static Set<Integer> synchronizedSet = Collections.synchronizedSet(new HashSet<>());
	
	public static void main(String[] args) {
		
		// 使用 synchronized  加锁
		for (int i = 0; i < 20; i++) {
			new Thread(new PutIfAbsentTask()).start();
		}
		
		// 使用 synchronizedSet 加锁
		for (int i = 0; i < 20; i++) {
			new Thread(new NormalPutTask()).start();
		}
		
	}
	
	public static synchronized void putIfAbsent(Integer integer)  {
		if (!synchronizedSet.contains(integer)) {
			boolean noExist = synchronizedSet.add(integer);
			if (!noExist) {
				throw new RuntimeException("putIfAbsent not thread safe!!!");
			}
		}
	}
	
	public static void normalPut(Integer integer) {
		synchronizedSet.add(integer);
	}
	
	static class NormalPutTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				int randomInt = RandomUtils.nextInt(0, 1024 * 1024);
				normalPut(randomInt);
			}
		}
		
	}
	
	static class PutIfAbsentTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				int randomInt = RandomUtils.nextInt(0, 1024 * 1024);
				putIfAbsent(randomInt);
			}
		}
		
	}
	
}
