package com.zhyyu.learn.concurrency.shutdown;

/**
 * 测试 System.exit, 会正在运行线程
 * @author zhyyu2016
 *
 */
public class SystemExitTest {

	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					System.out.println("run run run");
				}
			}
		}).start();
		
		Thread.sleep(1000);
		
		System.exit(0);
	}
	
}
