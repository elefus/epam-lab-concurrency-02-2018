package lesson_06_02_2018;

import java.util.concurrent.TimeUnit;

public class Example2 {



    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            int val = 0;
            try {
                while (true) {
                    try {
                        System.out.println(++val);
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                System.out.println("123");
            }
        });
        thread.setDaemon(true);
        thread.start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println("Main end");
    }
}
