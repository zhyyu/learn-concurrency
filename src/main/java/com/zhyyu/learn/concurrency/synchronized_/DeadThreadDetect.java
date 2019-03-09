package com.zhyyu.learn.concurrency.synchronized_;

/**
 * 死锁检测程序
 * @author zhongyuyu
 *
 */
public class DeadThreadDetect {

	private static final Integer LOCK_A = 1;
	private static final Integer LOCK_B = 2;
	
	public static void main(String[] args) {
		Thread thread1 = new Thread(new Task1());
		Thread thread2 = new Thread(new Task2());
		
		thread1.start();
		thread2.start();
		
		while (true);
	}
	
	private static class Task1 implements Runnable {

		@Override
		public void run() {
			while (true) {
				synchronized (LOCK_A) {
					synchronized (LOCK_B) {
						;
					}
				}
			}
		}
		
	}
	
	private static class Task2 implements Runnable {

		@Override
		public void run() {
			while (true) {
				synchronized (LOCK_B) {
					synchronized (LOCK_A) {
						;
					}
				}
			}
		}
		
	}
	
}


//"Thread-1" #14 prio=5 os_prio=0 tid=0x000000001e76c000 nid=0x5ad0 waiting for monitor entry [0x000000001fdbf000]
//		   java.lang.Thread.State: BLOCKED (on object monitor)
//		        at com.zhyyu.learn.jvm.tool.DeadThreadDetect$Task2.run(DeadThreadDetect.java:44)
//		        - waiting to lock <0x000000076b37b590> (a java.lang.Integer)
//		        - locked <0x000000076b37b5a0> (a java.lang.Integer)
//		        at java.lang.Thread.run(Thread.java:745)
//
//"Thread-0" #13 prio=5 os_prio=0 tid=0x000000001e762800 nid=0x2974 waiting for monitor entry [0x000000001fc4f000]
//   java.lang.Thread.State: BLOCKED (on object monitor)
//        at com.zhyyu.learn.jvm.tool.DeadThreadDetect$Task1.run(DeadThreadDetect.java:29)
//        - waiting to lock <0x000000076b37b5a0> (a java.lang.Integer)
//        - locked <0x000000076b37b590> (a java.lang.Integer)
//        at java.lang.Thread.run(Thread.java:745)
