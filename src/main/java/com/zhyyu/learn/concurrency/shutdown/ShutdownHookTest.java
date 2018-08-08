package com.zhyyu.learn.concurrency.shutdown;

/**
 * shutdown hook 测试
 * <pre>
 * 1. 程序正常关闭执行(main 方法执行完毕)
 * 2. windows taskkill 命令不执行
 * 3. linux kill -9 wating test
 * 4. Runtime.getRuntime().exit(0) will invoke hook
 * @author zhyyu
 *
 */
public class ShutdownHookTest {

	public static void main(String[] args) throws InterruptedException {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("shutdown hook invoked");
			}
		}));
		
		System.out.println("main invoked");
		Thread.sleep(1000);
		
		Runtime.getRuntime().exit(0);
		while (true);
	}
}
