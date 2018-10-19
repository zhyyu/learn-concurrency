package com.zhyyu.learn.concurrency.wait;

/**
 * 测试nofityAll
 * <pre>
 * 1. nofity 通知监视同一monitor arbitrary wait 线程, 其他线程WAITING 状态
 * 2. nofity 通知监视同一monitor 所有 wait 线程, 只有一个线程可执行, 其余 BLOCKED 状态, RUNNABLE 线程执行完毕后, BLOCKED 线程竞争获取monitor 以执行
 * @author zhyyu2016
 *
 */
public class NotifyAllTest {

	public static void main(String[] args) throws InterruptedException {
		MyObject myObject = new MyObject();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					myObject.synchronizedMethod1();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					myObject.synchronizedMethod2();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					myObject.synchronizedMethod3();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		Thread.sleep(1000L);
		// myObject.myNotify();
		myObject.myNotifyAll();
	}
	
	static class MyObject {
		
		public synchronized void synchronizedMethod1() throws InterruptedException {
			wait();
			System.out.println("synchronizedMethod1");
			while (true);
		}
		
		public synchronized void synchronizedMethod2() throws InterruptedException {
			wait();
			System.out.println("synchronizedMethod2");
			while (true);
		}
		
		public synchronized void synchronizedMethod3() throws InterruptedException {
			wait();
			System.out.println("synchronizedMethod3");
			while (true);
		}
		
		public synchronized void myNotify() {
			notify();
		}
		
		public synchronized void myNotifyAll() {
			notifyAll();
		}
	}
	
}
