package com.example.d3.exercise;

import java.time.LocalDateTime;

/**
 * @author wangchao
 * @date 2023/8/14 15:34:54
 */
public class ThreadTest {
    //    #### 锁释放时机代码演示
    public static void main(String[] args) {
        Object o = new Object();
        Thread thread = new Thread(() -> {
            synchronized (o) {
                System.out.println("新线程获取锁时间：  " + LocalDateTime.now() + " 新线 程名称：  " + Thread.currentThread().getName());
                try {

                    //wait 释放cpu 同时释放锁
                    o.wait(2000);

//sleep 释放 cpu 不释放锁
//Thread.sleep(2000);
                    System.out.println("新线程获取释放锁锁时间：  " + LocalDateTime.now() + " 新线程名称：  " + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("主线程获取锁时间：  " + LocalDateTime.now() + " 主线程名称：  " + Thread.currentThread().getName());

        synchronized (o) {
            System.out.println("主线程获取释放锁锁时间：  " + LocalDateTime.now() + " 主线程名称：  " + Thread.currentThread().getName());
        }
    }
}
