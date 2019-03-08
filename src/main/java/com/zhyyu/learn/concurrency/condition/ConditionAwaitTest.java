package com.zhyyu.learn.concurrency.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 测试condition await
 * <pre>
 * 1. await 之前必须持有condition 相关lock, 否则  IllegalMonitorStateException
 * 2. await 切记不与wait 混淆, 否则可能产生未知结果
 * 
 * ------------------------
 * CON:
 * 与object.wait() 条件队列基本相同, 不同点:
 * 1. object.wait 是使用this 作为锁, 生成唯一条件队列. 想当于"object.exclusiveCondition.await object.exclusiveCondition.signal" 
 * 2. Reentrantant 是自定义锁, 通过newCondion 生成自定义条件队列, 每一个条件队列仅接收该条件队列的signal 消息
 * 
 * 相同点:
 * 1. 均需要条件谓词
 * 2. 条件谓词必须由lock 保护
 * 3. while(true)
 * 4. await, signal, signalAll 均需在lock 块
 * 
 * @author zhyyu2016
 *
 */
public class ConditionAwaitTest {

	public static void main(String[] args) throws InterruptedException {
		MyObject myObject = new MyObject();
		// myObject.condition1AwaitWithoutLock();
		myObject.condition1AwaitWithLock();
	}
	
	static class MyObject {
		
		private static final ReentrantLock reentrantLock = new ReentrantLock();
		private static final Condition condition1 = reentrantLock.newCondition();
		
		public void condition1AwaitWithoutLock() throws InterruptedException {
			condition1.await();
		}
		
		public void condition1AwaitWithLock( ) throws InterruptedException {
			reentrantLock.lock();
			condition1.await();
		}
		
	}
	
}


//"main" #1 prio=5 os_prio=0 tid=0x0000000003503000 nid=0x262c waiting on condition [0x000000000318e000]
//		   java.lang.Thread.State: WAITING (parking)
//		        at sun.misc.Unsafe.park(Native Method)
//		        - parking to wait for  <0x00000000d6252bc8> (a java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject)
//		        at java.util.concurrent.locks.LockSupport.park(LockSupport.java:175)
//		        at java.util.concurrent.locks.AbstractQueuedSynchronizer$ConditionObject.await(AbstractQueuedSynchronizer.java:2039)
//		        at com.zhyyu.learn.concurrency.condition.ConditionAwaitTest$MyObject.condition1AwaitWithLock(ConditionAwaitTest.java:46)
//		        at com.zhyyu.learn.concurrency.condition.ConditionAwaitTest.main(ConditionAwaitTest.java:32)
