package com.example.d3.lock.synctest;

import com.example.d3.tools.DateTools;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author wangchao
 * @Description: 子类自己去加锁，将自由权下放。
 * @date 2023/9/5 11:19
 * @vVersion 1.0
 */
public class SyncClass extends SynchronizedExample {
    @Override
    public void doSomeThing(int i) {
        doMyM(i);

    }

    /**
     * 这里加锁
     * @param i
     */
    private synchronized void doMyM(int i) {
        super.setCount(super.getCount()+1);
        try {
            int num=i%2*1000;
            Thread.sleep(num);
            DateTools.getIntervalTime(i +"wait ,num:"+num+",now count:"+super.getCount());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * ReentrantLock类实现可重入锁
 */
class ReenTransClass extends SynchronizedExample{
    ReentrantLock reenTranLock = new ReentrantLock();
    @Override
    public void doSomeThing(int i) {
        reenTranLock.lock();
        super.setCount(super.getCount()+1);
        try {
            int num=i%2*1000;
            Thread.sleep(num);
            DateTools.getIntervalTime(i +"wait ,num:"+num+",now count:"+super.getCount());

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        reenTranLock.unlock();
    }
}

/**
 * 可重入读写锁
 */
class ReadWriteLockDemo {
    private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();


    public void readData() {
        rwl.readLock().lock(); // 只允许读取操作获取到读锁，因此需要使用readLock()方法获取读锁
        try {
            // 读取共享资源的操作
        } finally {
            rwl.readLock().unlock(); // 在finally块中释放读锁，即使发生异常也需要释放读锁
        }
    }

    public void writeData() {
        rwl.writeLock().lock(); // 只允许写入操作获取到写锁，因此需要使用writeLock()方法获取写锁
        try {
            // 写入共享资源的操作
        } finally {
            rwl.writeLock().unlock(); // 在finally块中释放写锁，即使发生异常也需要释放写锁
        }
    }
}

/**
 * 自旋锁
 */
class SpinLockDemo extends SynchronizedExample{
    private final ReentrantLock lock = new ReentrantLock();

    public void doSomething(int i) {
        while (true) {
            try {
                lock.lockInterruptibly(); // 尝试获取锁，如果获取失败则进入循环等待
                break;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // 抛出异常时中断当前线程
            }
        }
        try {
            super.setCount(super.getCount()+1);
            try {
                int num=i%2*1000;
                Thread.sleep(num);
                DateTools.getIntervalTime(i +"wait ,num:"+num+",now count:"+super.getCount());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } finally {
            lock.unlock(); // 在finally块中释放锁，即使发生异常也需要释放锁
        }
    }

    @Override
    public void doSomeThing(int i) {
        doSomething(i);
    }
}

/**
 * 乐观锁
 */
class OptimisticLockDemo extends SynchronizedExample{
     AtomicInteger count = new AtomicInteger(0);

    public void increment(int i) {
        System.out.println("i:"+i);
        int currentCount = count.get();
        while (true) {
            int nextCount = currentCount + 1;
            if (count.compareAndSet(currentCount, nextCount)) {
                break;
            } else {
                currentCount = nextCount;
                super.setCount(currentCount);
                int num=i%2*1000;
                try {
                    Thread.sleep(num);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                DateTools.getIntervalTime(i +"wait ,num:"+num+",now count:"+super.getCount());
            }
        }
    }

    @Override
    public void doSomeThing(int i) {
        increment(i);
    }

    public static void main(String[] args) {
        OptimisticLockDemo optimisticLockDemo = new OptimisticLockDemo();
        for (int i = 0; i < 9999; i++) {
            optimisticLockDemo.doSomeThing(i);
        }

    }
}
