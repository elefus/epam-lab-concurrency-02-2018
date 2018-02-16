package lesson_16_02_2018;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Example1 {

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(-3);
        System.out.println(semaphore.availablePermits());

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            try {
                System.out.println("1 start");
                semaphore.acquire();
                System.out.println("1 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();

        TimeUnit.SECONDS.sleep(3);
        System.out.println(semaphore.drainPermits());
        semaphore.release();
        System.out.println("Main end");
    }
}
