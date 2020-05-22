package com.zhyyu.learn.concurrency.threadpool.argpass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * output: 1 2 3 4 5 6 999 7 8 9
 * <pre>
 *     CON: list1 处理完毕 等待1s, put list2 待处理, put list3 待处理(不会修改list2 处理的引用至list3)
 * </pre>
 *
 * @author juror
 * @datatime 2020/5/22 16:30
 */
public class ArgPassTest {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(1);

    public static void main(String[] args) {
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        List<String> list2 = new ArrayList<>();
        list2.add("4");
        list2.add("5");
        list2.add("6");

        List<String> list3 = new ArrayList<>();
        list3.add("7");
        list3.add("8");
        list3.add("9");

        asynHandle(list1);

        asynHandle(list2);
        list2.add("999");

        asynHandle(list3);
    }

    private static void asynHandle(List<String> list) {
        threadPool.submit(new Runnable() {
            @Override
            public void run() {
                for (String s : list) {
                    System.out.println(s);
                }

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}