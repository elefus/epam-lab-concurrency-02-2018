package lesson_16_02_2018;

import java.util.concurrent.*;

public class Example2 {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(2);

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            try {
                System.out.println("1 start");
                latch.countDown();
                latch.await();
                System.out.println("1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                System.out.println("2 start");
                latch.countDown();
                latch.await();
                System.out.println("2 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();

        TimeUnit.SECONDS.sleep(3);
        System.out.println("Main end");
    }
}

/**
 * N потоков-машин.
 * Каждая обладает скоростью (рандом), влияющей на время приостановки (засыпания) потока.
 * Машины выезжают на старт, дожидаются команд Ready-Steady-Go (с задержкой в 1 секунду)
 * и одновременно начинают движение (засыпают).
 * По мере пробуждения каждый поток отчитывается о пересечении финишной черты.
 * После сигнала последней машины поток main оповещает об окончании гонки и завершает работу приложения.
 */
