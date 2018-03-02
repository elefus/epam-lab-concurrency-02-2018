package lesson_16_02_2018;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Race {

    public static final CountDownLatch startLatch = new CountDownLatch(9);
    public static final CountDownLatch endLatch = new CountDownLatch(6);

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Ready");
        startLatch.countDown();
        System.out.println("Steady");
        startLatch.countDown();
        System.out.println("Go");
        startLatch.countDown();
        for (int i=1; i<=6; i++) {
            new Thread(getCarBehavior()).start();
        }
        endLatch.await();
        System.out.println("All cars have finished!");
    }


    private static Runnable getCarBehavior() {
        return () -> {
            startLatch.countDown();
            try {
                startLatch.await();
                Long sleepingTime = ThreadLocalRandom.current().nextLong(1, 5);
                TimeUnit.SECONDS.sleep(sleepingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + " finished");
            endLatch.countDown();
        };
    }
}
