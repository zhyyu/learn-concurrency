package com.zhyyu.learn.concurrency.interrupt;

/**
 * 测试不使用sleep 等方法捕获interrupt
 * <br>
 * 1. Thread.sleep
 * 2. Object.wait
 * 3. Thread.interrupted 均会清除中断状态
 * =====================
 * 4. thread.isInterrupted 不会清除中断状态
 * @author zhyyu
 *
 */
public class HandleInteruptWithoutSleep {

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new MyTask());
		thread.start();
		
		Thread.sleep(1000);
		thread.interrupt();
		
		// 未抛出异常时 false
		Thread.sleep(1000);
		System.out.println("is thread interrupted: " + thread.isInterrupted());
	}
	
	public static class MyTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				if (Thread.interrupted()) {
					System.out.println("my task is interrupted!!!");
					Thread.currentThread().interrupt();
					break;
				}
			}
			
			System.out.println("my task is finished");
			while (true);
		}
		
	}
	
}
