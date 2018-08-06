package com.zhyyu.learn.concurrency.interrupt;

/**
 * 在阻塞方法执行之前手动设置中断标志
 * <br>
 * OUTPUT:
 * Exception in thread "main" java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at com.zhyyu.learn.concurrency.interrupt.ManualInterruptBeforeBlockMethod.main(ManualInterruptBeforeBlockMethod.java:7)

CON:
阻塞方法不阻塞, 直接抛出InterruptedException 
 * @author Administrator
 *
 */
public class ManualInterruptBeforeBlockMethod {

	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().interrupt();
		Thread.sleep(5000);
	}
	
}
