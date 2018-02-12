package lesson_09_02_2018;

public class Example1 {

    private static volatile long value = 0;

    public static void main(String[] args) throws InterruptedException {
        Object monitor = new Object();
        Thread counter = new Thread(() -> {
            for (int i = 0; i < 1_000_000; ++i) {
                if (value == 500_000) {
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
                value++;
            }
            System.out.println("Counter end");
        });
        counter.start();

        while (value != 500_000) {
        }

        synchronized (monitor) {
            while (value != 500_000) {
                monitor.wait();
            }
        }

        System.out.println(value);

    }
}
