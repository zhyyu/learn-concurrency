package com.zhyyu.learn.concurrency.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * test CyclicBarrier
 * <br>
 * OUTPUT:
 * all square thread started
use barrier count squre sum: 5
all squre thread died

 * @author Administrator
 *
 */
public class CyclicBarrierTest {
	
	private static final Integer BARRIER_PARTIES_SIZE = 3;
	private static CyclicBarrier cyclicBarrier = new CyclicBarrier(BARRIER_PARTIES_SIZE, new BarrierAction());
	private static Integer[] squareArray = new Integer[BARRIER_PARTIES_SIZE];
	private static List<Thread> squareThreadList = new ArrayList<>();
	
	static {
		for (int i = 0; i < BARRIER_PARTIES_SIZE; i++) {
			squareThreadList.add(new Thread(new SquareTask(i)));
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		for (Thread squareThread : squareThreadList) {
			squareThread.start();
		}
		
		System.out.println("all square thread started");
		
		for (Thread squareThread : squareThreadList) {
			squareThread.join();
		}
		
		System.out.println("all squre thread died");
	}
	
	public static class BarrierAction implements Runnable {

		@Override
		public void run() {
			Integer squareSum = 0;
			
			for (Integer square : squareArray) {
				squareSum += square;
			}
			
			System.out.println("use barrier count squre sum: " + squareSum);
		}
		
	}
	
	public static class SquareTask implements Runnable {

		private int i;
		
		public SquareTask(int i) {
			this.i = i;
		}
		
		@Override
		public void run() {
			Integer square = i * i;
			squareArray[i] = square;
			
			try {
				cyclicBarrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
