package com.zhyyu.learn.concurrency.sleep;

/**
 * thread sleep 测试
 * <pre>
 * 1. 可以不在同步代码块中运行
 * 2. The thread does not lose ownership of any monitors. // 不会丢弃ThreadSleepTest.class monitor, 若在同步代码块运行, 不会丢弃任何 monitor 控制权
 * @author zhyyu2016
 *
 */
public class ThreadSleepTest {

	public static void main(String[] args) throws InterruptedException {
		// 可以不在同步代码块中运行
		//synchronized (ThreadSleepTest.class) {
			Thread.sleep(30_000L);
		//}
	}
	
}

//"main" #1 prio=5 os_prio=0 tid=0x000000000274e800 nid=0x2bc0 waiting on condition [0x000000000375f000]
//		   java.lang.Thread.State: TIMED_WAITING (sleeping)
//		        at java.lang.Thread.sleep(Native Method)
//		        at com.zhyyu.learn.concurrency.sleep.ThreadSleepTest.main(ThreadSleepTest.java:6)


//"main" #1 prio=5 os_prio=0 tid=0x0000000002bae800 nid=0x5c20 waiting on condition [0x00000000036ef000]
//		   java.lang.Thread.State: TIMED_WAITING (sleeping)
//		        at java.lang.Thread.sleep(Native Method)
//		        at com.zhyyu.learn.concurrency.sleep.ThreadSleepTest.main(ThreadSleepTest.java:7)
//		        - locked <0x000000076b3cb7f0> (a java.lang.Class for com.zhyyu.learn.concurrency.sleep.ThreadSleepTest)