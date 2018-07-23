package com.zhyyu.learn.concurrency.escape;

import java.util.Arrays;

/**
 * output:
 * [one, two, three]
[one, two, xxx]
	CON:ArrayStatus.getStatus() 使内部状态逸出, 破坏封装性
	
 * @author Administrator
 *
 */
public class ArrayEscapeTest {

	public static void main(String[] args) {
		String[] status = ArrayStatus.getStatus();
		ArrayStatus.printStatus();
		
		status[2] = "xxx";
		ArrayStatus.printStatus();
	}
	
	public static class ArrayStatus {
		private static String[] status = {"one", "two", "three"};
		
		public static String[] getStatus() {
			return status;
		}

		public static void printStatus() {
			System.out.println(Arrays.asList(status));
		}
		
	}
	
}
