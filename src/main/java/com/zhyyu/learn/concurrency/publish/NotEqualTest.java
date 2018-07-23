package com.zhyyu.learn.concurrency.publish;

import java.util.Random;

/**
 * != 线程安全分析
 * <br>
 * OUTPUT:
 * <br>
 * Exception in thread "Thread-1" java.lang.RuntimeException: haha
	at com.zhyyu.learn.concurrency.publish.NotEqualTest.compare(NotEqualTest.java:20)
	at com.zhyyu.learn.concurrency.publish.NotEqualTest$1.run(NotEqualTest.java:37)
	at java.lang.Thread.run(Thread.java:745)

	CON:
	<br>
	n != n 不是线程安全
	
	REASON:
	//         0: aload_0
//         1: getfield      #18                 // Field n:I
//         4: aload_0
//         5: getfield      #18                 // Field n:I
//         8: if_icmpeq     21
 * @author Administrator
 *
 */
public class NotEqualTest {

	private int n;
	
	public void setN(int n) {
		this.n = n;
	}
	
	public void compare() {
		if (n != n) {
			throw new RuntimeException("haha");
		}
	}
	
	public static void main(String[] args) {
		Random random = new Random();
		
		NotEqualTest notEqualTest = new NotEqualTest();
		
		for (int i = 0; i < 10; i ++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					
					while (true) {
						notEqualTest.setN(random.nextInt(Integer.MAX_VALUE));
						notEqualTest.compare();
					}
					
				}
			}).start();
		}
		while (true);
	}
	
}


