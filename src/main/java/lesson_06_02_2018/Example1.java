package lesson_06_02_2018;

import java.util.concurrent.TimeUnit;

public class Example1 {



    public static void main(String[] args) throws InterruptedException {
        Thread counter = new Thread(() -> {
            int val = 0;
            for (int i = 0; i < 1_000; ++i) {
                ++val;
            }
            System.out.println("Counter end = " + val);
        });
        counter.start();


//        while (thread.isAlive()) {
//            TimeUnit.MILLISECONDS.sleep(50);
//        }

        counter.join(1);

        System.out.println("After first join = " + counter.getState());

        counter.join();

        System.out.println("After second join = " + counter.getState());

        System.out.println("Main end");
    }
}
