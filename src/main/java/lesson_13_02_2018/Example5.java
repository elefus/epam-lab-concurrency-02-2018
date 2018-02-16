package lesson_13_02_2018;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Example5 {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition condition1 = lock.newCondition();
        Condition condition2 = lock.newCondition();

        System.out.println(((ReentrantLock) lock).isLocked());

        new Thread(() -> {
            try {
                System.out.println("Before 1 lock");
                lock.lock();
                condition1.await();
                System.out.println("After 1 await");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                System.out.println("Before 2 lock");
                lock.lock();
                condition2.await();
                System.out.println("After 2 await");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        TimeUnit.SECONDS.sleep(3);

        lock.lock();
        try {
            condition2.signal();
            System.out.println("Main end");
        } finally {
            lock.unlock();
        }
    }

}
