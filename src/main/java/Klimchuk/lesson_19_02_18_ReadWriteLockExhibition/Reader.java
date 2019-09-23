package Klimchuk.lesson_19_02_18_ReadWriteLockExhibition;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Reader extends Thread{

    private Storage storage;
    private String name;

    public Reader(Storage storage, String name) {
        this.storage = storage;
        this.name = name;
    }

    @SneakyThrows
    public void read(String name) {
        TimeUnit.MILLISECONDS.sleep(500);
        storage.read(name);
    }

    @Override
    @SneakyThrows
    public void run() {
        for (int i = 0; i < 15; i++) {
            read(name);
        }
    }

}
