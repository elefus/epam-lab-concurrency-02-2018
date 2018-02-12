package lesson_09_02_2018.exhibition;

import lombok.SneakyThrows;

import java.util.concurrent.TimeUnit;

public class Writer extends Thread {

    private final Storage storage ;

    Writer(Storage storage) {
        this.storage = storage;
    }

    @Override
    @SneakyThrows
    public void run() {
        for (int i = 0; i < 5; i++) {
            storage.write(String.valueOf(i));
            System.out.println("The writer wrote: " + String.valueOf(i));
            TimeUnit.SECONDS.sleep(3);
        }
    }
}
