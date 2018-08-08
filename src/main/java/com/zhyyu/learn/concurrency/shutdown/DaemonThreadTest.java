package com.zhyyu.learn.concurrency.shutdown;

/**
 * 测试 DaemonThread
 * <br>
 * CON:
 * 1. 工作线程结束, 若剩余线程全是守护线程, 则"抛弃"守护线程, 关闭jvm
 * 2. 线程守护状态继承创建该线程的守护状态, 如下main 创建thread, thread 默认守护状态为false
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
