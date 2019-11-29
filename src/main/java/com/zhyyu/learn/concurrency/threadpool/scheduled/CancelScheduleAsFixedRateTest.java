package com.zhyyu.learn.concurrency.threadpool.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author juror
 * @datatime 2019/11/29 11:21
 */
public class CancelScheduleAsFixedRateTest {

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(new MyTask(), 0, 1, TimeUnit.SECONDS);

        Thread.sleep(5000L);
        // cancel can terminate
//        scheduledFuture.cancel(true);

        // shutdown can terminate
//        scheduledExecutorService.shutdown();


        // 上一个task throw exception 后, 会释放线程, 可再新提交任务
        ScheduledFuture<?> scheduledFuture2 = scheduledExecutorService.scheduleAtFixedRate(new MyTask(), 3, 1, TimeUnit.SECONDS);
    }

    private static class MyTask implements Runnable {

        private int exeCount = 0;

        @Override
        public void run() {
            System.out.println("hello");
            exeCount++;

            // throw exception can terminate
            if (exeCount == 5) {
                throw new RuntimeException();
            }
        }
    }

}
