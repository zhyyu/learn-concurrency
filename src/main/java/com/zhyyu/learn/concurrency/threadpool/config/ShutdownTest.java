package com.zhyyu.learn.concurrency.threadpool.config;

/**
 * TODO 无法关闭Executors, 任务while(true)
 * TODO coreSize = 0, 所有任务完成, jvm 退出
 * <pre>
 * 待测试:
 * 1. shutdown
 * 2. shutdownNow
 * 3. while(true)
 * 4. not while(ture)
 * @author zhyyu2016
 *
 */
public class ShutdownTest {

}
