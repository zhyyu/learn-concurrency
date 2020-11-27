package com.zhyyu.learn.concurrency.threadpool.poolsize;

import lombok.*;

import java.util.Date;
import java.util.concurrent.*;

/**
 * 优先填充core, 其次填充queue, 最后填充max
 * core 中执行任务, queue 中不执行(queue 进入core 后执行), max 中执行
 * @author juror
 * @date 2020/11/27
 */
public class OneCoreOneQueueTwoMaxTest {

    public static void main(String[] args) {
        ArrayBlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(1);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 1, TimeUnit.SECONDS, taskQueue);

        threadPoolExecutor.submit(new PrintTask(1));
        threadPoolExecutor.submit(new PrintTask(2));
        threadPoolExecutor.submit(new PrintTask(3));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class PrintTask implements Runnable {

        private int printNum;

        @SneakyThrows
        @Override
        public void run() {
            int count = 0;
            while (true) {
                if (printNum == 1) {
                    if (count == 6) {
                        break;
                    }
                }
                Date date = new Date();
                System.out.println(printNum + "_" + date);
                Thread.sleep(1000);
                count++;
            }
        }

    }

}
