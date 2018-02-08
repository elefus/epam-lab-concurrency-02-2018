package lesson_08_02_2018;

import lombok.SneakyThrows;
import lombok.Synchronized;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;


@SuppressWarnings("Duplicates")
public class Example2 {

    private static class Counter {

        private int value = 100;

        @Synchronized
        private void inc() {
            ++value;
        }

        @Synchronized
        private void dec() {
            --value;
        }

        public int getValue() {
            return value;
        }

        public static synchronized void myMethod() {
            System.out.println("My method");
        }
    }

    @SneakyThrows
    public static void main(String[] args) {
        Counter counter = new Counter();

        Field $lockField = Counter.class.getDeclaredField("$lock");
        $lockField.setAccessible(true);
        Object $lock = $lockField.get(counter);

        synchronized ($lock) {
            new Thread(() -> {
                System.out.println("Before");
                counter.dec();
                System.out.println("After");
            }).start();

            TimeUnit.SECONDS.sleep(5);
            System.out.println("Main end synchro-block");
        }
    }
}