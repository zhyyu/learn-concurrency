package com.zhyyu.learn.concurrency.volatile_;

public class SafePublicationWithEagerInitTest {

	private static MyObj myobj = new MyObj();
	
	public static MyObj getMyObj() {
		return myobj;
	}
	
	static class MyObj {
	}
	
}
