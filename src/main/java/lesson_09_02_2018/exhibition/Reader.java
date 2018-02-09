package lesson_09_02_2018.exhibition;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Reader extends Thread {

    private final Storage storage;

    public Reader(Storage storage, String name) {
        super(name);
        this.storage = storage;
    }

    @Override
    @SneakyThrows
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(storage.read());
            TimeUnit.MILLISECONDS.sleep(500);
        }
    }
}
