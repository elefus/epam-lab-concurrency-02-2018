package lesson_02_02_2018;

import java.util.concurrent.TimeUnit;

public class Example2 {

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getId());
            int counter = 0;
            while (true) {
                System.out.println(counter++);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Counter").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getId());
            int counter = 0;
            while (true) {
                System.out.println(counter++);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Counter").start();

    }

}
