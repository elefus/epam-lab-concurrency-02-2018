package Klimchuk.lesson_16_02_2018_Klimchuk;

import java.util.concurrent.*;

public class Race {

    private static final int READY_STEADY_GO = 3;
    private static final int CARS_NUMBER = 5;

    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(CARS_NUMBER);
        CountDownLatch startRace = new CountDownLatch(READY_STEADY_GO);

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < CARS_NUMBER; ++i) {
            executorService.execute(new Runnable() {

                @Override
                public void run() {

                    ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
                    int speed = threadLocalRandom.nextInt(1, 10);

                    try {
                        startRace.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        TimeUnit.SECONDS.sleep(speed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("CAR FINISH " + Thread.currentThread().getName());
                    countDownLatch.countDown();

                }
            });
        }

        executorService.shutdown();

        System.out.println("READY");
        TimeUnit.SECONDS.sleep(1);
        startRace.countDown();
        System.out.println("STEADY");
        TimeUnit.SECONDS.sleep(1);
        startRace.countDown();
        System.out.println("GO");
        startRace.countDown();

        countDownLatch.await();
        System.out.println("FINISH");

    }

}
