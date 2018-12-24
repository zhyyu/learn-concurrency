package com.zhyyu.learn.concurrency.volatile_;

public class SafePublicationWithSynchronizedTest {

	private static MyObj myobj;
	
	public static synchronized void initMyObj() {
		myobj = new MyObj();
	}
	
	public static synchronized MyObj getMyObj() {
		return myobj;
	}
	
	static class MyObj {
	}
	
}
