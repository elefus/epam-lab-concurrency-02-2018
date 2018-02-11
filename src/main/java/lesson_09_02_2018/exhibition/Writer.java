package lesson_09_02_2018.exhibition;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Writer extends Thread {

    private final Storage storage ;

    public Writer(Storage storage) {
        super("Writer");
        this.storage = storage;
    }

    @Override
    @SneakyThrows
    public void run() {
        for (int i = 0; i < 3; i++) {
            storage.write(String.valueOf(i));
            TimeUnit.SECONDS.sleep(2);
        }
    }
}
