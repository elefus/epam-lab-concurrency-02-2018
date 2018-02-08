package lesson_02_02_2018;

import java.util.concurrent.TimeUnit;

public class Example3 {

    public static void main(String[] args) throws InterruptedException {
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

        Runnable counter = () -> {
            int value = 0;
            while (!Thread.interrupted()) {
                ++value;
            }
            System.out.println(Thread.currentThread().getName() + " " + value);
        };

        Thread[] thread = new Thread[Runtime.getRuntime().availableProcessors() + 2];
        for (int i = 0; i < thread.length; ++i) {
            thread[i] = new Thread(counter, "Counter-" + i);
            thread[i].setPriority(i < 2 ? Thread.MAX_PRIORITY - 3 : Thread.MAX_PRIORITY);
        }

        for (Thread curr : thread) {
            curr.start();
        }

        TimeUnit.SECONDS.sleep(2);
        for (Thread curr : thread) {
            curr.interrupt();
        }
    }

}
