package com.zhyyu.learn.concurrency.thread;

/**
 * 测试 join
 * @author zhyyu2016
 * <pre>
 * 1. join() 为阻塞方法
 * 2. 线程结束后释放
 */
public class JoinTest {

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("just wait");
				try {
					Thread.sleep(3000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		thread.start();
		thread.join();
		
		System.out.println("wait die?");
	}
	
}
