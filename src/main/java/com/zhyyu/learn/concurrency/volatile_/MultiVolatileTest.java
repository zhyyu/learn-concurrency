package com.zhyyu.learn.concurrency.volatile_;

/**
 * 多volatile 变量组合条件(不行!)
 * <pre>
 * 例子: b1, b2 均为 volatile 变量, 组合条件为 b1 && b2
 * 
 * b1 && b2 反编译后jvm 指令为
    //     	   0: getstatic     #19                 // Field b1:Z
	//         3: ifeq          20
	//         6: getstatic     #21                 // Field b2:Z
	//         9: ifeq          20
	// 		   12: getstatic     #23                 // Field java/lang/System.out:Ljava/io/PrintStream;
	不为原子性操作, 异常路径:
	1. 0, get static 为true
	2. 此时, #19 对应boolean 更新为false
	3. 6, get static 为true
	4. 执行12(不应执行, 因为#19 对应boolean 已更新为false)
 * @author zhyyu2016
 *
 */
public class MultiVolatileTest {

	private static volatile boolean b1;
	
	private static volatile boolean b2;
	
	public static void main(String[] args) {
		if (b1 && b2) {
			System.out.println("hello");
		}
	}
	
}


//F:\workspace-zhyyu-learn\learn-concurrency\target\classes\com\zhyyu\learn\concurrency\volatile_>javap -p -v MultiVolatileTest.class
//Classfile /F:/workspace-zhyyu-learn/learn-concurrency/target/classes/com/zhyyu/learn/concurrency/volatile_/MultiVolatileTest.class
//  Last modified 2018-12-20; size 715 bytes
//  MD5 checksum 0c54f3b43a9b45540fac4a1ed08ca7c3
//  Compiled from "MultiVolatileTest.java"
//public class com.zhyyu.learn.concurrency.volatile_.MultiVolatileTest
//  minor version: 0
//  major version: 52
//  flags: ACC_PUBLIC, ACC_SUPER
//Constant pool:
//   #1 = Class              #2             // com/zhyyu/learn/concurrency/volatile_/MultiVolatileTest
//   #2 = Utf8               com/zhyyu/learn/concurrency/volatile_/MultiVolatileTest
//   #3 = Class              #4             // java/lang/Object
//   #4 = Utf8               java/lang/Object
//   #5 = Utf8               b1
//   #6 = Utf8               Z
//   #7 = Utf8               b2
//   #8 = Utf8               <init>
//   #9 = Utf8               ()V
//  #10 = Utf8               Code
//  #11 = Methodref          #3.#12         // java/lang/Object."<init>":()V
//  #12 = NameAndType        #8:#9          // "<init>":()V
//  #13 = Utf8               LineNumberTable
//  #14 = Utf8               LocalVariableTable
//  #15 = Utf8               this
//  #16 = Utf8               Lcom/zhyyu/learn/concurrency/volatile_/MultiVolatileTest;
//  #17 = Utf8               main
//  #18 = Utf8               ([Ljava/lang/String;)V
//  #19 = Fieldref           #1.#20         // com/zhyyu/learn/concurrency/volatile_/MultiVolatileTest.b1:Z
//  #20 = NameAndType        #5:#6          // b1:Z
//  #21 = Fieldref           #1.#22         // com/zhyyu/learn/concurrency/volatile_/MultiVolatileTest.b2:Z
//  #22 = NameAndType        #7:#6          // b2:Z
//  #23 = Fieldref           #24.#26        // java/lang/System.out:Ljava/io/PrintStream;
//  #24 = Class              #25            // java/lang/System
//  #25 = Utf8               java/lang/System
//  #26 = NameAndType        #27:#28        // out:Ljava/io/PrintStream;
//  #27 = Utf8               out
//  #28 = Utf8               Ljava/io/PrintStream;
//  #29 = String             #30            // hello
//  #30 = Utf8               hello
//  #31 = Methodref          #32.#34        // java/io/PrintStream.println:(Ljava/lang/String;)V
//  #32 = Class              #33            // java/io/PrintStream
//  #33 = Utf8               java/io/PrintStream
//  #34 = NameAndType        #35:#36        // println:(Ljava/lang/String;)V
//  #35 = Utf8               println
//  #36 = Utf8               (Ljava/lang/String;)V
//  #37 = Utf8               args
//  #38 = Utf8               [Ljava/lang/String;
//  #39 = Utf8               StackMapTable
//  #40 = Utf8               SourceFile
//  #41 = Utf8               MultiVolatileTest.java
//{
//  private static volatile boolean b1;
//    descriptor: Z
//    flags: ACC_PRIVATE, ACC_STATIC, ACC_VOLATILE
//
//  private static volatile boolean b2;
//    descriptor: Z
//    flags: ACC_PRIVATE, ACC_STATIC, ACC_VOLATILE
//
//  public com.zhyyu.learn.concurrency.volatile_.MultiVolatileTest();
//    descriptor: ()V
//    flags: ACC_PUBLIC
//    Code:
//      stack=1, locals=1, args_size=1
//         0: aload_0
//         1: invokespecial #11                 // Method java/lang/Object."<init>":()V
//         4: return
//      LineNumberTable:
//        line 3: 0
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0       5     0  this   Lcom/zhyyu/learn/concurrency/volatile_/MultiVolatileTest;
//
//  public static void main(java.lang.String[]);
//    descriptor: ([Ljava/lang/String;)V
//    flags: ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=2, locals=1, args_size=1
//         0: getstatic     #19                 // Field b1:Z
//         3: ifeq          20
//         6: getstatic     #21                 // Field b2:Z
//         9: ifeq          20
//        12: getstatic     #23                 // Field java/lang/System.out:Ljava/io/PrintStream;
//        15: ldc           #29                 // String hello
//        17: invokevirtual #31                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
//        20: return
//      LineNumberTable:
//        line 10: 0
//        line 11: 12
//        line 13: 20
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      21     0  args   [Ljava/lang/String;
//      StackMapTable: number_of_entries = 1
//        frame_type = 20 /* same */
//}
//SourceFile: "MultiVolatileTest.java"