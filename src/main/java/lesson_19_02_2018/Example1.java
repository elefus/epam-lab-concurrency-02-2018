package lesson_19_02_2018;

import java.util.concurrent.atomic.AtomicLong;

public class Example1 {

    private static class SynchronizedCounter {

        private volatile long value = 0;

        public long getValue() {
            return value;
        }

        public synchronized long inc() {
            return ++value;
        }
    }

    private static class AtomicCounter {

        private final AtomicLong value = new AtomicLong();

        public long getValue() {
            return value.get();
        }

        public long inc() {
            long val;
            do {
                val = value.get();
            } while (!value.compareAndSet(val, val + 1));
            return val + 1;
        }
    }
}
