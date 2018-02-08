package lesson_07_02_2018;

import lombok.Synchronized;

import java.util.Objects;

@SuppressWarnings("Duplicates")
public class Example2 {

    private static volatile Integer value = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread inc = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                inc();
            }
        });

        Thread dec = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                dec();
            }
        });

        inc.start();
        dec.start();

        inc.join();
        dec.join();

        System.out.println(value);
    }

    private static void inc() {
        synchronized (Example2.class) {
            ++value;
        }
    }

    private synchronized static void dec() {
        --value;
    }

    private synchronized void method1() {
        // 1
        // 2
        // 3 <-
        // 4
    }

    private final Object monitor = new Object();

    private void method2() {
        // 1
        // 2
        synchronized (monitor) {
            // 3 <-
        }
        // 4
    }
}