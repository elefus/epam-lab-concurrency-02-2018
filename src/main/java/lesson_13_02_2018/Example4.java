package lesson_13_02_2018;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Example4 {

    public static void main(String[] args) throws InterruptedException {
        Lock lock = new ReentrantLock();


        lock.lock();


        try {
            System.out.println(((ReentrantLock) lock).isLocked());

            new Thread(() -> {
                try {
                    System.out.println("Before lock");
                    System.out.println(lock.tryLock(5, TimeUnit.SECONDS));
                    System.out.println("After lock");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }).start();

            TimeUnit.SECONDS.sleep(3);

            System.out.println("Main end");
        } finally {
            lock.unlock();
        }
    }

}
