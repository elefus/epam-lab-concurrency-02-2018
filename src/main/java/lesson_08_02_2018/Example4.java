package lesson_08_02_2018;

@SuppressWarnings("Duplicates")
public class Example4 {

    private static class Counter {

        private int value = 100;

        private synchronized void inc() {
            ++value;
        }

        private synchronized void dec() {
            --value;
            inc();
        }

        public int getValue() {
            return value;
        }
    }

    public static void main(String[] args) {
        new Counter().dec();


    }
}