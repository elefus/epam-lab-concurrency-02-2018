package lesson_15_02_2018;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Example3 {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            try {
                synchronized (semaphore) {
                    semaphore.acquire();
                    semaphore.acquire();
                    semaphore.acquire();
                    semaphore.acquire();
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("1 end");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                synchronized (semaphore) {
                    TimeUnit.SECONDS.sleep(1);
                    semaphore.release();
                    System.out.println("2 end");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                semaphore.release();
                System.out.println("3 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        service.shutdown();
    }
}
