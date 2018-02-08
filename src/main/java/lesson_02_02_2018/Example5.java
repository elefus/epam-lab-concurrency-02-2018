package lesson_02_02_2018;

import java.util.concurrent.TimeUnit;

public class Example5 {

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getState());
        System.out.println("Before sleep");


        Thread thread = new Thread(() -> {
            System.out.println(Thread.currentThread());
            int value = 0;
            while (!Thread.interrupted()) {
                ++value;
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " " + value);
        });
        thread.start();

        TimeUnit.SECONDS.sleep(30);
        thread.interrupt();

        System.out.println("After sleep");
    }
}
