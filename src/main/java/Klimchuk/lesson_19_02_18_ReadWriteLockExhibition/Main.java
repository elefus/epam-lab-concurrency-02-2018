package Klimchuk.lesson_19_02_18_ReadWriteLockExhibition;

import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        Storage storage = new Storage();

        new Writer(storage).start();

        IntStream.range(0, 4)
                 .mapToObj(i -> new Reader(storage, String.valueOf(i)))
                 .forEach(Thread::start);

    }

}
