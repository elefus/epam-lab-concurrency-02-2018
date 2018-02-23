package Klimchuk.lesson_19_02_18_ReadWriteLockExhibition;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Writer extends Thread {

    private Storage storage;

    public Writer(Storage storage) {
        this.storage = storage;
    }

    @SneakyThrows
    public void write() {
        TimeUnit.SECONDS.sleep(3);
        storage.write();
    }

    @SneakyThrows
    @Override
    public void run() {
        for (int i = 0; i < 6; i++) {
            write();
        }
    }

}
