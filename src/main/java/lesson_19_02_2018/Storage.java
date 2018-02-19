package lesson_19_02_2018;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Storage {

    private volatile String value = "DEFAULT";
    private final Lock readerLock;
    private final Lock writerLock;

    public Storage() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        writerLock = lock.writeLock();
        readerLock = lock.readLock();
    }


    String read() {
        try {
            readerLock.lock();
            TimeUnit.SECONDS.sleep(1);
            return value;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            readerLock.unlock();
        }
    }

    @SneakyThrows
    void write(String value) {
        try {
            writerLock.lock();
            this.value = value;
        } finally {
            writerLock.unlock();
        }
    }
}