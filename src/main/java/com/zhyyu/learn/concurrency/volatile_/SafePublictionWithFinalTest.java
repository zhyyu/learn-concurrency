package com.zhyyu.learn.concurrency.volatile_;

/**
 * SafePublictionWithFinalTest
 * 
 * @author zhyyu2016
 *
 */
public class SafePublictionWithFinalTest {

	private final int i;
	private final int j;
	
	public SafePublictionWithFinalTest() {
		i = 1;
		j = 2;
	}

	public int getI() {
		return i;
	}

	public int getJ() {
		return j;
	}
	
}
