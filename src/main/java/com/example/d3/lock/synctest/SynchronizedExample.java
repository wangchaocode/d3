package com.example.d3.lock.synctest;

import lombok.Data;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author wangchao
 * @Description: Java 锁联系
 * 乐观锁和悲观锁
 * 独享锁和共享锁
 * 互斥锁和读写锁
 * 可重入锁
 * 公平锁和非公平锁
 * 分段锁
 * 偏向锁、轻量级锁和重量级锁
 * @date 2023/9/1 16:22
 * @vVersion 1.0
 */
@Data
public abstract  class SynchronizedExample {
    private int count = 0;
    static SynchronizedExample example;
    /**
     * 加锁，可以独享这段占用。资料利用率低。
     * @param i
     * @param treadName
     */
    public void increment(int i,String treadName) {
        doSomeThing(i);
//        System.out.println(this.getClass().getName()+","+treadName+"，序号："+i+"获取到锁，进行新增:"+getCount());
    }

    public static SynchronizedExample getExample() {
        return example;
    }


    public static void setExample(SynchronizedExample example) {
        SynchronizedExample.example = example;
    }

    public  abstract void doSomeThing(int i);


   public static void  runMyMethod(){
        SynchronizedExample example = getExample();
        final int[] l = {0};
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 5; i++) {
            executor.submit(() -> {
                // 异步执行的代码,使用函数式。
                new Thread(()->{
                    int m=l[0]++;
                    String name=""+Thread.currentThread();
                    System.out.println(m  +"去新增,"+name);
                    example.increment(m,name);

                }
                ).start();
            });

        }
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
       SynchronizedExample sy=new SyncClass();
       /*setExample(sy);


       sy.setExample(new ReenTransClass());
        sy.setExample(new SpinLockDemo());
       */


       sy.setExample(new OptimisticLockDemo());


        runMyMethod();
    }
}
