package lesson_16_02_2018;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class Example4 {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newCachedThreadPool();
        Phaser phaser = new Phaser(1);
        printState(phaser);

        phaser.arrive();

        printState(phaser);

        service.execute(() -> {
            try {
                phaser.register();
                printState(phaser);
                TimeUnit.SECONDS.sleep(3);

                phaser.arriveAndAwaitAdvance();
                printState(phaser);

                System.out.println("2 end");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        service.execute(() -> {
            phaser.awaitAdvance(2);
            System.out.println("3 end");
        });
        service.shutdown();


        TimeUnit.SECONDS.sleep(2);
        phaser.arriveAndDeregister();
        printState(phaser);
        System.out.println("Main end");
    }

    private static void printState(Phaser phaser) {
        System.out.println(phaser.getPhase());
        System.out.println(phaser.getRegisteredParties());
        System.out.println();
    }
}
