package com.zhyyu.learn.concurrency.volatile_;

/**
 * 测试volatile 可见性
 * <pre>
 * 测着测着发现该例验证重排序更合适
 * 1. 线程A 执行 initMyObj 方法
 * 		重排序至code1: 0 3 7 4(code2)
 * 2. 线程B 执行 getMyObj
 * 		获取线程A code1:7 引用(myObj), 但引用数据还没初始化完全(未执行code1: 4), 故改对对象(myObj) 状态不完整
 * @author zhyyu2016
 *
 */
public class VolatileVisibilityTest {

	private static MyObj myObj;
	
	public void initMyObj() {
		myObj = new MyObj();
	}
	
	public MyObj getMyObj() {
		return myObj;
	}
	
	static class MyObj {
		
		private int i;
		private int j;
		
		public MyObj() {
			i = 1;
			j = 2;
		}
		public int getI() {
			return i;
		}
		public void setI(int i) {
			this.i = i;
		}
		public int getJ() {
			return j;
		}
		public void setJ(int j) {
			this.j = j;
		}
		
	}
	
}


/* ========================== code1 ========================== */
//public void initMyObj();
//descriptor: ()V
//flags: ACC_PUBLIC
//Code:
//  stack=2, locals=1, args_size=1
//     0: new           #17                 // class com/zhyyu/learn/concurrency/volatile_/VolatileVisibilityTest$MyObj
//     3: dup
//     4: invokespecial #19                 // Method com/zhyyu/learn/concurrency/volatile_/VolatileVisibilityTest$MyObj."<init>":()V
//     7: putstatic     #20                 // Field myObj:Lcom/zhyyu/learn/concurrency/volatile_/VolatileVisibilityTest$MyObj;
//    10: return
//  LineNumberTable:
//    line 13: 0
//    line 14: 10
//  LocalVariableTable:
//    Start  Length  Slot  Name   Signature
//        0      11     0  this   Lcom/zhyyu/learn/concurrency/volatile_/VolatileVisibilityTest;


/* ========================== code2 ========================== */
//public com.zhyyu.learn.concurrency.volatile_.VolatileVisibilityTest$MyObj();
//descriptor: ()V
//flags: ACC_PUBLIC
//Code:
//  stack=2, locals=1, args_size=1
//     0: aload_0
//     1: invokespecial #11                 // Method java/lang/Object."<init>":()V
//     4: aload_0
//     5: iconst_1
//     6: putfield      #13                 // Field i:I
//     9: aload_0
//    10: iconst_2
//    11: putfield      #15                 // Field j:I
//    14: return
//  LineNumberTable:
//    line 25: 0
//    line 26: 4
//    line 27: 9
//    line 28: 14
//  LocalVariableTable:
//    Start  Length  Slot  Name   Signature
//        0      15     0  this   Lcom/zhyyu/learn/concurrency/volatile_/VolatileVisibilityTest$MyObj;
