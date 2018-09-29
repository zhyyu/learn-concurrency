package com.zhyyu.learn.concurrency.intrinsiclock;

/**
 * 保护多变量反模式, 访问变量不同方式使用不同锁, 故非线程安全
 * @author zhyyu
 * <pre>
 * 1. getAPlusB 由this 保护
 * 2. incrA 由ProtectMultiStatusAntiDemo.class 保护
 */
public class ProtectMultiStatusAntiDemo {

	private Integer statusA;
	
	private Integer statusB;
	
	public synchronized Integer getAPlusB() {
		return statusA + statusB;
	}
	
	public synchronized Integer getAMuliB() {
		return statusA * statusB;
	}
	
	public void incrA() {
		synchronized (ProtectMultiStatusAntiDemo.class) {
			statusA++;
		}
	}
	
	public void incrB() {
		synchronized (ProtectMultiStatusAntiDemo.class) {
			statusB++;
		}
	}
	
}
