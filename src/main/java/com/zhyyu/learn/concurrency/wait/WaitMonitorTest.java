package com.zhyyu.learn.concurrency.wait;

/**
 * 测试wait/notifyAll 监视monitor
 * <pre>
 * wait/nofity(all) 方法均需要持有当前对象monitor, 否则 java.lang.IllegalMonitorStateException
 * @author zhyyu2016
 *
 */
public class WaitMonitorTest {
	
	private static final Integer MyLock = new Integer(1);

	public static void main(String[] args) throws InterruptedException {
		MyObj myObj = new MyObj();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					myObj.waitByThisMonitor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					myObj.waitByThisMonitor2();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		
		// 需暂停一段时间, 否则notifyAll 时还未开始wait
		Thread.sleep(1000);
		
		// 两个等待 myObj monitor 线程均执行
		synchronized (myObj) {
			myObj.notifyAll();
		}
	}
	
	static class MyObj {
		// 编译报错, object.wait 为非静态方法, 不能在静态方法中
		public static synchronized void waitByClassMonitor() throws InterruptedException  {
			// wait();
		}
		
		public synchronized void waitByThisMonitor() throws InterruptedException {
			wait();
			System.out.println("waitByThisMonitor");
		}
		
		public void waitByThisMonitor2() throws InterruptedException {
			synchronized (this) {
				wait();
				System.out.println("waitByThisMonitor2");
			}
		}
		
		// java.lang.IllegalMonitorStateException
		public void waitByOtherMonitor() throws InterruptedException {
			synchronized (MyLock) {
				wait();
				System.out.println("waitByOtherMonitor");
			}
		}
		
	}
	
}



//"Thread-1" #14 prio=5 os_prio=0 tid=0x000000001e54a000 nid=0x63bc in Object.wait() [0x000000001fa1f000]
//		   java.lang.Thread.State: WAITING (on object monitor)
//		        at java.lang.Object.wait(Native Method)
//		        - waiting on <0x000000076b3d0e70> (a com.zhyyu.learn.concurrency.wait.WaitMonitorTest$MyObj)
//		        at java.lang.Object.wait(Object.java:502)
//		        at com.zhyyu.learn.concurrency.wait.WaitMonitorTest$MyObj.waitByThisMonitor2(WaitMonitorTest.java:46)
//		        - locked <0x000000076b3d0e70> (a com.zhyyu.learn.concurrency.wait.WaitMonitorTest$MyObj)
//		        at com.zhyyu.learn.concurrency.wait.WaitMonitorTest$2.run(WaitMonitorTest.java:25)
//		        at java.lang.Thread.run(Thread.java:745)
//
//"Thread-0" #13 prio=5 os_prio=0 tid=0x000000001e547000 nid=0x561c in Object.wait() [0x000000001eebf000]
//   java.lang.Thread.State: WAITING (on object monitor)
//        at java.lang.Object.wait(Native Method)
//        - waiting on <0x000000076b3d0e70> (a com.zhyyu.learn.concurrency.wait.WaitMonitorTest$MyObj)
//        at java.lang.Object.wait(Object.java:502)
//        at com.zhyyu.learn.concurrency.wait.WaitMonitorTest$MyObj.waitByThisMonitor(WaitMonitorTest.java:40)
//        - locked <0x000000076b3d0e70> (a com.zhyyu.learn.concurrency.wait.WaitMonitorTest$MyObj)
//        at com.zhyyu.learn.concurrency.wait.WaitMonitorTest$1.run(WaitMonitorTest.java:14)
//        at java.lang.Thread.run(Thread.java:745)
