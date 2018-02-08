package lesson_08_02_2018;

import java.util.concurrent.TimeUnit;

@SuppressWarnings("Duplicates")
public class Example5 {

    public static void main(String[] args) {
        Object resource1 = new Object();
        Object resource2 = new Object();


        new Thread(() -> {
            synchronized (resource1) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1 - Got it (resource1)!");
                synchronized (resource2) {
                    System.out.println("1 - Got it (resource2)!");
                }
            }
        }).start();

        new Thread(() -> {
            synchronized (resource2) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("2 - Got it (resource2)!");
                synchronized (resource1) {
                    System.out.println("2 - Got it (resource1)!");
                }
            }
        }).start();

    }
}