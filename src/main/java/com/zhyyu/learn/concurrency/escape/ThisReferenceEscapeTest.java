package com.zhyyu.learn.concurrency.escape;

/**
 * output:
 * <br>
 * i in initial: 0
i after initial: 2

	CON:
	<br>
	构造方法中使用内部内会使还未构造号的对象引用溢出
 * @author Administrator
 *
 */
public class ThisReferenceEscapeTest {
	
	private int i = 0;
	
	public ThisReferenceEscapeTest() {
		InnerClass innerClass = new InnerClass();
		innerClass.printOutterI();
		i = 2;
	}

	public static void main(String[] args) {
		ThisReferenceEscapeTest thisReferenceEscapeTest = new ThisReferenceEscapeTest();
		System.out.println("i after initial: " + thisReferenceEscapeTest.getI());
	}
	
	class InnerClass {
		
		void printOutterI() {
			System.out.println("i in initial: " + ThisReferenceEscapeTest.this.i);
		}
		
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
	
}
