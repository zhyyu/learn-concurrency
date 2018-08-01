package com.zhyyu.learn.concurrency.util;

import java.util.concurrent.Exchanger;

/**
 * test Exchanger
 * @author Administrator
 *
 */
public class ExchangerTest {

	private static Exchanger<Integer> exchanger = new Exchanger<>();
	
	public static void main(String[] args) throws InterruptedException {
		new Thread(new ExchangerTask1()).start();
		new Thread(new ExchangerTask2()).start();
	}
	
	public static class ExchangerTask1 implements Runnable {
		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				try {
					Integer exchange = exchanger.exchange(i);
					System.out.println("task1 exchage " + exchange + " from exchanger by " + i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static class ExchangerTask2 implements Runnable {
		@Override
		public void run() {
			for (int i = 9; i >= 0; i--) {
				try {
					Integer exchange = exchanger.exchange(i);
					System.out.println("task2 exchage " + exchange + " from exchanger by " + i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
