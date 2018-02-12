package lesson_12_02_2018;

import java.util.concurrent.TimeUnit;

public class Example4 {

    public static void main(String[] args) throws InterruptedException {
        Object monitor = new Object();

        Thread thread = new Thread(() -> {
            synchronized (monitor) {
                try {
                    System.out.println("Start");

                    TimeUnit.SECONDS.sleep(3);

                    System.out.println("Between");

                    TimeUnit.SECONDS.sleep(3);

                    System.out.println("End");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.suspend();

        synchronized (monitor) {
            System.out.println("Monitor");
        }

        TimeUnit.SECONDS.sleep(1);
        thread.resume();
    }
}
