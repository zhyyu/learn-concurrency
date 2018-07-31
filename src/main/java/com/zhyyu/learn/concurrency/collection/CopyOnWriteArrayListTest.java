package com.zhyyu.learn.concurrency.collection;

import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.RandomUtils;

/**
 * test CopyOnWriteArrayList
 * @author Administrator
 *
 */
public class CopyOnWriteArrayListTest {

	private static CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
	
	static {
		for (int i = 0; i < 100; i++) {
			copyOnWriteArrayList.add(i);
		}
	}
	
	public static void main(String[] args) {
		new Thread(new IterateTask()).start();
		new Thread(new AddTask()).start();
	}
	
	static class AddTask implements Runnable {

		@Override
		public void run() {
			while (true) {
				int randomInt = RandomUtils.nextInt();
				copyOnWriteArrayList.add(randomInt);
				System.out.println("add into list: " + randomInt);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	static class IterateTask implements Runnable {

		@Override
		public void run() {
			for (Integer integer : copyOnWriteArrayList) {
				System.out.println("integer in list: " + integer);
				
				int size = copyOnWriteArrayList.size();
				System.out.println("list size: " + size);
				
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
}
