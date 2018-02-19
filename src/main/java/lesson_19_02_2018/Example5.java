package lesson_19_02_2018;

import java.util.concurrent.locks.*;

public class Example5 {

    public static void main(String[] args) {
        Lock lock = new ReentrantLock();


        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();

        StampedLock stampedLock = new StampedLock();

    }
}
