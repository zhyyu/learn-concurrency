package com.zhyyu.learn.concurrency.wait;

/**
 * 测试wait
 * <pre>
 * CON:
 * 1. wait/nofify/notifyAll 方法必须在同步代码块中, 否则调用即报 IllegalMonitorStateException
 * 2. 同步代码块监视对象必须是实例对象, 不是静态方法的class 对象
 * 3. 监视给定对象的代码块调用notifyAll, 仅会激活监视同一对象wait 方法, 不会激活监视其他对象wait 方法
 * @author zhyyu2016
 *
 */
public class SimpleWaitTest {

	public static void main(String[] args) throws InterruptedException {
		WaitNofifyClass waitNofifyClass = new WaitNofifyClass();
		WaitNofifyClass2 waitNofifyClass2 = new WaitNofifyClass2();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				waitNofifyClass.testWait();
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				waitNofifyClass2.testWait2();
			}
		}).start();
		
		
		Thread.sleep(2000);
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				waitNofifyClass.testNotifyAll();
				// waitNofifyClass2.testNotifyAll2();
			}
		}).start();
	}
	
	public static class WaitNofifyClass {
	
		public synchronized void testWait() {
			try {
				wait();
				System.out.println("wait finished!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public synchronized void testNotifyAll() {
			notifyAll();
		}
		
	}
	
	public static class WaitNofifyClass2 {
		
		public synchronized void testWait2() {
			try {
				wait();
				System.out.println("wait2 finished!");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		public synchronized void testNotifyAll2() {
			notifyAll();
		}
		
	}
	
}
