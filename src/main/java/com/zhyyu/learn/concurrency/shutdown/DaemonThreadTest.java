package com.zhyyu.learn.concurrency.shutdown;

/**
 * 测试 DaemonThread
 * @author zhyyu2016
 *
 */
public class DaemonThreadTest {

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					System.out.println("daemon or not");
				}
			}
		});
		
		// 放开注释, main 线程退出后jvm 退出, 否则jvm 不退出
		// thread.setDaemon(true);
		thread.start();
		Thread.sleep(1000);
	}
	
}
