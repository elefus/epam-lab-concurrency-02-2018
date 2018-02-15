package lesson_09_02_2018.exhibition;

import lombok.SneakyThrows;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Storage {

    private volatile String value = "DEFAULT";
    private final Semaphore semaphore = new Semaphore(Integer.MAX_VALUE, true);

    String read() {
        try {
            semaphore.acquire();
            TimeUnit.SECONDS.sleep(1);
            return value;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            semaphore.release();
        }
    }

    @SneakyThrows
    void write(String value) {
        try {
            semaphore.acquire(Integer.MAX_VALUE);
            this.value = value;
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        } finally {
            semaphore.release(Integer.MAX_VALUE);
        }
    }
}