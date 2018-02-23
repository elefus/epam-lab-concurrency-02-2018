package Klimchuk.lesson_19_02_18_ReadWriteLockExhibition;

import lombok.SneakyThrows;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Storage {

    private final Lock readLock;
    private final Lock writeLock;
    private String data = "DEFAULT DATA";
    

    public Storage() {
        ReadWriteLock lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    @SneakyThrows
    public void read(String name) {
        try {
            readLock.lock();
            TimeUnit.SECONDS.sleep(1);
            System.out.println(data + "; Reader " + name);
        } finally {
            readLock.unlock();
        }
    }

    @SneakyThrows
    public void write() {
        try {
            writeLock.lock();
            TimeUnit.SECONDS.sleep(1);
            this.data = "some number ===> " + ThreadLocalRandom.current().nextInt(0, 100);
            System.out.println("Data has been written");
        } finally {
            writeLock.unlock();
        }
    }
}
