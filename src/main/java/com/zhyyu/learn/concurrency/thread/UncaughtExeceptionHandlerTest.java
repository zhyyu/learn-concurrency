package com.zhyyu.learn.concurrency.thread;

/**
 * 测试 UncaughtExceptionHandler
 * @author zhyyu
 *
 */
public class UncaughtExeceptionHandlerTest {

	public static void main(String[] args) {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				throw new RuntimeException("my runtime exception");
			}
		});
		
		thread.setName("my thread");
		thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println("handler exception thread: " + t);
				System.out.println("handler exception throwable " + e);
			}
		});
		
		thread.start();
	}
	
}
