package lesson_09_02_2018.exhibition;


import java.sql.Time;
import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Storage {

    private String value = "DEFAULT";

    private Boolean isUnderService = true;

    private final Object serviceLock = true;

    private final Object storageLock = true;

    private volatile Integer readerCounter = 0;

    String read() throws InterruptedException {
        System.err.println("--->" + Thread.currentThread().getName() + " came for exhibition");
        if (isUnderService) {
            System.err.println(Thread.currentThread().getName() + " wait for service");
            synchronized (serviceLock) {
                serviceLock.wait();
            }
        }

        synchronized (storageLock) {
            ++readerCounter;
            System.err.println(">>>" + Thread.currentThread().getName() + " enter exhibition");
        }
        TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(500, 1500));
        synchronized (storageLock) {
            System.err.println("---" + Thread.currentThread().getName() + " read value = " + this.value + " ,reader counter = " + readerCounter);
            --readerCounter;
            System.err.println("<<<" + Thread.currentThread().getName() + " leave exhibition and notify writer");
            storageLock.notify();
            return value;
        }
    }

    void write(String value) throws InterruptedException {
        isUnderService = true;
        System.err.println("--->" + Thread.currentThread().getName() + " came for service" + " ,reader counter " + readerCounter);
        while (readerCounter > 0) {
            synchronized (storageLock) {
                System.err.println(Thread.currentThread().getName() + " wait for readers");
                storageLock.wait();
            }
        }

        TimeUnit.SECONDS.sleep(1);
        this.value = value;
        System.err.println(">>>" + Thread.currentThread().getName() + " enter exhibition");
        System.err.println("+++" + Thread.currentThread().getName() + " set value = " + this.value + " ,reader counter " + readerCounter);
        synchronized (serviceLock) {
            System.err.println("<<<" + Thread.currentThread().getName() + " leave exhibition and notify readers");
            isUnderService = false;
            serviceLock.notifyAll();
        }
    }
}
