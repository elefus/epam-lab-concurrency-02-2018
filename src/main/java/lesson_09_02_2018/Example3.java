package lesson_09_02_2018;

import java.util.concurrent.TimeUnit;

public class Example3 {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            try {
                System.out.println("Start 1");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End 1");
        });
        thread.start();


        new Thread(() -> {
            try {
                System.out.println("Start 2");
                synchronized (thread) {
                    thread.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("End 2");
        }).start();
    }
}
