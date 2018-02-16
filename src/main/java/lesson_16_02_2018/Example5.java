package lesson_16_02_2018;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class Example5 {

    private static volatile long value = 10;
    private static AtomicLong atomicLong = new AtomicLong(10);

    public static void main(String[] args) throws InterruptedException {
        // Compare
        // And
        // Swap / Set

        value += 50;

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            if (atomicLong.compareAndSet(10, 100)) {
                System.out.println("Right expectedValue");
            }
        });
        service.shutdown();

        TimeUnit.SECONDS.sleep(1);
        if (!atomicLong.compareAndSet(10, 1000)) {
            System.out.println("Wrong expectedValue");
        }
    }
}
