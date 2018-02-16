package lesson_16_02_2018;

import java.util.concurrent.*;

public class Example3 {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(3, () -> {
            throw new RuntimeException();
        });

        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(() -> {
            try {
                System.out.println("1 start");
                barrier.await();
                System.out.println("1 end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        service.execute(() -> {
            try {
                System.out.println("2 start");
//                TimeUnit.SECONDS.sleep(1);
//                main.interrupt();
//                System.out.println(barrier.isBroken());
                barrier.await();
                System.out.println("2 end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();

        try {
            barrier.await();
        } catch (BrokenBarrierException | InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("In main thread: " +barrier.isBroken());
    }
}
