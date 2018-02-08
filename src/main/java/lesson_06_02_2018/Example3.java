package lesson_06_02_2018;

import java.util.concurrent.TimeUnit;

@SuppressWarnings({"Duplicates", "InfiniteLoopStatement"})
public class Example3 {

    public static void main(String[] args) throws InterruptedException {
        Thread counter = new Thread(() -> {
            int val = 0;
            while (true) {
                try {
                    System.out.println(++val);
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println(Thread.currentThread().isInterrupted());
                }
            }
        });
        counter.start();

        TimeUnit.SECONDS.sleep(3);

        counter.interrupt();
    }
}
