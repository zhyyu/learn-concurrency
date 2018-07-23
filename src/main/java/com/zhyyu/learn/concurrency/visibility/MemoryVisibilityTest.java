package com.zhyyu.learn.concurrency.visibility;

/**
 * test memory visibility
 * @author Administrator
 * 
 * OUTPUT:42 (not definite wrong)
 *
 */
public class MemoryVisibilityTest {

	private static boolean ready;
	
	private static int num;
	
	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!ready) {
					;
				}
				System.out.println(num);
			}
		}).start();
		
		num = 42;
		ready = true;
	}
	
}