//F:\workspace-zhyyu-learn\learn-concurrency\target\classes\com\zhyyu\learn\concurrency\publish>javap -p -v NotEqualTest.class
//Classfile /F:/workspace-zhyyu-learn/learn-concurrency/target/classes/com/zhyyu/learn/concurrency/publish/NotEqualTest.class
//  Last modified 2018-7-23; size 1219 bytes
//  MD5 checksum cde64c1e893b703e924d9b4147458c52
//  Compiled from "NotEqualTest.java"
//public class com.zhyyu.learn.concurrency.publish.NotEqualTest
//  minor version: 0
//  major version: 52
//  flags: ACC_PUBLIC, ACC_SUPER
//Constant pool:
//   #1 = Class              #2             // com/zhyyu/learn/concurrency/publish/NotEqualTest
//   #2 = Utf8               com/zhyyu/learn/concurrency/publish/NotEqualTest
//   #3 = Class              #4             // java/lang/Object
//   #4 = Utf8               java/lang/Object
//   #5 = Utf8               n
//   #6 = Utf8               I
//   #7 = Utf8               <init>
//   #8 = Utf8               ()V
//   #9 = Utf8               Code
//  #10 = Methodref          #3.#11         // java/lang/Object."<init>":()V
//  #11 = NameAndType        #7:#8          // "<init>":()V
//  #12 = Utf8               LineNumberTable
//  #13 = Utf8               LocalVariableTable
//  #14 = Utf8               this
//  #15 = Utf8               Lcom/zhyyu/learn/concurrency/publish/NotEqualTest;
//  #16 = Utf8               setN
//  #17 = Utf8               (I)V
//  #18 = Fieldref           #1.#19         // com/zhyyu/learn/concurrency/publish/NotEqualTest.n:I
//  #19 = NameAndType        #5:#6          // n:I
//  #20 = Utf8               compare
//  #21 = Class              #22            // java/lang/RuntimeException
//  #22 = Utf8               java/lang/RuntimeException
//  #23 = String             #24            // haha
//  #24 = Utf8               haha
//  #25 = Methodref          #21.#26        // java/lang/RuntimeException."<init>":(Ljava/lang/String;)V
//  #26 = NameAndType        #7:#27         // "<init>":(Ljava/lang/String;)V
//  #27 = Utf8               (Ljava/lang/String;)V
//  #28 = Utf8               StackMapTable
//  #29 = Utf8               main
//  #30 = Utf8               ([Ljava/lang/String;)V
//  #31 = Class              #32            // java/util/Random
//  #32 = Utf8               java/util/Random
//  #33 = Methodref          #31.#11        // java/util/Random."<init>":()V
//  #34 = Methodref          #1.#11         // com/zhyyu/learn/concurrency/publish/NotEqualTest."<init>":()V
//  #35 = Class              #36            // java/lang/Thread
//  #36 = Utf8               java/lang/Thread
//  #37 = Class              #38            // com/zhyyu/learn/concurrency/publish/NotEqualTest$1
//  #38 = Utf8               com/zhyyu/learn/concurrency/publish/NotEqualTest$1
//  #39 = Methodref          #37.#40        // com/zhyyu/learn/concurrency/publish/NotEqualTest$1."<init>":(Lcom/zhyyu/learn/concurrency/publish/NotEqua
//lTest;Ljava/util/Random;)V
//  #40 = NameAndType        #7:#41         // "<init>":(Lcom/zhyyu/learn/concurrency/publish/NotEqualTest;Ljava/util/Random;)V
//  #41 = Utf8               (Lcom/zhyyu/learn/concurrency/publish/NotEqualTest;Ljava/util/Random;)V
//  #42 = Methodref          #35.#43        // java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
//  #43 = NameAndType        #7:#44         // "<init>":(Ljava/lang/Runnable;)V
//  #44 = Utf8               (Ljava/lang/Runnable;)V
//  #45 = Methodref          #35.#46        // java/lang/Thread.start:()V
//  #46 = NameAndType        #47:#8         // start:()V
//  #47 = Utf8               start
//  #48 = Utf8               args
//  #49 = Utf8               [Ljava/lang/String;
//  #50 = Utf8               random
//  #51 = Utf8               Ljava/util/Random;
//  #52 = Utf8               notEqualTest
//  #53 = Utf8               i
//  #54 = Utf8               SourceFile
//  #55 = Utf8               NotEqualTest.java
//  #56 = Utf8               InnerClasses
//{
//  private int n;
//    descriptor: I
//    flags: ACC_PRIVATE
//
//  public com.zhyyu.learn.concurrency.publish.NotEqualTest();
//    descriptor: ()V
//    flags: ACC_PUBLIC
//    Code:
//      stack=1, locals=1, args_size=1
//         0: aload_0
//         1: invokespecial #10                 // Method java/lang/Object."<init>":()V
//         4: return
//      LineNumberTable:
//        line 21: 0
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0       5     0  this   Lcom/zhyyu/learn/concurrency/publish/NotEqualTest;
//
//  public void setN(int);
//    descriptor: (I)V
//    flags: ACC_PUBLIC
//    Code:
//      stack=2, locals=2, args_size=2
//         0: aload_0
//         1: iload_1
//         2: putfield      #18                 // Field n:I
//         5: return
//      LineNumberTable:
//        line 26: 0
//        line 27: 5
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0       6     0  this   Lcom/zhyyu/learn/concurrency/publish/NotEqualTest;
//            0       6     1     n   I
//
//  public void compare();
//    descriptor: ()V
//    flags: ACC_PUBLIC
//    Code:
//      stack=3, locals=1, args_size=1
//         0: aload_0
//         1: getfield      #18                 // Field n:I
//         4: aload_0
//         5: getfield      #18                 // Field n:I
//         8: if_icmpeq     21
//        11: new           #21                 // class java/lang/RuntimeException
//        14: dup
//        15: ldc           #23                 // String haha
//        17: invokespecial #25                 // Method java/lang/RuntimeException."<init>":(Ljava/lang/String;)V
//        20: athrow
//        21: return
//      LineNumberTable:
//        line 30: 0
//        line 31: 11
//        line 33: 21
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      22     0  this   Lcom/zhyyu/learn/concurrency/publish/NotEqualTest;
//      StackMapTable: number_of_entries = 1
//        frame_type = 21 /* same */
//
//  public static void main(java.lang.String[]);
//    descriptor: ([Ljava/lang/String;)V
//    flags: ACC_PUBLIC, ACC_STATIC
//    Code:
//      stack=6, locals=4, args_size=1
//         0: new           #31                 // class java/util/Random
//         3: dup
//         4: invokespecial #33                 // Method java/util/Random."<init>":()V
//         7: astore_1
//         8: new           #1                  // class com/zhyyu/learn/concurrency/publish/NotEqualTest
//        11: dup
//        12: invokespecial #34                 // Method "<init>":()V
//        15: astore_2
//        16: iconst_0
//        17: istore_3
//        18: goto          43
//        21: new           #35                 // class java/lang/Thread
//        24: dup
//        25: new           #37                 // class com/zhyyu/learn/concurrency/publish/NotEqualTest$1
//        28: dup
//        29: aload_2
//        30: aload_1
//        31: invokespecial #39                 // Method com/zhyyu/learn/concurrency/publish/NotEqualTest$1."<init>":(Lcom/zhyyu/learn/concurrency/publ
//ish/NotEqualTest;Ljava/util/Random;)V
//        34: invokespecial #42                 // Method java/lang/Thread."<init>":(Ljava/lang/Runnable;)V
//        37: invokevirtual #45                 // Method java/lang/Thread.start:()V
//        40: iinc          3, 1
//        43: iload_3
//        44: bipush        10
//        46: if_icmplt     21
//        49: goto          49
//      LineNumberTable:
//        line 36: 0
//        line 38: 8
//        line 40: 16
//        line 41: 21
//        line 52: 37
//        line 40: 40
//        line 54: 49
//      LocalVariableTable:
//        Start  Length  Slot  Name   Signature
//            0      52     0  args   [Ljava/lang/String;
//            8      44     1 random   Ljava/util/Random;
//           16      36     2 notEqualTest   Lcom/zhyyu/learn/concurrency/publish/NotEqualTest;
//           18      31     3     i   I
//      StackMapTable: number_of_entries = 3
//        frame_type = 254 /* append */
//          offset_delta = 21
//          locals = [ class java/util/Random, class com/zhyyu/learn/concurrency/publish/NotEqualTest, int ]
//        frame_type = 21 /* same */
//        frame_type = 250 /* chop */
//          offset_delta = 5
//}
//SourceFile: "NotEqualTest.java"
//InnerClasses:
//     #37; //class com/zhyyu/learn/concurrency/publish/NotEqualTest$1
