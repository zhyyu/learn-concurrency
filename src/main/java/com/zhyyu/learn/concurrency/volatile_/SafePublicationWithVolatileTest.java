package com.zhyyu.learn.concurrency.volatile_;

/**
 * SafePublicationWithVolatileTest
 * 
 * @author zhyyu2016
 *
 */
public class SafePublicationWithVolatileTest {

	private static volatile MyObj myObj;

	public static void initMyOjb() {
		myObj = new MyObj();
	}
	
	public static void getMyObj() {
	}
	
	static class MyObj {
	}
	
}
