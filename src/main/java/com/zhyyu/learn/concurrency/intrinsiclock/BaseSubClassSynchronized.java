package com.zhyyu.learn.concurrency.intrinsiclock;

/**
 * 测试子类同步方法监视对象
 * <pre>
 * 1. 使用非静态方法, 监视对象均为子类示例对象
 * 2. 使用静态方法, 监视对象分别: 父类.class, 子类.class
 * @author zhyyu2016
 *
 */
public class BaseSubClassSynchronized {

	public static void main(String[] args) {
		MySubclass mySubclass = new MySubclass();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mySubclass.baseSynchronziedMethod();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mySubclass.subSynchronizedMethod();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mySubclass.baseStaticSynchronziedMethod();
			}
		}).start();
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mySubclass.subStaticSynchronziedMethod();
			}
		}).start();
	}
	
	static class MyBaseClass {
		public synchronized void baseSynchronziedMethod() {
			System.out.println("baseSynchronziedMethod");
			while (true);
		}
		
		public static synchronized void baseStaticSynchronziedMethod() {
			System.out.println("baseStaticSynchronziedMethod");
			while (true);
		}
	}
	
	static class MySubclass extends MyBaseClass {
		public synchronized void subSynchronizedMethod() {
			System.out.println("subSynchronizedMethod");
			while (true);
		}
		
		public static synchronized void subStaticSynchronziedMethod() {
			System.out.println("subStaticSynchronziedMethod");
			while (true);
		}
	}
	
}
