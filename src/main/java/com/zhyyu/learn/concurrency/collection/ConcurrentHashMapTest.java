package com.zhyyu.learn.concurrency.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 可以并发修改 ConcurrentHashMap, 但 HashMap 不行
 * @author zhyyu
 *
 */
public class ConcurrentHashMapTest {

	private static Map<Integer, Integer> normalMap = new HashMap<>();
	private static Map<Integer, Integer> concurrentMap = new ConcurrentHashMap<>();
	
	static {
		for (int i = 0; i < 100; i++) {
			normalMap.put(i, i);
			concurrentMap.put(i, i);
		}
	}
	
	public static void main(String[] args) {
		
		new Thread(new IterateNormalMapTask()).start();
		new Thread(new IterateConcurrentMapTask()).start();
		new Thread(new RemoveNormalMapTask()).start();
		new Thread(new RemoveConcurrentMapTask()).start();
		
	}
	
	static class IterateNormalMapTask implements Runnable {
		@Override
		public void run() {
			for (Entry<Integer, Integer> entry : normalMap.entrySet()) {
				System.out.println("hashmap iterate, key: " + entry.getKey() + ", value: " + entry.getValue());
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class IterateConcurrentMapTask implements Runnable {
		@Override
		public void run() {
			for (Entry<Integer, Integer> entry : concurrentMap.entrySet()) {
				System.out.println("concurent hashmap iterate, key: " + entry.getKey() + ", value: " + entry.getValue());
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	static class RemoveNormalMapTask implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < 10; i++) {
				normalMap.remove(i);
			}
		}
	}
	
	static class RemoveConcurrentMapTask implements Runnable {
		@Override
		public void run() {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			for (int i = 0; i < 10; i++) {
				concurrentMap.remove(i);
			}
		}
	}
	
}



//hashmap iterate, key: 0, value: 0
//concurent hashmap iterate, key: 0, value: 0
//concurent hashmap iterate, key: 1, value: 1
//hashmap iterate, key: 1, value: 1
//concurent hashmap iterate, key: 2, value: 2
//hashmap iterate, key: 2, value: 2
//hashmap iterate, key: 3, value: 3
//concurent hashmap iterate, key: 3, value: 3
//concurent hashmap iterate, key: 4, value: 4
//hashmap iterate, key: 4, value: 4
//concurent hashmap iterate, key: 5, value: 5
//Exception in thread "Thread-0" java.util.ConcurrentModificationException
//	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1442)
//	at java.util.HashMap$EntryIterator.next(HashMap.java:1476)
//	at java.util.HashMap$EntryIterator.next(HashMap.java:1474)
//	at com.zhyyu.learn.concurrency.collection.ConcurrentHashMapTest$IterateNormalMapTask.run(ConcurrentHashMapTest.java:37)
//	at java.lang.Thread.run(Thread.java:748)
//concurent hashmap iterate, key: 10, value: 10
//concurent hashmap iterate, key: 11, value: 11
//concurent hashmap iterate, key: 12, value: 12
//concurent hashmap iterate, key: 13, value: 13
//concurent hashmap iterate, key: 14, value: 14
//concurent hashmap iterate, key: 15, value: 15

