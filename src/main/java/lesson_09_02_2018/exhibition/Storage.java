package lesson_09_02_2018.exhibition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Storage {

    private final Lock readLock;
    private final Lock writeLock;
    private volatile String value = "DEFAULT";

    Storage() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        writeLock = lock.writeLock();
        readLock = lock.readLock();
    }

    String read() {
        try {
            readLock.lock();
            TimeUnit.SECONDS.sleep(1);
            return value;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            readLock.unlock();
        }
    }

    void write(String value) {
        try {
            writeLock.lock();
            this.value = value;
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            writeLock.unlock();
        }
    }
}