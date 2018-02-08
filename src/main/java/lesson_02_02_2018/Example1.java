package lesson_02_02_2018;

import java.util.concurrent.TimeUnit;

public class Example1 {

    public static void main(String[] args) throws Throwable {
//        Thread.setDefaultUncaughtExceptionHandler((t, e) -> System.out.println("In thread " + t + " was caught " + e));
        Thread thread = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                System.out.println("Я проснулся!");
                System.out.println(Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "ThreadName");
        thread.start();


        new MyThread().start();

        System.out.println("Main end");
    }


    private static class MyThread extends Thread {

        public MyThread() {
            super("Really thread");
        }

        @Override
        public void run() {
            System.out.println("I'm really thread!");
        }
    }
}
