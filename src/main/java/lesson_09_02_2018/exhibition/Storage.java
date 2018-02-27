package lesson_09_02_2018.exhibition;


import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Storage {

    private volatile String value = "DEFAULT";
    private volatile Boolean isClosed = false;
    private volatile Integer counter = 0;
    private final Object readerMonitor = new Object();
    private final Object writerMonitor = new Object();

    @SneakyThrows
    String read() {
        if (isClosed) {
            synchronized (writerMonitor) {
                writerMonitor.wait();
            }
        }

        synchronized (readerMonitor) {
            counter++;
        }
        TimeUnit.SECONDS.sleep(1);
        synchronized (readerMonitor) {
            if (--counter == 0) {
                readerMonitor.notify();
            }
            return value;
        }
    }

    @SneakyThrows
    void write(String value) {
        isClosed = true;
        while (counter > 0) {
            synchronized (readerMonitor) {
                TimeUnit.SECONDS.sleep(1);
                readerMonitor.wait();
            }
        }

        TimeUnit.SECONDS.sleep(1);

        this.value = value;

        synchronized (writerMonitor) {
            isClosed = false;
            writerMonitor.notifyAll();
        }

    }
}
