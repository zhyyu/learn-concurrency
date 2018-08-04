package com.zhyyu.learn.concurrency.interrupt;

/**
 * test thread interrupt and resume interrupt status 
 * @author Administrator
 *
 */
public class InterruptTest {

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new InterruptedTask());
		thread.start();
		
		thread.interrupt();
		
		Thread.sleep(1000);
		
		System.out.println(thread.isInterrupted());
	}
	
	static class InterruptedTask implements Runnable {

		@Override
		public void run() {
			try {
				Thread.sleep(10_000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				
				/**
				 * catch InterruptedException 中断状态未设置, 手动设置中断状态, 使其他线程可知道该线程被中断过
				 */
				Thread.currentThread().interrupt();
			}
			
			// 线程必须是存活的, thread.isInterrupted() 才会返回是否中断, 否则一律返回false
			while (true);
		}
		
	}
	
}
