package lesson_09_02_2018.exhibition;

import java.util.stream.IntStream;

public class Launcher {

    public static void main(String[] args) {
        Storage storage = new Storage();

        new Writer(storage).start();

        IntStream.range(0, 4)
                 .mapToObj(i -> new Reader(storage, String.valueOf("Reader-" + i)))
                 .forEach(Thread::start);
    }
}
